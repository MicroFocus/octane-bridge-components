package com.microfocus.octane.bridge.server;

import java.util.List;

/**
 * This class' purpose is to manage bridged (connected) clients
 * - should allow to register bridge client connected to the hosting application
 * - should be able to maintain list of all open bridges
 * - should define the bridge API as an endpoint service allowing to send and receive messages
 * - should be able to hand out an open bridge
 */

public interface BridgedClientsManager {

	void registerBridge(BridgedClient bridgedClient);

	List<? extends BridgedClient> getOpenBridges();

	BridgedClient getBridge();
}
