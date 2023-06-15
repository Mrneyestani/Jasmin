package fr.jasmin.encryption.algo;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import fr.jasmin.encryption.keys.GenerateKeys;
import fr.jasmin.entity.Param;
import fr.jasmin.enums.EncryptionEnum;
import fr.jasmin.model.dao.impl.ParamDao;
import fr.jasmin.utils.KeyConvertor;

public final class EncryptionAlgorithm {
	private static String algorithm = EncryptionEnum.AES.getAlgorithme();
	private static ParamDao paramDao = new ParamDao();
	private static Param param = new Param();
	private static SecretKey key = null;

	public final static byte[] encrypt(String messageToEncrypt) throws Exception {
		param = paramDao.getSecurityKeyById(1);
		if (param == null) {
			Param param1 = new Param();
			key = GenerateKeys.getKeyEncryption();
			param1.setKeyInByte(KeyConvertor.convertSercetToByte(key));
			param1.setAlgorithm(algorithm);
			paramDao.addSecurityKey(param1);
		} else {
			byte[] keyByte = param.getKeyInByte();
			key = KeyConvertor.convertByteToSercet(keyByte, algorithm);
		}

		switch (algorithm) {
		case "DES":
		case "AES":
		case "BLOWFISH":
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(KeyConvertor.convertStringToByte(messageToEncrypt));
		case "PBE_MD5_DES":
			throw new NoSuchAlgorithmException("The algorithm '" + algorithm + "' is not yet implemented!");
		default:
			throw new NoSuchAlgorithmException("The algorithm '" + algorithm + "' is not supported!");
		}
	}

	public final static String decrypt(byte[] messageEnCrypted) throws Exception {
		param = paramDao.getSecurityKeyById(1);
		if (param == null) {
			Param param1 = new Param();
			key = GenerateKeys.getKeyEncryption();
			param1.setKeyInByte(KeyConvertor.convertSercetToByte(key));
			param1.setAlgorithm(algorithm);
			paramDao.addSecurityKey(param1);
		} else {
			byte[] keyByte = param.getKeyInByte();
			key = KeyConvertor.convertByteToSercet(keyByte, algorithm);
		}

		switch (algorithm) {
		case "DES":
		case "AES":
		case "BLOWFISH":
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] messageDecrypteByte = cipher.doFinal(messageEnCrypted);
			return new String(messageDecrypteByte);
		case "PBE_MD5_DES":
			throw new NoSuchAlgorithmException("The algorithm '" + algorithm + "' is not yet implemented!");
		default:
			throw new NoSuchAlgorithmException("The algorithm '" + algorithm + "' is not supported!");
		}
	}
}