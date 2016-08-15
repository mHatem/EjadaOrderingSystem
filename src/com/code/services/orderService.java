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
		@SuppressWarnings("deprecation")
		SessionFactory sessionFactory = SessionFactorySingleton.getSingleton().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.getNamedQuery("Order.all");
		@SuppressWarnings("unchecked")
		List<OrderView> orders = (List<OrderView>) query.list();

		session.getTransaction().commit();
		session.close();
		return orders;
	}

	public static void insert(Order r) {
		@SuppressWarnings("deprecation")
		SessionFactory sessionFactory = SessionFactorySingleton.getSingleton().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(r);

		session.getTransaction().commit();
		session.close();
	}

	public static void delete(Order r) {
		@SuppressWarnings("deprecation")
		SessionFactory sessionFactory = SessionFactorySingleton.getSingleton().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(r);
		session.getTransaction().commit();
		session.close();
	}

	public static List<OrderView> find(String userNAME, String placeNAME, String status, Long id, Long placeID,Long ownerID) {
		@SuppressWarnings("deprecation")
		SessionFactory sessionFactory = SessionFactorySingleton.getSingleton().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.getNamedQuery("search");
		if (userNAME == null || userNAME.trim().equals("")) {
			userNAME = "-1";
		}
		if (placeNAME == null || placeNAME.trim().equals("")) {
			placeNAME = "-1";
		}
		if (status == null || status.trim().equals("")) {
			status = "-1";
		}
		query.setString("ownerName", userNAME);
		query.setString("placeName", placeNAME);
		query.setString("status", status);
		query.setLong("id", id==null ? -1 : id);
		query.setLong("placeId", placeID == null ? -1 : placeID);
		query.setLong("ownerId", ownerID == null ? -1 : ownerID);
		@SuppressWarnings("unchecked")
		List<OrderView> orders = (List<OrderView>) query.list();
		session.getTransaction().commit();
		session.close();
		return orders;
	}
	
	public static void update(Order o)
	{
		SessionFactory sessionFactory = SessionFactorySingleton.getSingleton().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(o);
		session.getTransaction().commit();
		session.close();
		
	}

}
