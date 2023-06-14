package fr.jasmin.main;

import fr.jasmin.encryption.algo.EncryptionAlgorithm;
import fr.jasmin.utils.KeyConvertor;

public class Test_AES_algo {

	public static void main(String[] args) throws Exception {
		final String message = "Hello! I am a computer science student, thank you!";
		final String message1 = "1234567893214569877";
//		SecretKey key = GenerateKeys.getKeyEncryption();
//		System.out.println("key (" + key.getAlgorithm() + "," 
//		+ key.getFormat() + ") : " + KeyConvertor.convertSercetToString(key));	

		// Encryption of the message
		byte[] cipherBytes = EncryptionAlgorithm.encrypt(message);
		byte[] cipherBytes1 = EncryptionAlgorithm.encrypt(message1);
		// Decryption of the message
		String messageDesCrypte = EncryptionAlgorithm.decrypt(cipherBytes);
		String messageDesCrypte1 = EncryptionAlgorithm.decrypt(cipherBytes1);
		System.out.println("Message Original : " + message);
		System.out.println("Message Original : " + message1);
		System.out.println("Starting encryption...");
		System.out.println("Encrypted Message : " + KeyConvertor.convertByteToString(cipherBytes));
		System.out.println("Decrypted Message : " + messageDesCrypte);
		System.out.println("Encrypted Message : " + KeyConvertor.convertByteToString(cipherBytes1));
		System.out.println("Decrypted Message : " + messageDesCrypte1);

	}

}
