package fr.jasmin.model.dao.impl;

import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.jasmin.entity.ItemCart;
import fr.jasmin.model.connector.HibernateConnector;
import fr.jasmin.model.dao.interfaces.IItemCartDao;

public class ItemCartDao implements IItemCartDao {

	public ItemCartDao() {

	}

	@Override
	public ItemCart addItemCart(ItemCart itemCart) throws Exception {
		Session session = HibernateConnector.getSession();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();
			session.save(itemCart.getItem());
			session.save(itemCart.getUser());
			session.save(itemCart);
			tx.commit();

		} catch (RollbackException e) {
			tx.rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return itemCart;
	}

	@Override
	public ItemCart getItemCart(Integer id) throws Exception {
		Session session = HibernateConnector.getSession();
		ItemCart itemCart = null;
		try {
			itemCart = session.find(ItemCart.class, id);

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return itemCart;
	}

	@Override
	public void updateItemCart(ItemCart itemCart) throws Exception {
		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(itemCart);
			tx.commit();

		} catch (RollbackException e) {
			tx.rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

	}

	@Override
	public void removeItemCart(Integer id) throws Exception {
		ItemCart itemCart = new ItemCart();
		itemCart = getItemCart(id);
		removeItemCart(itemCart);

	}

	@Override
	public void removeItemCart(ItemCart itemCart) throws Exception {
		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.remove(itemCart);
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
	public List<ItemCart> getItemCartList() throws Exception {
		Session session = HibernateConnector.getSession();
		List<ItemCart> itemCartList;

		try {

			Query query = session.createQuery("from ItemCart");
			itemCartList = query.list();

		} finally {
			;
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return itemCartList;
	}

}
