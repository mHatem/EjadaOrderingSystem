package com.code.services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

public class SessionFactorySingleton implements Serializable {
	private static SessionFactorySingleton sessionFactorySingleton = null;
	private static SessionFactory sessionFactory = null;

	private SessionFactorySingleton() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	public static SessionFactorySingleton getSingleton() {
		if (sessionFactorySingleton == null) {
			sessionFactorySingleton = new SessionFactorySingleton();
		}
		return sessionFactorySingleton;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
