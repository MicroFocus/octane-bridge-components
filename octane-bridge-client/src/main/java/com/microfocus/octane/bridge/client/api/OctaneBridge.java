package com.microfocus.octane.bridge.client.api;

public interface OctaneBridge {

	void addTaskHandler(OctaneTaskHandler handler);

	void close();
}
