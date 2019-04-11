package com.microfocus.octane.bridge.client.impl;

import java.nio.charset.StandardCharsets;

final class OctaneResultWrapper {
	private final String type;
	private final String refId;
	private final byte[] body;

	OctaneResultWrapper(String type, String refId, byte[] body) {
		this.type = type;
		this.refId = refId;
		this.body = body;
	}

	byte[] bytes() {
		byte[] typeBytes = type.getBytes(StandardCharsets.UTF_8);
		byte[] refIdBytes = refId.getBytes(StandardCharsets.UTF_8);
		byte[] result = new byte[3 + typeBytes.length + refIdBytes.length + body.length];
		result[0] = 1;
		result[1] = (byte) typeBytes.length;
		result[2] = (byte) refIdBytes.length;
		System.arraycopy(typeBytes, 0, result, 3, typeBytes.length);
		System.arraycopy(refIdBytes, 0, result, 3 + typeBytes.length, refIdBytes.length);
		System.arraycopy(body, 0, result, 3 + typeBytes.length + refIdBytes.length, body.length);
		return result;
	}
}
