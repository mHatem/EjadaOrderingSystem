package com.code.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.code.dal.orm.Place;
import com.code.services.PlaceService;




@ManagedBean (name = "place")
@SessionScoped

public class PlaceBean implements Serializable{
	private String name;
	private String phoneNo;
	private Collection<PlaceService>placeSer= new ArrayList<PlaceService>();
	
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
	
	public Collection<PlaceService> getPlaceSer() {
		return placeSer;
	}
	public void setPlaceSer(Collection<PlaceService> placeSer) {
		this.placeSer = placeSer;
	}
	public String addPlace(){
		PlaceService place = new PlaceService(); 
		place.insert(name, phoneNo);
		return "index";
	}
	public List<Place> showAll (){
		PlaceService placeSer=new PlaceService();
		List<Place> places= placeSer.retrieve();
		return places ;

	}
}
