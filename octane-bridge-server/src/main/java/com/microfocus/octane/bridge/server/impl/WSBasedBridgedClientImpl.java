package com.microfocus.octane.bridge.server.impl;

import com.microfocus.octane.bridge.server.BridgedClient;

import java.util.concurrent.TimeUnit;

/**
 * WS based bridged client implementation is oriented towards an
 * underlying WebSockets connectivity mechanism
 *
 * @param <T>
 */

public class WSBasedBridgedClientImpl<T> implements BridgedClient {

	WSBasedBridgedClientImpl() {
		//  do all the setup here
		//  should get here tasks cache
		//  should get here results cache
	}

	@Override
	public String getRefId() {
		return null;
	}

	@Override
	public Object getMetadata() {
		return null;
	}

	@Override
	public String sendAndWaitForResult(Object task) {
		return null;
	}

	@Override
	public String sendAndWaitForResult(Object task, long timeout, TimeUnit timeUnit) {
		return null;
	}

	@Override
	public void sendAndForget(Object task) {

	}
}
