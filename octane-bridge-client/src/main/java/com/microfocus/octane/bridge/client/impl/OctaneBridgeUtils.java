package com.microfocus.octane.bridge.client.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Base64;
import java.util.Map;
import java.util.zip.*;

class OctaneBridgeUtils {

	private OctaneBridgeUtils() {
	}

	static byte[] isToBytes(InputStream is) {
		byte[] buffer = new byte[4096];
		int length;
		try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
			while ((length = is.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			return result.toByteArray();
		} catch (IOException ioe) {
			throw new RuntimeException("failed to read input stream to bytes", ioe);
		}
	}

	/**
	 * this method will deflate (compress) and encode to Base64 any input string
	 *
	 * @param input textual input (assumed encoding is UTF-8)
	 * @return pair of long and string, where the string (value) is the transformed input and the long is the CRC32 of an the original value
	 */
	static Map.Entry<Long, String> deflateEncode(String input) throws Exception {
		return internalDeflateEncode(input.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * this method will deflate (compress) and encode to Base64 any input string
	 *
	 * @param input textual input (assumed encoding is UTF-8)
	 * @return pair of long and string, where the string (value) is the transformed input and the long is the CRC32 of an the original value
	 */
	static Map.Entry<Long, String> deflateEncode(InputStream input) throws Exception {
		return internalDeflateEncode(isToBytes(input));
	}

	/**
	 * this method will decode from Base64 and inflate (decompress) the input and validate the output with provided CRC32
	 *
	 * @param input deflated and encoded content
	 * @param crc   crc of the original content
	 * @return original content if succeed and passed validation
	 * @throws IllegalStateException if the provided CRC32 and the calculated one are not matching
	 */
	static String decodeInflateValidate(ByteBuffer input, long crc) throws Exception {
		//  inflate (decompress)
		byte[] originalBytes;
		Inflater inflater = new Inflater(true);
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     InflaterOutputStream ios = new InflaterOutputStream(baos, inflater)) {
			ios.write(Base64.getDecoder().decode(input.array()));
			originalBytes = baos.toByteArray();
		}

		//  calculate CRC and validate
		CRC32 crc32 = new CRC32();
		crc32.update(originalBytes);
		if (crc32.getValue() == crc) {
			return new String(originalBytes, StandardCharsets.UTF_8);
		} else {
			throw new IllegalStateException("checksum of the extracted text does not match expectations");
		}
	}

	private static Map.Entry<Long, String> internalDeflateEncode(byte[] originalBytes) throws Exception {
		//  calculate CRC of the original body
		CRC32 crc32 = new CRC32();
		crc32.update(originalBytes);
		long crc = crc32.getValue();

		//  deflate (compress)
		String compressedBase64;
		Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION, true);
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     DeflaterOutputStream dos = new DeflaterOutputStream(baos, deflater)) {
			dos.write(originalBytes);
			dos.close();
			byte[] compressedBytes = baos.toByteArray();
			compressedBase64 = Base64.getEncoder().encodeToString(compressedBytes);
		}

		return new AbstractMap.SimpleImmutableEntry<>(crc, compressedBase64);
	}
}
