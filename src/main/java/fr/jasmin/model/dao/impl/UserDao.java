package fr.jasmin.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NamedQuery;
import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.jasmin.entity.Address;
import fr.jasmin.entity.BankCard;
import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;
import fr.jasmin.entity.User;
import fr.jasmin.enums.ProfileUserEnum;
import fr.jasmin.model.connector.HibernateConnector;
import fr.jasmin.model.dao.interfaces.IUserDao;

public class UserDao implements IUserDao {

	public UserDao() {

	}
//==========================================================
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
	//==========================================================
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
	//==========================================================
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
	
//===========================================================
	@Override
	public void addAddress(Address address) throws Exception {
		
		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(address);
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
//===========================================================
	@Override
	public void updateAddress(Address address) throws Exception {
		
		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(address);
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
//========================================================
	@Override
	public void removeUser(Integer id) throws Exception {
		User magasinier = new User();
		magasinier = getUser(id);
		removeUser(magasinier);

	}
	//========================================================
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
	//========================================================
	@Override
	public List<User> getUsers() throws Exception {
		Session session = HibernateConnector.getSession();
		List<User> users = new ArrayList<User>();

		try {
			Query<User> query = session.createQuery("FROM User u", User.class);
			users = query.list();

		} finally {
			;
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return users;

	}
	//========================================================
			@Override
			public List<User> getUsersByProfile(String profile) throws Exception {
				Session session = HibernateConnector.getSession();
				List<User> users = new ArrayList<User>();
				
				try {
					// requête JPQL
					Query<User> query = session.createQuery("FROM User u WHERE u.profile = ?1", User.class);
					query.setParameter(1, profile);
					users = query.list();

				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
				return users;
			}
	//========================================================
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
	
	//========================================================
	@Override
	public Address getAddressByUserId(Integer userId) throws Exception {
		
		Session session = HibernateConnector.getSession();
		
		Address address = null;
		
		try {
			// requête JPQL
			Query<Address> query = session.createQuery("FROM Address a WHERE a.user.id = ?1", Address.class);
			query.setParameter(1, userId);
			address = query.uniqueResult(); // user = query.getSingleResult();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}		
		return address;
	}
	
	
	
	//========================================================
		@Override
		public BankCard getCarteByUserId(Integer userId) throws Exception {
			
			Session session = HibernateConnector.getSession();
			
			BankCard carte = null;
			
			try {
				// requête JPQL
				Query<BankCard> query = session.createQuery("FROM BankCard c WHERE c.user.id = ?1", BankCard.class);
				query.setParameter(1, userId);
				carte = query.uniqueResult(); // user = query.getSingleResult();

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}		
			return carte;
		}
//==============================================================
	@Override
	public BankCard addCarte(BankCard carte) throws Exception {

		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			session.save(carte);
			tx.commit();

		} catch (RollbackException e) {
			tx.rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return carte;
	}
	//==============================================================
		@Override
		public BankCard updateCarte(BankCard carte) throws Exception {

			Session session = HibernateConnector.getSession();
			Transaction tx = null;
			try {

				tx = session.beginTransaction();
				session.update(carte);
				tx.commit();

			} catch (RollbackException e) {
				tx.rollback();
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
			return carte;
		}
	
}
	
