package fr.jasmin.main;

import org.hibernate.Session;

import fr.jasmin.model.connector.HibernateConnector;

public class TestHibernate {

	public static void main(String[] args) {

		Session session = null;
		try {
			session = HibernateConnector.getSession();
			System.out.println(session);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}			
		}
	}

}
