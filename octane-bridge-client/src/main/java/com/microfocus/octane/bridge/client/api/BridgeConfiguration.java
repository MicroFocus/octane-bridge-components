package com.microfocus.octane.bridge.client.api;

public class BridgeConfiguration {
	private String endpointUrl;
	private String client;
	private String secret;
	private String proxyUrl;
	private String proxyUsername;
	private String proxyPassword;

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public BridgeConfiguration setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
		return this;
	}

	public String getClient() {
		return client;
	}

	public BridgeConfiguration setClient(String client) {
		this.client = client;
		return this;
	}

	public String getSecret() {
		return secret;
	}

	public BridgeConfiguration setSecret(String secret) {
		this.secret = secret;
		return this;
	}

	public String getProxyUrl() {
		return proxyUrl;
	}

	public BridgeConfiguration setProxyUrl(String proxyUrl) {
		this.proxyUrl = proxyUrl;
		return this;
	}

	public String getProxyUsername() {
		return proxyUsername;
	}

	public BridgeConfiguration setProxyUsername(String proxyUsername) {
		this.proxyUsername = proxyUsername;
		return this;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public BridgeConfiguration setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
		return this;
	}
}
