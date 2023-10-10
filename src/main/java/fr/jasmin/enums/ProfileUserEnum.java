package fr.jasmin.enums;

import java.util.ArrayList;
import java.util.List;

public enum ProfileUserEnum {

	CLIENT ("Client"),
	MAGASINIER ("Magasinier"),
	ADMIN ("Admin"),
	WEBSERVICE("Webservice");
	
	
	private String profilUser;
	private static List<String> profiles; 
	
	private ProfileUserEnum(String profilUser) {
		this.profilUser = profilUser;
	}
	
	public String getValue() {
		return profilUser;
	}
	
	public static List<String> getProfiles() {
		profiles = new ArrayList<String>();
		for (ProfileUserEnum oneValue : ProfileUserEnum.values()) {
			 profiles.add(oneValue.getValue());
		}
		
		return profiles;
	}
}
