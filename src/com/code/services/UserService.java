package com.code.services;

import com.code.dal.orm.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UserService {
	private static UserService singleton = null;
	private SessionFactory sessionFactory = null;

	private UserService() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	public static UserService getSingleton() {
		if (singleton == null)
			singleton = new UserService();

		return singleton;
	}

	public void saveUser(User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(user);

		session.getTransaction().commit();
		session.close();
	}
}
