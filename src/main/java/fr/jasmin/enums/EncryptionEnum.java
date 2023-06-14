package fr.jasmin.enums;

public enum EncryptionEnum{

	DES ("DES"),
	AES ("AES"),
	BLOWFISH ("BLOWFISH"),
	PBE_MD5_DES ("PBEWithMD5AndDES");
	
	private String algorithme;
	
	private EncryptionEnum(String algorithme) {
		this.algorithme = algorithme;
	}

	public String getAlgorithme() {
		return algorithme;
	}
	
}
