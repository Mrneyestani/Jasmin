package fr.jasmin.main;


import fr.jasmin.entity.User;
import fr.jasmin.model.dao.impl.UserDao;
import fr.jasmin.model.dao.interfaces.IUserDao;

public class MainDeleteUser {

	public static void main(String[] args) {
		//test delete ok

		IUserDao userDao = new UserDao();

		try {
			
			User user = userDao.getUser(5);
			System.out.println(user);
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println(user.getId());
			userDao.removeUser(2);
			System.out.println(user);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
