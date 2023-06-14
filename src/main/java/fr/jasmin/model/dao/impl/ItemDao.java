package fr.jasmin.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;
import fr.jasmin.model.connector.HibernateConnector;
import fr.jasmin.model.dao.interfaces.IItemDao;

public class ItemDao implements IItemDao {

	@Override
	public List<Item> getItemsByCategory(String categoryName) throws Exception {

		Session session = HibernateConnector.getSession();
		List<Item> items = new ArrayList<Item>();
		try {

			Query<Item> query = session.createQuery(
					"SELECT i FROM Item i INNER JOIN Category c ON c.id = i.category.id WHERE c.nomCategorie = :cname",
					Item.class);
			query.setParameter("cname", categoryName);
			items = query.list();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return items;
	}

	@Override
	public Item getArticle(Integer id) throws Exception {
		Session session = HibernateConnector.getSession();
		Item article = null;
		try {

			article = session.find(Item.class, id);

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return article;
	}

	@Override
	public List<Item> getArticles() throws Exception {
		Session session = HibernateConnector.getSession();
		List<Item> items = new ArrayList<Item>();
		
		try {

			Query<Item> query = session.createQuery("FROM Item i", Item.class);

			items = query.list();

			

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return items;
	}

	@Override
	public void updateArticle(Item article) throws Exception {
		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(article);
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
	public void updateArticleById(Integer id) throws Exception {
		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		Item item = null;
		try {
			item = session.find(Item.class, id);
			tx = session.beginTransaction();
			session.update(item);
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
	public void removeArticle(Integer id) throws Exception {
		Item item = new Item();
		item = getArticle(id);
		removeArticle(item);

	}

	@Override
	public void removeArticle(Item article) throws Exception {

		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.remove(article);
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

}
