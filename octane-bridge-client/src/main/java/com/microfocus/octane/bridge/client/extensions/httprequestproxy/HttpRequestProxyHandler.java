package com.microfocus.octane.bridge.client.extensions.httprequestproxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microfocus.octane.bridge.client.api.OctaneTaskHandler;

public class HttpRequestProxyHandler implements OctaneTaskHandler {
	private static final ObjectMapper om = new ObjectMapper();

	@Override
	public String getTaskType() {
		return "http-request";
	}

	@Override
	public String handle(String taskBody) throws Exception {
		HttpRequestTask httpRequestTask = om.readValue(taskBody, HttpRequestTask.class);
		if (httpRequestTask.method != null && httpRequestTask.url != null) {
			System.out.println(httpRequestTask.method);
			System.out.println(httpRequestTask.url);
			System.out.println(httpRequestTask.headers);
			System.out.println(httpRequestTask.body);
		}
		return null;
	}
}
