package fr.jasmin.main;

import java.util.ArrayList;
import java.util.List;

import fr.jasmin.control.impl.UserMetier;
import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.entity.User;
import fr.jasmin.enums.ProfileUserEnum;


public class MainGetUser {

	public static void main(String[] args) {
		// test getUser BycEmail ok
		// test  getUser by id  ok
		// test List<User>  ok
	
//		IUserDao userDao = new UserDao();
		IUserMetier userMetier = new UserMetier();
		try {
			
//			System.out.println("========= getUsers===========================");
//			List<User> users = new ArrayList<User>();
//			users = userMetier.getUsers();
////			System.out.println(users);
//			users.forEach(u -> System.out.println("  -> " + u.getFirstName()+ " " +u.getLastName()));
			
//			System.out.println("========= getUserByProfile===========================");
//			List<User> users = new ArrayList<User>();
//			users = userMetier.getUsersByProfile("Magasinier");
//			System.out.println(users);
//				users.forEach(u -> System.out.println("  -> " + u.getFirstName()+ " " +u.getLastName()));
//		
			System.out.println("========= getProfiles===========================");	
//			List<String> profiles = new ArrayList<String>();
//			for (ProfileUserEnum oneValue : ProfileUserEnum.values()) {
//				 profiles.add(oneValue.getValue());
//			}
//			System.out.println(profiles);
			System.out.println(ProfileUserEnum.getProfiles());
//			System.out.println(ProfileUserEnum.getProfiles());
//			System.out.println("========= getUser ByEmail===========================");
//			User user = userDao.getUserByEmail("Ahmedi@gmail.com");
//			System.out.println(user);
//			user.getAddresses().forEach(a -> System.out.println("  -> " + a));
//			user.getBankCardList().forEach(bk -> System.out.println("  -> " + bk));
//			
//			System.out.println("=========getUser by id===========================");
//			User user2 = userMetier.getUserById(5);
//			System.out.println(user2);
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
