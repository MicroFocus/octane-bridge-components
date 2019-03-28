package com.microfocus.octane.bridge.client;

import com.microfocus.octane.bridge.client.api.BridgeConfiguration;
import com.microfocus.octane.bridge.client.api.OctaneBridge;
import com.microfocus.octane.bridge.client.impl.OctaneBridgeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * OctaneBridgesManager serves as the main entry point for operating On-Premises bridges to Octane server
 * - singleton
 * - provides means to create bridge configuration
 * - allows to initialize bridge/s as per configuration
 */

public final class OctaneBridgesManager {
	private static final Logger logger = LoggerFactory.getLogger(OctaneBridgesManager.class);
	private final Map<BridgeConfiguration, OctaneBridge> bridges = new HashMap<>();

	private OctaneBridgesManager() {
	}

	public static OctaneBridgesManager getInstance() {
		return INSTANCE_HOLDER.INSTANCE;
	}

	synchronized public OctaneBridge initBridge(BridgeConfiguration configuration) {
		if (configuration == null) {
			throw new IllegalArgumentException("configuration MUST NOT be NULL");
		}
		//  TODO: validate configuration?
		OctaneBridge result = new OctaneBridgeImpl(configuration);
		bridges.put(configuration, result);
		return result;
	}

	private static final class INSTANCE_HOLDER {
		private static final OctaneBridgesManager INSTANCE = new OctaneBridgesManager();
	}
}
