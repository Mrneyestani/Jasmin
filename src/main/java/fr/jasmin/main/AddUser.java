package fr.jasmin.main;

import fr.jasmin.encryption.algo.EncryptionAlgorithm;
import fr.jasmin.entity.Address;
import fr.jasmin.entity.BankCard;
import fr.jasmin.entity.User;
import fr.jasmin.enums.ProfileUserEnum;
import fr.jasmin.model.dao.impl.UserDao;
import fr.jasmin.model.dao.interfaces.IUserDao;
import fr.jasmin.utils.Dates;

public class AddUser {

	public static void main(String[] args) {

		try {
			System.out.println("***********************début de test**********************");
			IUserDao userDao = new UserDao();

			Address address1 = new Address("12", "Rue Paul Vaillant Couturier","75000", "Paris", "France" );
			Address address2 = new Address("3", "Avenue Victor Hugo","69000", "Lyon", "France");

			User user = new User();
			user.setLastName("dupont");
			user.setFirstName("kamal");
			user.setBirthDate(Dates.convertStringToDate("09/07/2004"));
			user.setEmail("kamal@gmail.com");
			user.setPassword(EncryptionAlgorithm.encrypt("amineamine"));
			user.setProfile(ProfileUserEnum.MAGASINIER.getValue());
			user.setIsActif(true);
			user.setPhone("07656897656");

			address1.setUser(user);
			address2.setUser(user);
			user.getAddresses().add(address1);
			user.getAddresses().add(address2);

			// ajouter carte de paiement à l'utilisateur
			BankCard bankCard1 = new BankCard();
			bankCard1.setNameOwner("AHMEDI");
			bankCard1.setFirstNameOwner("imane");
			bankCard1.setNumber(EncryptionAlgorithm.encrypt("6667899867454567"));
			bankCard1.setDateFinValidite(Dates.convertStringToDate("09/07/2028"));
			bankCard1.setCryptogram(EncryptionAlgorithm.encrypt("756"));

			BankCard bankCard2 = new BankCard();
			bankCard2.setNameOwner("AHMEDI");
			bankCard2.setFirstNameOwner("imane");
			bankCard2.setNumber(EncryptionAlgorithm.encrypt("8767899867454567"));
			bankCard2.setDateFinValidite(Dates.convertStringToDate("11/06/2023"));
			bankCard2.setCryptogram(EncryptionAlgorithm.encrypt("880"));

			bankCard1.setUser(user);
			user.getBankCardList().add(bankCard1);

			bankCard2.setUser(user);
			user.getBankCardList().add(bankCard2);

			userDao.addUser(user);
			System.out.println(user);
			user.getAddresses().forEach(a -> System.out.println("  -> " + a));
			user.getBankCardList().forEach(bk -> System.out.println("  -> " + bk));

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("***********************fin de test**********************");

	}

}
