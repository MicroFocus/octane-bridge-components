package com.microfocus.octane.bridge.client.api;

public interface OctaneTaskHandler {

	String getTaskType();

	Object handle(String taskBody) throws Exception;
}
