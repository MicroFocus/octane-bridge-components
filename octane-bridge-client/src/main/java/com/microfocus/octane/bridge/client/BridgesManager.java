package com.microfocus.octane.bridge.client;

import com.microfocus.octane.bridge.client.api.OctaneBridge;
import com.microfocus.octane.bridge.client.impl.OctaneBridgeImpl;

public interface BridgesManager {

	default OctaneBridge initBridge() {
		BridgeConfiguration bridgeConfiguration = new BridgeConfiguration();
		return new OctaneBridgeImpl(bridgeConfiguration);
	}

	/**
	 * configuration class for the internal usage only
	 */
	final class BridgeConfiguration {

		private BridgeConfiguration() {
		}
	}
}
