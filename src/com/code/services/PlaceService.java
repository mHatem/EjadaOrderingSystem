package com.code.services;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.code.dal.orm.Place;
public class PlaceService implements Serializable {
	public static void insertPlace(Place place ) {
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			session.save(place);

			session.getTransaction().commit();
			session.close();
			

	}
	public static List<Place> retrievePlaces (){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("Place_all");
		List<Place> places = (List<Place>) query.list();

		session.getTransaction().commit();
		session.close();
		return places;
	}
	public static void deletePlace(Place place){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.delete(place);
		session.getTransaction().commit();
		session.close();
	}
}