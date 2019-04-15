package com.microfocus.octane.bridge.client.extensions.httprequestproxy;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class HttpRequestResult {
	String method;
	String url;
	Map<String, String> headers;
	String body;
}
