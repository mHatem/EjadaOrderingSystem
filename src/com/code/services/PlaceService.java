package com.code.services;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.code.dal.orm.Place;
public class PlaceService implements Serializable {
	private String name;
	private String phoneNo;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	
	public void insert(String name,String phoneNo) {
		
			Place place = new Place();
			place.setName(name);
			place.setPhoneNo(phoneNo);
           
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			session.save(place);

			session.getTransaction().commit();
			session.close();
			

	}
	public List<Place> retrieve (){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("Place_all");
		List<Place> places = (List<Place>) query.list();

		session.getTransaction().commit();
		session.close();
		return places;
	}

	
}
