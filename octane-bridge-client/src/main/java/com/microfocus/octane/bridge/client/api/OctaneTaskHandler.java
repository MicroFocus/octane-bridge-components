package com.microfocus.octane.bridge.client.api;

public interface OctaneTaskHandler {

	String getTaskType();

	String handle(String taskBody) throws Exception;
}
