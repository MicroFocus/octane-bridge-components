package com.microfocus.octane.bridge.client.impl;

import com.microfocus.octane.bridge.client.api.BridgeConfiguration;
import com.microfocus.octane.bridge.client.api.OctaneBridge;
import com.microfocus.octane.websocket.OctaneWSClientContext;
import com.microfocus.octane.websocket.OctaneWSClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OctaneBridgeImpl implements OctaneBridge {
	private static final Logger logger = LoggerFactory.getLogger(OctaneBridgeImpl.class);
	private final BridgeConfiguration configuration;
	private final OctaneBridgeWSClient bridgeWSClient;

	public OctaneBridgeImpl(BridgeConfiguration configuration) {
		this.configuration = configuration;
		OctaneWSClientContext bridgeContext = buildContext(configuration);
		bridgeWSClient = new OctaneBridgeWSClient(bridgeContext, configuration);
		OctaneWSClientService.getInstance().initClient(bridgeWSClient);
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
	public void close() {
		bridgeWSClient.stop();
		logger.info("bridge to " + configuration + " closed");
	}
}
