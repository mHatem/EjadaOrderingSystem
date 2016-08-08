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
	private List<Place> places;
	
	
	public PlaceBean() {
	showAll();	
	}
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
	
	
	public String addPlace(){
		PlaceService place = new PlaceService(); 
		place.insert(name, phoneNo);
		return "index";
	}
	
	public List<Place> getPlaces() {
		return places;
	}
	public void setPlaces(List<Place> places) {
		this.places = places;
	}
	public List<Place> showAll (){
		PlaceService placeSer=new PlaceService();
		places= placeSer.retrieve();
		return places ;

	}
	
	
}
