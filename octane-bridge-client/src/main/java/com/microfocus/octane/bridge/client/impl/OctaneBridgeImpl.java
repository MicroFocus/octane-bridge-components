package com.microfocus.octane.bridge.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microfocus.octane.bridge.client.api.*;
import com.microfocus.octane.bridge.client.extensions.HttpRequestProxyHandler;
import com.microfocus.octane.websocket.OctaneWSClientContext;
import com.microfocus.octane.websocket.OctaneWSClientService;
import com.microfocus.octane.websocket.OctaneWSEndpointClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OctaneBridgeImpl implements OctaneBridge {
	private static final Logger logger = LoggerFactory.getLogger(OctaneBridgeImpl.class);
	private static final ObjectMapper om = new ObjectMapper();

	private final ExecutorService tasksExecutors;
	private final BridgeConfiguration configuration;
	private final OctaneBridgeWSClient bridgeWSClient;
	private final Map<String, OctaneTaskHandler> taskHandlers = new HashMap<>();

	public OctaneBridgeImpl(BridgeConfiguration config) {
		configuration = config;
		tasksExecutors = Executors.newFixedThreadPool(config.getNumberOfWorkers(), new OctaneBridgeTaskWorkersFactory());
		OctaneWSClientContext bridgeContext = buildContext(configuration);
		bridgeWSClient = new OctaneBridgeWSClient(bridgeContext);
		OctaneWSClientService.getInstance().initClient(bridgeWSClient);

		//  register default handlers
		addTaskHandler(new HttpRequestProxyHandler());
	}

	private OctaneWSClientContext buildContext(BridgeConfiguration configuration) {
		OctaneWSClientContext.OctaneWSClientContextBuilder builder = OctaneWSClientContext.builder()
				.setEndpointUrl(configuration.getEndpointUrl())
				.setClient(configuration.getClient())
				.setSecret(configuration.getSecret());
		if (configuration.getProxyUrl() != null && !configuration.getProxyUrl().isEmpty()) {
			builder.setProxyUrl(configuration.getProxyUrl());
			builder.setProxyUsername(configuration.getProxyUsername());
			builder.setProxyPassword(configuration.getProxyPassword());
		}
		return builder.build();
	}

	@Override
	public void addTaskHandler(OctaneTaskHandler handler) {
		if (handler == null) {
			throw new IllegalArgumentException("handler MUST OT be NULL");
		}
		if (handler.getTaskType() == null || handler.getTaskType().isEmpty()) {
			throw new IllegalArgumentException("handler MUST declare a non-NULL and non-EMPTY target task type, found '" + handler.getTaskType() +
					"' in handler " + handler.getClass().getSimpleName());
		}
		if (taskHandlers.containsKey(handler.getTaskType())) {
			throw new IllegalStateException("task type '" + handler.getTaskType() + "' has duplicate handlers definition (" +
					taskHandlers.get(handler.getTaskType()).getClass().getSimpleName() + ", " + handler.getClass().getSimpleName() + ")");
		}
		taskHandlers.put(handler.getTaskType(), handler);
	}

	@Override
	public void close() {
		bridgeWSClient.stop();
		logger.info("bridge to " + configuration + " closed");
	}

	/**
	 * internal WS client to handle the raw message
	 */
	private final class OctaneBridgeWSClient extends OctaneWSEndpointClient {

		OctaneBridgeWSClient(OctaneWSClientContext context) {
			super(context);
		}

		@Override
		public void onWebSocketText(String message) {
			//  parse task wrapper
			OctaneTaskWrapper taskWrapper;
			try {
				taskWrapper = om.readValue(message, OctaneTaskWrapper.class);
			} catch (Exception e) {
				logger.error("failed to parse the OctaneTaskWrapper, (partial) body: '" + message + "'", e);
				//  TODO: consider to send some error message if expected result
				return;
			}

			//  get handler
			OctaneTaskHandler handler = taskHandlers.get(taskWrapper.type);

			//  handout task to handler and process result
			tasksExecutors.execute(() -> {
				Object result;

				//  process task
				try {
					result = handler.handle(taskWrapper.body);
				} catch (Exception e) {
					logger.error(handler.getClass().getSimpleName() + " failed to handle task", e);
					//  TODO: consider to send some error message if expected result
					return;
				}

				//  send result
				try {
					OctaneResultWrapper resultWrapper = new OctaneResultWrapper(taskWrapper.type, taskWrapper.refId, om.writeValueAsBytes(result));
					bridgeWSClient.sendBinary(resultWrapper.bytes());
				} catch (Exception e) {
					logger.error("failed to serialize result of task handling of " + handler.getClass().getSimpleName());
					//  TODO: consider to send some error message if expected result
				}
			});
		}
	}
}
