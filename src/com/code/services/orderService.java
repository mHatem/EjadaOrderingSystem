package com.code.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.code.dal.orm.Order;
import com.code.dal.orm.OrderView;

public class orderService {

	public static List<OrderView> getALL() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.getNamedQuery("Order.all");
		@SuppressWarnings("unchecked")
		List<OrderView> orders = (List<OrderView>) query.list();
		return orders;
	}

	public static void insert(Order r) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(r);

		session.getTransaction().commit();

	}
	
	public static void delete(Order r)
	{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(r);
		session.getTransaction().commit();
		
	}

}
