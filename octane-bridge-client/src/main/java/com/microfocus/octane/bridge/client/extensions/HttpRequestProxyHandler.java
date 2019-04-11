package com.microfocus.octane.bridge.client.extensions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microfocus.octane.bridge.client.api.OctaneTaskHandler;

public class HttpRequestProxyHandler implements OctaneTaskHandler {
	private static final ObjectMapper om = new ObjectMapper();

	@Override
	public String getTaskType() {
		return "http-request";
	}

	@Override
	public Object handle(byte[] body) throws Exception {
		HttpRequestTask httpRequestTask = om.readValue(body, HttpRequestTask.class);
		if (httpRequestTask.method != null && httpRequestTask.url != null) {
			System.out.println(httpRequestTask.headers);
			System.out.println(httpRequestTask.body);
		}
		return null;
	}
}
