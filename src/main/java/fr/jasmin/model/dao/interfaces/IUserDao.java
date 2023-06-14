package fr.jasmin.model.dao.interfaces;

import java.util.List;

import fr.jasmin.entity.User;

public interface IUserDao {
	
	User addUser(User user) throws Exception ;
	User getUser(Integer id) throws Exception;
	User getUserByEmail(String email) throws Exception;
	void updateUser(User user) throws Exception;
	void removeUser(Integer id) throws Exception;
	void removeUser(User user) throws Exception;
	List<User> getUser() throws Exception;	


}
