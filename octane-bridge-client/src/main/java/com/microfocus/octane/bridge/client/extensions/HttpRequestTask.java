package com.microfocus.octane.bridge.client.extensions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class HttpRequestTask {
	String method;
	String url;
	Map<String, String> headers;
	String body;
}
