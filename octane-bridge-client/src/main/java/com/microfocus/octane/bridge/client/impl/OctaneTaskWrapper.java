package com.microfocus.octane.bridge.client.impl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

final class OctaneTaskWrapper {
	final String type;
	final String refId;
	final byte[] body;

	private OctaneTaskWrapper(String type, String refId, byte[] body) {
		this.type = type;
		this.refId = refId;
		this.body = body;
	}

	static OctaneTaskWrapper parse(byte[] input, int offset, int length) {
		if (input[offset] == 1) {
			return parseV1(input, offset + 1, length - 1);
		} else {
			throw new IllegalArgumentException("unexpected input");
		}
	}

	static private OctaneTaskWrapper parseV1(byte[] input, int offset, int length) {
		byte typeLen = input[offset++];
		byte refIdLen = input[offset++];
		String type = new String(input, offset += typeLen, typeLen, StandardCharsets.UTF_8);
		String refId = new String(input, offset += refIdLen, refIdLen, StandardCharsets.UTF_8);
		return new OctaneTaskWrapper(type, refId, Arrays.copyOfRange(input, offset, length - offset));
	}
}
