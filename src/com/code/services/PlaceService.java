package com.code.services;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.code.dal.orm.Place;
@SuppressWarnings("serial")
public class PlaceService implements Serializable {
	
	public static void insertPlace(Place place ) {
			SessionFactory sessionFactory = SessionFactorySingleton.getSingleton().getSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			session.save(place);

			session.getTransaction().commit();
			session.close();
			

	}
	
	public static void updatePlace(Place place ) {
		SessionFactory sessionFactory = SessionFactorySingleton.getSingleton().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.update(place);

		session.getTransaction().commit();
		session.close();
		

	}

	public static List<Place> retrievePlaces (){
		SessionFactory sessionFactory = SessionFactorySingleton.getSingleton().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("Place_all");
		List<Place> places = (List<Place>) query.list();

		session.getTransaction().commit();
		session.close();
		return places;
	}
	
	
	public static List<Place> finalSearch(String name, String phone) {
		
		SessionFactory sessionFactory = SessionFactorySingleton.getSingleton().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.getNamedQuery("placeSearchByNameAndPhone");
		if (name == null || name.trim().equals("")) {
			name = "-1";
		}
		if (phone == null || phone.trim().equals("")) {
			phone = "-1";
		}
		
		query.setString("name", name);
		query.setString("phoneNo", phone);
		
		@SuppressWarnings("unchecked")
		List<Place> place = (List<Place>) query.list();
		session.getTransaction().commit();
		session.close();
		return place;
	}

public static List<Place> SearchItems(String sandwitch) {
		
		SessionFactory sessionFactory = SessionFactorySingleton.getSingleton().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.getNamedQuery("placeItem");
		if (sandwitch == null || sandwitch.trim().equals("")) {
			sandwitch = "-1";
		}
		query.setString("itemName", sandwitch);
		
		@SuppressWarnings("unchecked")
		List<Place> place = (List<Place>) query.list();
		session.getTransaction().commit();
		session.close();
		return place;
	}
	
	public static void deletePlace(Place place){
		SessionFactory sessionFactory = SessionFactorySingleton.getSingleton().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.delete(place);
		
		session.getTransaction().commit();
		session.close();
	}
}