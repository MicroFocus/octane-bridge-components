package com.microfocus.octane.bridge.client.api;

/**
 * Bridge configuration DTO - to be created via built-in builder
 */
public class BridgeConfiguration {
	private static final int DEFAULT_NUMBER_OF_WORKERS = 20;
	private String endpointUrl;
	private String client;
	private String secret;
	private String proxyUrl;
	private String proxyUsername;
	private String proxyPassword;
	private int numberOfWorkers;

	private BridgeConfiguration() {
	}

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public String getClient() {
		return client;
	}

	public String getSecret() {
		return secret;
	}

	public String getProxyUrl() {
		return proxyUrl;
	}

	public String getProxyUsername() {
		return proxyUsername;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public int getNumberOfWorkers() {
		return numberOfWorkers;
	}

	public static Builder builder() {
		return new Builder(new BridgeConfiguration());
	}

	/**
	 * internal builder class
	 */
	public static final class Builder {
		private final BridgeConfiguration c;
		private volatile boolean built = false;

		private Builder(BridgeConfiguration config) {
			this.c = config;
		}

		public Builder setEndpointUrl(String endpointUrl) {
			validateNotBuilt();
			c.endpointUrl = endpointUrl;
			return this;
		}

		public Builder setClient(String client) {
			validateNotBuilt();
			c.client = client;
			return this;
		}

		public Builder setSecret(String secret) {
			validateNotBuilt();
			c.secret = secret;
			return this;
		}

		public Builder setProxyUrl(String proxyUrl) {
			validateNotBuilt();
			c.proxyUrl = proxyUrl;
			return this;
		}

		public Builder setProxyUsername(String proxyUsername) {
			validateNotBuilt();
			c.proxyUsername = proxyUsername;
			return this;
		}

		public Builder setProxyPassword(String proxyPassword) {
			validateNotBuilt();
			c.proxyPassword = proxyPassword;
			return this;
		}

		public Builder setNumberOfWorkers(int numberOfWorkers) {
			validateNotBuilt();
			c.numberOfWorkers = numberOfWorkers;
			return this;
		}

		public BridgeConfiguration build() {
			validateNotBuilt();
			validateConfiguration();
			built = true;
			return c;
		}

		private void validateNotBuilt() {
			if (built) {
				throw new IllegalStateException("build MAY BE used only ONCE");
			}
		}

		private void validateConfiguration() {
			if (c.endpointUrl == null || c.endpointUrl.isEmpty()) {
				throw new IllegalStateException("endpoint URL MUST NOT be NULL nor EMPTY");
			}
			if (c.numberOfWorkers == 0) {
				c.numberOfWorkers = DEFAULT_NUMBER_OF_WORKERS;
			}
		}
	}
}
