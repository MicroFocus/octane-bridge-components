package com.microfocus.octane.tests;

import com.microfocus.octane.bridge.client.OctaneBridgesManager;
import com.microfocus.octane.bridge.client.api.BridgeConfiguration;
import com.microfocus.octane.bridge.client.api.OctaneBridge;
import com.microfocus.octane.bridge.server.BridgedClient;
import com.microfocus.octane.bridge.server.BridgedClientsManager;
import com.microfocus.octane.tests.simulation.TestWebSocketsSimulator;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BridgeServerE2ETest {
	private static final Logger logger = LoggerFactory.getLogger(BridgeServerE2ETest.class);
	private static int E2E_SERVER_PORT = 3333;
	private static Server testServer;

	@BeforeClass
	public static void startTestServer() throws Exception {
		logger.info("-- starting test server");
		String portParam = System.getProperty("octane.bridge.server.test.port");
		testServer = TestWebSocketsSimulator.startWebsocketServer(
				portParam != null && !portParam.isEmpty() ? E2E_SERVER_PORT = Integer.parseInt(portParam) : E2E_SERVER_PORT,
				E2ETestHttpServlet.class,
				E2ETestWSServlet.class);
	}

	@AfterClass
	public static void stopTestServer() throws Exception {
		logger.info("-- stopping test server");
		testServer.stop();
		WSTestsUtils.waitAtMostFor(5000, () -> {
			if (testServer.isStopped()) {
				return true;
			} else {
				return null;
			}
		});
	}

	@Test
	public void testE2E() {

		//  build configuration
		BridgedClientsManager.Configuration configuration = BridgedClientsManager.configurationBuilder()
				.setBridgeStatusesCache(null)
				.setTasksCache(null)
				.setResultsCache(null)
				.build();

		//  init manager (starting being ready to accept connections as bridges)
		BridgedClientsManager bridgedClientsManager = BridgedClientsManager.init(configuration);

		//  open client to represent bridge (this should effectively create BridgedClient)

		//  TODO: verify bridged client created

		//  TODO: test interop E2E

		//  register client in manager
		SyncOPBBridge syncOPBBridgeMetadata = new SyncOPBBridge();
		BridgedClient<SyncOPBBridge> bridgedClient = bridgedClientsManager.initBridge(
				"some ref id", null, syncOPBBridgeMetadata
		);
		String refId = bridgedClient.getRefId();
		SyncOPBBridge metadata = bridgedClient.getMetadata();
		bridgedClient.sendAndForget("something");
		String result = bridgedClient.sendAndWaitForResult("else");
	}

	private void openBridge() {
		BridgeConfiguration bc = BridgeConfiguration.builder()
				.setEndpointUrl("")
				.setClient("")
				.setSecret("")
				.build();
		OctaneBridge bridge = OctaneBridgesManager.getInstance().initBridge(bc);
	}

	private static final class SyncOPBBridge {

	}

	public static final class E2ETestHttpServlet extends HttpServlet {
		private static String expectedClient;
		private static String expectedSecret;

		@Override
		protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			Request request = (Request) req;

			if ("POST".equalsIgnoreCase(req.getMethod()) && "/authentication/sign_in".equals(request.getHttpURI().getPath())) {
				String contentLine = request.getReader().readLine();
				if (contentLine.contains(expectedClient) && contentLine.contains(expectedSecret)) {
					resp.setStatus(HttpStatus.OK_200);
					resp.setHeader("Set-Cookie", "NON_RELEVANT_COOKIE=non_relevant_data;LWSSO_COOKIE_KEY=some_fake_token");
				} else {
					resp.setHeader("Set-Cookie", "NON_RELEVANT_COOKIE=non_relevant_data");
					resp.setStatus(HttpStatus.UNAUTHORIZED_401);
				}
			} else {
				resp.setStatus(HttpStatus.NOT_FOUND_404);
			}
			resp.flushBuffer();

			request.setHandled(true);
		}
	}

	public static final class E2ETestWSServlet extends WebSocketServlet {

		@Override
		public void configure(WebSocketServletFactory webSocketServletFactory) {
			webSocketServletFactory.getPolicy().setMaxTextMessageSize(256 * 1024);
			webSocketServletFactory.getPolicy().setMaxBinaryMessageSize(256 * 1024);
			webSocketServletFactory.register(E2ETestWSHandler.class);
		}
	}

	public static final class E2ETestWSHandler extends WebSocketAdapter {
		private static byte[] lastReceivedBinary;
		private static String lastReceivedString;

		@Override
		public void onWebSocketBinary(byte[] payload, int offset, int len) {
			lastReceivedBinary = payload;
			try {
				this.getSession().getRemote().sendBytes(ByteBuffer.wrap(payload));
			} catch (IOException ioe) {
				//
			}
		}

		@Override
		public void onWebSocketText(String message) {
			lastReceivedString = message;
			try {
				this.getSession().getRemote().sendString(message);
			} catch (IOException ioe) {
				//
			}
		}
	}
}
