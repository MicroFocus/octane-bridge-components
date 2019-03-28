package com.microfocus.octane.bridge.client.impl;

import com.microfocus.octane.bridge.client.api.BridgeConfiguration;
import com.microfocus.octane.websocket.OctaneWSClientContext;
import com.microfocus.octane.websocket.OctaneWSEndpointClient;

class OctaneBridgeWSClient extends OctaneWSEndpointClient {
	private final BridgeConfiguration bridgeConfiguration;

	OctaneBridgeWSClient(OctaneWSClientContext context, BridgeConfiguration bridgeConfiguration) {
		super(context);
		this.bridgeConfiguration = bridgeConfiguration;
	}

	@Override
	public void onBinaryMessage(byte[] message) {
		super.onBinaryMessage(message);
	}

	@Override
	public void onStringMessage(String message) {
		super.onStringMessage(message);
	}

	@Override
	public void onWebSocketBinary(byte[] message, int offset, int len) {
		super.onWebSocketBinary(message, offset, len);
	}
}
