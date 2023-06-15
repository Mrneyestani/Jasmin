package fr.jasmin.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public abstract class KeyConvertor {

	public static byte[] convertSercetToByte(SecretKey KeySercet) throws Exception {
		return KeySercet.getEncoded();

	}

	public static String convertSercetToString(SecretKey KeySercet) throws Exception {
		return new String(KeySercet.getEncoded(), "UTF8");

	}

	public static SecretKey convertByteToSercet(byte[] KeyByte, String algorithm) {

		return new SecretKeySpec(KeyByte, 0, KeyByte.length, algorithm);
	}

	public static String convertByteToString(byte[] Byte) throws Exception {

		return new String(Byte, "UTF8");
	}

	public static byte[] convertStringToByte(String text) throws Exception {

		return text.getBytes("UTF-8");
	}
}
