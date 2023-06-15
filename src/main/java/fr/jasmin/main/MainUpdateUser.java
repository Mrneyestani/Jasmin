package fr.jasmin.main;


import fr.jasmin.entity.User;
import fr.jasmin.model.dao.impl.UserDao;
import fr.jasmin.model.dao.interfaces.IUserDao;

public class MainUpdateUser {

	public static void main(String[] args) {
		//test upader user ok
		IUserDao userDao = new UserDao();
		
		try {
		
			
			User user = userDao.getUser(1);
			user.setLastName(user.getLastName() + "-MAJ");
			user.setFirstName(user.getFirstName() + "-MAJ");
			userDao.updateUser(user);
			
			
			System.out.println(user);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
