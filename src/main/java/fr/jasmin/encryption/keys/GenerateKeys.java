package fr.jasmin.encryption.keys;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import fr.jasmin.enums.EncryptionEnum;

public final class GenerateKeys {

	private GenerateKeys() {
	}

	public final static SecretKey getKeyEncryption() throws Exception {
		String algorithm = EncryptionEnum.AES.getAlgorithme();
		Integer keysize = 256;
		if (algorithm == null || algorithm.isEmpty()) {
			throw new IllegalArgumentException("You need to specify the algorithm !");
		}
		if (keysize == null || keysize <= 0 || keysize % 8 != 0) {
			throw new IllegalArgumentException("The keysize is incorrect !");
		}
		SecretKey key = null;
		KeyGenerator keyGen = null;

		switch (algorithm) {
		case "DES":
			if (keysize != 56) {
				throw new IllegalArgumentException("Wrong keysize: must be equal to 56");
			}
			keyGen = KeyGenerator.getInstance(algorithm);
			keyGen.init(keysize);
			key = keyGen.generateKey();
			break;
		case "AES":

			List<Integer> listeKeySize = Arrays.asList(128, 192, 256);
			if (!listeKeySize.contains(keysize)) {
				throw new IllegalArgumentException("Wrong keysize: must be equal to 128, 192 or 256");
			}

			keyGen = KeyGenerator.getInstance(algorithm);
			keyGen.init(keysize);
			key = keyGen.generateKey();
			break;
		default:
			throw new NoSuchAlgorithmException("The algorithm '" + algorithm + "' is not supported !");
		}
		return key;
	}

}