package com.microfocus.octane.bridge.server;

import java.util.concurrent.TimeUnit;

public interface BridgedClient<T> {

	String getRefId();

	T getMetadata();

	String sendAndWaitForResult(Object task);

	String sendAndWaitForResult(Object task, long timeout, TimeUnit timeUnit);

	void sendAndForget(Object task);
}
