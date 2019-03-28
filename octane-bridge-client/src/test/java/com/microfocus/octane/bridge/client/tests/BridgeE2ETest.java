package com.microfocus.octane.bridge.client.tests;

import com.microfocus.octane.bridge.client.OctaneBridgesManager;
import com.microfocus.octane.bridge.client.api.OctaneBridge;
import org.junit.Test;

public class BridgeE2ETest {

	@Test
	public void test() {
		OctaneBridge bridge = OctaneBridgesManager.getInstance().initBridge(null);
	}
}
