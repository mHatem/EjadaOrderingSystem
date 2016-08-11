package com.code.services;

import com.code.dal.orm.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserService {
	public static final String SCHEMA_NAME = "TEST";
	public static final String UNIQUE_USERNAME_CONSTRAINT_NAME = SCHEMA_NAME + "." + "SYS_C0046515";

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

	public User getUserById(Long id) {
		User user = null;

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("User.byId");
		query.setLong("id", id);

		List list = query.list();
		if (list.size() == 1)
			user = (User) list.get(0);

		session.getTransaction().commit();
		session.close();

		return user;
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		User user = null;

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("User.byUsernameAndPassword");
		query.setString("username", username);
		query.setString("password", password);

		List list = query.list();
		if (list.size() == 1)
			user = (User) list.get(0);

		session.getTransaction().commit();
		session.close();

		return user;
	}

	public void saveUser(User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(user);

		session.getTransaction().commit();
		session.close();
	}

	public void updateUser(User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.update(user);

		session.getTransaction().commit();
		session.close();
	}

	public User getUserByUsername(String username) {
		return getUserByUsernameAndPassword(username, null);
	}

	public User authenticateUser(String username, String password) {
		return getUserByUsernameAndPassword(username, password);
	}

}
