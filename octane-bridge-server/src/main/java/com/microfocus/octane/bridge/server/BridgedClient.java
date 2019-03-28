package com.microfocus.octane.bridge.server;

import java.util.concurrent.Future;

public interface BridgedClient {

	String getRefId();

	Future<Object> sendMessage(Object task);
}
