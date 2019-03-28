package com.microfocus.octane.bridge.client.api;

public interface OctaneBridge {

	void sendMessage(String message);

	void sendMessage(byte[] message);

	void close();
}
