package fr.jasmin.main;

import fr.jasmin.control.impl.UserMetier;
import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.entity.Address;
import fr.jasmin.entity.User;


public class MainGetAddress {

	public static void main(String[] args) {
		// test getUser BycEmail ok
		// test  getUser by id  ok
		// test List<User>  ok

//		IUserDao userDao = new UserDao();
		IUserMetier userMetier = new UserMetier();
		try {
			
//			System.out.println("========= getUser BycEmail===========================");
//			User user = userDao.getUserByEmail("Ahmedi@gmail.com");
//			System.out.println(user);
//			user.getAddresses().forEach(a -> System.out.println("  -> " + a));
//			user.getBankCardList().forEach(bk -> System.out.println("  -> " + bk));
//			
			System.out.println("=========getAddress by Userid===========================");
			Address address = userMetier.getAddressByUserId(11);
			System.out.println(address);
//			user2.getAddresses().forEach(a -> System.out.println("  -> " + a));
//			user2.getBankCardList().forEach(bk -> System.out.println("  -> " + bk));
			
			
//			System.out.println("========= getUser List users ===========================");
			System.out.println("==========================================");
			
//			List<User> users = userDao.getUser();
//			for (User user1 : users) {
//				System.out.println(user1);
//				user1.getAddresses().forEach(a -> System.out.println("  -> " + a));
//				user1.getBankCardList().forEach(bk -> System.out.println("  -> " + bk));
//			}
			
//			System.out.println("==========================================");
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
