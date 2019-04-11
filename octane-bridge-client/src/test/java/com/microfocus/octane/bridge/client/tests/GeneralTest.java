package com.microfocus.octane.bridge.client.tests;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class GeneralTest {

	@Test
	public void testA() {
		byte[] bytes = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		ByteBuffer buffer = ByteBuffer.wrap(bytes, 2, 5);
		System.out.println("lsjdfls");
	}
}
