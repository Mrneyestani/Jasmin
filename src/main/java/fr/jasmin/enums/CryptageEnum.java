package fr.jasmin.enums;

public enum CryptageEnum {

	DES ("DES"),
	AES ("AES"),
	BLOWFISH ("BLOWFISH"),
	PBE_MD5_DES ("PBEWithMD5AndDES");
	
	private String algorithme;
	
	private CryptageEnum(String algorithme) {
		this.algorithme = algorithme;
	}

	public String getAlgorithme() {
		return algorithme;
	}
	
}
