package com.code.services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean
@ApplicationScoped
public class SessionFactoryBean implements Serializable {
	private SessionFactory sessionFactory = null;

	public SessionFactoryBean() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
