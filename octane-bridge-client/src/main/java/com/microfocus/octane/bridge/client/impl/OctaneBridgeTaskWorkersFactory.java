package com.microfocus.octane.bridge.client.impl;

import java.util.concurrent.ThreadFactory;

class OctaneBridgeTaskWorkersFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		Thread result = new Thread(r);
		result.setName("OBTaskWorker [" + result.getId() + "]");
		result.setDaemon(true);
		return result;
	}
}
