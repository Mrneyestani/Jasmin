package fr.jasmin.model.dao.impl;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.jasmin.entity.Param;
import fr.jasmin.model.connector.HibernateConnector;
import fr.jasmin.model.dao.interfaces.IParamDao;

public class ParamDao implements IParamDao {

	@Override
	public void addSecurityKey(Param param) throws Exception {

		Session session = HibernateConnector.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(param);
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
	public Param getSecurityKeyById(Integer id) throws Exception {
		Session session = HibernateConnector.getSession();
		Param param = null;
		try {
			param = session.find(Param.class, id);

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return param;

	}

}
