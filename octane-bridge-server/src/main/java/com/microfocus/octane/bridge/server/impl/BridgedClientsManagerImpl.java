package com.microfocus.octane.bridge.server.impl;

import com.microfocus.octane.bridge.server.BridgedClient;
import com.microfocus.octane.bridge.server.BridgedClientsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.AsyncResponse;
import java.util.List;

public class BridgedClientsManagerImpl extends BridgedClientsManager {
	private static final Logger logger = LoggerFactory.getLogger(BridgedClientsManagerImpl.class);

	public BridgedClientsManagerImpl(Configuration configuration) {
		logger.info("bridged clients manager initialization");
	}

	@Override
	public <T> BridgedClient<T> initBridge(String refId, AsyncResponse connection, T metadata) {
		return null;
	}

	@Override
	public List<? extends BridgedClient> getOpenBridges() {
		return null;
	}

	@Override
	public BridgedClient getBridge(String refId) {
		return null;
	}
}
