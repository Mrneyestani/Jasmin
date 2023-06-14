package fr.jasmin.model.dao.impl;

import java.util.List;

import javax.persistence.NamedQuery;
import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.jasmin.entity.Category;
import fr.jasmin.entity.User;
import fr.jasmin.enums.ProfileUserEnum;
import fr.jasmin.model.connector.HibernateConnector;
import fr.jasmin.model.dao.interfaces.IUserDao;

public class UserDao implements IUserDao {

	public UserDao() {

	}

	@Override
	public User addUser(User user) throws Exception {

		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			session.save(user);
			if (user.getAddresses() != null && !user.getAddresses().isEmpty()) {
				user.getAddresses().forEach(a -> session.save(a));
			}
			if (user.getPanier() != null && !user.getPanier().isEmpty()) {
				user.getPanier().forEach(p -> session.save(p));
			}

			tx.commit();

		} catch (RollbackException e) {
			tx.rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return user;
	}

	@Override
	public User getUser(Integer id) throws Exception {

		Session session = HibernateConnector.getSession();
		User user = null;
		try {
			user = session.find(User.class, id);

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return user;
	}

	@Override
	public void updateUser(User user) throws Exception {

		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(user);
			if (user.getPanier() != null && !user.getPanier().isEmpty()) {
				user.getPanier().forEach(p -> session.save(p));
			}
			tx.commit();

		} catch (Exception ex) {
			if (tx != null) {
				tx.rollback();
			}

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}

		}

	}

	@Override
	public void removeUser(Integer id) throws Exception {
		User magasinier = new User();
		magasinier = getUser(id);
		removeUser(magasinier);

	}

	@Override
	public void removeUser(User user) throws Exception {
		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.remove(user);
			tx.commit();

		} catch (Exception ex) {
			if (tx != null) {
				tx.rollback();
			}

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}

		}

	}

	@Override
	public List<User> getUser() throws Exception {
		Session session = HibernateConnector.getSession();
		List<User> userList;

		try {
			Query query = session.createQuery("from User");
			userList = query.list();
			for (User user : userList) {
			}

		} finally {
			;
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return userList;

	}

	@Override
	public User getUserByEmail(String email) throws Exception {

		Session session = HibernateConnector.getSession();
		User user = null;
		try {
			// requête JPQL
			Query<User> query = session.createQuery("FROM User u WHERE u.email = ?1", User.class);
			query.setParameter(1, email);
			user = query.uniqueResult(); // user = query.getSingleResult();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return user;
	}

}
