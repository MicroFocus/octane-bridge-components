package com.microfocus.octane.test;

import com.microfocus.octane.bridge.server.BridgedClient;
import com.microfocus.octane.bridge.server.BridgedClientsManager;
import org.junit.Test;

public class BridgeServerE2ETest {

	@Test
	public void testE2E() {

		//  build configuration
		BridgedClientsManager.Configuration configuration = BridgedClientsManager.configurationBuilder()
				.setBridgeStatusesCache(null)
				.setTasksCache(null)
				.setResultsCache(null)
				.build();

		//  init manager
		BridgedClientsManager bridgedClientsManager = BridgedClientsManager.init(configuration);

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

	private static final class SyncOPBBridge {

	}
}
