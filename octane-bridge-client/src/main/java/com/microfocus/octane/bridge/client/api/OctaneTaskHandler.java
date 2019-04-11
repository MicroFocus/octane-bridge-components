package com.microfocus.octane.bridge.client.api;

public interface OctaneTaskHandler {

	String getTaskType();

	Object handle(byte[] body) throws Exception;
}
