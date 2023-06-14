package fr.jasmin.enums;

import fr.jasmin.utils.Utils;

public class TEnumsUtilisation {

	public static void main(String[] args) {
		
		Utils.trace("*************************** Begin ************************************\n");
		
		System.out.println(Profile.COSTUMER);
		System.out.println(Profile.COSTUMER.getId());
		System.out.println(Profile.COSTUMER.getName());
		System.out.println(Profile.fromString("Client WS basic"));
		
		System.out.println(Gender.MALE.getId());
		System.out.println(Gender.MALE.getTitle());
		System.out.println(Gender.MALE.getValue());
		
		System.out.println(Gender.fromId(0));
		System.out.println(Gender.fromString("Homme"));
		
		Utils.trace("*************************** end ************************************");

	}

}
