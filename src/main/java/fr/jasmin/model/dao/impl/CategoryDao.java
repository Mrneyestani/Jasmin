package fr.jasmin.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.jasmin.entity.Category;
import fr.jasmin.model.connector.HibernateConnector;
import fr.jasmin.model.dao.interfaces.ICategoryDao;

public class CategoryDao implements ICategoryDao {

	public CategoryDao() {

	}

	//------------------------------------------------------------------------------------------
	@Override
	public Category addCategory(Category category) throws Exception {
		Session session = HibernateConnector.getSession();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();
			session.save(category);
			if (category.getItems() != null && !category.getItems().isEmpty()) {
				category.getItems().forEach(item -> session.save(item));
			}
			tx.commit();

		} catch (RollbackException e) {
			tx.rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return category;
	}

	//------------------------------------------------------------------------------------------
	@Override
	public Category getCategory(Integer id) throws Exception {
		Session session = HibernateConnector.getSession();
		Category category = null;
		try {

			category = session.find(Category.class, id);

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return category;
	}
	
	//------------------------------------------------------------------------------------------
	@Override
	public Category getCategoryByNom(String nomCategorie) throws Exception {
		Session session = HibernateConnector.getSession();
		Category category = null;
		try {
			
//			category = session.find(Category.class, nomCategory);
			Query<Category> query = session.createQuery("FROM Category c WHERE c.nomCategorie = ?1", Category.class);
			query.setParameter(1, nomCategorie);
			category = query.uniqueResult();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return category;
	}
	//------------------------------------------------------------------------------------------
//	@Override
//	public User getUserByEmail(String email) throws Exception {
//		
//		Session session = HibernateConnector.getSession();
//		User user = null;
//		try {
			// requête JPQL
//			Query<User> query = session.createQuery("FROM User u WHERE u.email = ?1", User.class);
//			query.setParameter(1, email);
//			user = query.uniqueResult();   //user = query.getSingleResult();

			// requête prédéfinie
//			Query<User> query = session.createNamedQuery("User:findByEmail", User.class);
//			query.setParameter("email", email);
//			user = query.uniqueResult();
//			
//		} finally {
//			if (session != null && session.isOpen()) {
//				session.close();
//			}
//		}
//		return user;
//	}
	
	//------------------------------------------------------------------------------------------
	
	@Override
	public void updateCategory(Category category) throws Exception {
		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(category);
//			if (category.getItems() != null && !category.getItems().isEmpty()) {
//				category.getItems().forEach(item -> session.update(item));
//			}
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

	//------------------------------------------------------------------------------------------
	@Override
	public void removeCategory(Integer id) throws Exception {
		Category category = new Category();
		category = getCategory(id);
		removeCategory(category);

	}

	//------------------------------------------------------------------------------------------
	@Override
	public void removeCategory(Category category) throws Exception {
		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.remove(category);
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
	//------------------------------------------------------------------------------------------
	@Override
	public List<String> getCategories() throws Exception {
		Session session = HibernateConnector.getSession();
		List<String> categories = new ArrayList<String>();

		try {
			Query<String> query = session.createQuery("SELECT c.nomCategorie FROM Category c", String.class);
			categories = query.list();
		} finally {
			;
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return categories;

	}
	
	//------------------------------------------------------------------------------------------

	
	@Override
	public List<Category> getCategoriess() throws Exception {
		
		Session session = HibernateConnector.getSession();
		List<Category> categories = new ArrayList<Category>();
		try {
			
			Query<Category> query = session.createQuery("FROM Category a", Category.class);
			
			categories = query.list();  
			
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return categories;
	}

	//------------------------------------------------------------------------------------------
	@Override
	public List<Category> getCategorieList() throws Exception {
		Session session = HibernateConnector.getSession();
		List<Category> categorieList = new ArrayList<Category>();

		try {
			Query<Category> query = session.createQuery("FROM Category a", Category.class);
			categorieList = query.list();
		} finally {
			;
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return categorieList;
	}

}
