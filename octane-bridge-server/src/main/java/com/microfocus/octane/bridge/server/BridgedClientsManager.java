package com.microfocus.octane.bridge.server;

import com.microfocus.octane.bridge.server.impl.BridgedClientsManagerImpl;

import javax.cache.Cache;
import javax.ws.rs.container.AsyncResponse;
import java.util.List;

/**
 * This class' purpose is to manage bridged (connected) clients
 * - should allow to register bridge client connected to the hosting application
 * - should be able to maintain list of all open bridges
 * - should define the bridge API as an endpoint service allowing to send and receive messages
 * - should be able to hand out an open bridge
 */

public abstract class BridgedClientsManager {

	public static ConfigurationBuilder configurationBuilder() {
		return new ConfigurationBuilder();
	}

	public static BridgedClientsManager init(Configuration configuration) {
		return new BridgedClientsManagerImpl(configuration);
	}

	abstract public <T> BridgedClient<T> initBridge(
			String refId,
			AsyncResponse connection,
			T metadata
	);

	abstract public List<? extends BridgedClient> getOpenBridges();

	abstract public BridgedClient getBridge(String refId);

	public static final class Configuration {
		private final Cache<String, String> bridgeStatusesCache;
		private final Cache<String, String> tasksCache;
		private final Cache<String, String> resultsCache;

		private Configuration(
				Cache<String, String> bridgeStatusesCache,
				Cache<String, String> tasksCache,
				Cache<String, String> resultsCache
		) {
			this.bridgeStatusesCache = bridgeStatusesCache;
			this.tasksCache = tasksCache;
			this.resultsCache = resultsCache;
		}
	}

	public static final class ConfigurationBuilder {
		private volatile boolean built = false;
		private Cache<String, String> bridgeStatusesCache;
		private Cache<String, String> tasksCache;
		private Cache<String, String> resultsCache;

		private ConfigurationBuilder() {
		}

		public ConfigurationBuilder setBridgeStatusesCache(Cache<String, String> bridgeStatusesCache) {
			validateNotBuilt();
			if (bridgeStatusesCache == null) {
				throw new IllegalArgumentException("bridge statuses cache MUST NOT be NULL");
			}
			this.bridgeStatusesCache = bridgeStatusesCache;
			return this;
		}

		public ConfigurationBuilder setTasksCache(Cache<String, String> tasksCache) {
			validateNotBuilt();
			if (tasksCache == null) {
				throw new IllegalArgumentException("tasks cache MUST NOT be NULL");
			}
			this.tasksCache = tasksCache;
			return this;
		}

		public ConfigurationBuilder setResultsCache(Cache<String, String> resultsCache) {
			validateNotBuilt();
			if (resultsCache == null) {
				throw new IllegalArgumentException("results cache MUST NOT be NULL");
			}
			this.resultsCache = resultsCache;
			return this;
		}

		public Configuration build() {
			validateNotBuilt();
			validateAllInPlace();
			Configuration result = new Configuration(bridgeStatusesCache, tasksCache, resultsCache);
			built = true;
			return result;
		}

		private void validateNotBuilt() {
			if (built) {
				throw new IllegalStateException("configuration build MAY be used only once");
			}
		}

		private void validateAllInPlace() {
			if (bridgeStatusesCache == null) {
				throw new IllegalStateException("bridge statuses cache MUST NOT be NULL");
			}
			if (tasksCache == null) {
				throw new IllegalStateException("tasks cache MUST NOT be NULL");
			}
			if (resultsCache == null) {
				throw new IllegalStateException("results cache MUST NOT be NULL");
			}
		}
	}
}
