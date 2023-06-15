package fr.jasmin.main;

import java.util.List;


import fr.jasmin.entity.User;
import fr.jasmin.model.dao.impl.UserDao;
import fr.jasmin.model.dao.interfaces.IUserDao;


public class MainGetUser {

	public static void main(String[] args) {
		// test getUser BycEmail ok
		// test  getUser by id  ok
		// test List<User>  ok

		IUserDao userDao = new UserDao();
		
		try {
			
			System.out.println("========= getUser BycEmail===========================");
			User user = userDao.getUserByEmail("Ahmedi@gmail.com");
			System.out.println(user);
			user.getAddresses().forEach(a -> System.out.println("  -> " + a));
			user.getBankCardList().forEach(bk -> System.out.println("  -> " + bk));
			
			System.out.println("=========getUser by id===========================");
			User user2 = userDao.getUser(1);
			System.out.println(user2);
			user2.getAddresses().forEach(a -> System.out.println("  -> " + a));
			user2.getBankCardList().forEach(bk -> System.out.println("  -> " + bk));
			
			
			System.out.println("========= getUser List users ===========================");
			System.out.println("==========================================");
			
			List<User> users = userDao.getUser();
			for (User user1 : users) {
				System.out.println(user1);
				user1.getAddresses().forEach(a -> System.out.println("  -> " + a));
				user1.getBankCardList().forEach(bk -> System.out.println("  -> " + bk));
			}
			
			System.out.println("==========================================");
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
