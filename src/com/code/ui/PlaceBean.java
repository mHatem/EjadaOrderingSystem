package com.code.ui;

import java.io.Serializable;
import java.util.List;
//import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.code.dal.orm.Place;
import com.code.dal.orm.User;
import com.code.services.PlaceService;
//import com.code.services.UserService;
//import com.code.ui.user.Login;

@SuppressWarnings("serial")
@ManagedBean(name = "place")
@SessionScoped

public class PlaceBean implements Serializable {
	private String name;
	private String phoneNo;
	private List<Place> places;
	//private String userName;
	
	public PlaceBean() {
		
		places = null;
		showAll();
	}

	// edit 
	public void editPlace(Place place) {
		place.setSelected(true);
	}

	public void savePlace(Place place) {
	    PlaceService.updatePlace(place);
	    place.setSelected(false);
	}
	//////////////////////////////////////////////////////////////////
	
	
	// insert
	public String addPlace() {
		Place insertedPlace = new Place();
		insertedPlace.setName(name);
		insertedPlace.setPhoneNo(phoneNo);
		PlaceService.insertPlace(insertedPlace);
		places.add(insertedPlace);
		name = null;
		phoneNo = null;
		return null;
	}
	
	// show the list
	public List<Place> showAll() {
		places = PlaceService.retrievePlaces();
		return places;

	}
	
	// search by name
	public List<Place> searchPlaceName() {
		places = PlaceService.searchPlaces(name);
		name = null;
		phoneNo = null;
		return places;
	}
	
	//search by name and phone 
	public List<Place> searchPlace() {
			places = PlaceService.finalSearch(name, phoneNo);
			return places;
			}
		


	
	public void deletePlace(Place deletedPlace) {
		PlaceService.deletePlace(deletedPlace);
		places.remove(deletedPlace);
	}
	
	
	public String redirectToItems(Place place)
	{	
		Long placeId=place.getId();
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("placeId", placeId);
		return "Items?faces-redirct=true";
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

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}
	/*
	public String getUserName() {
		Map<String, Object> sessionMap=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Long userId=(Long) sessionMap.get(Login.SESSION_KEY_USER_ID);
		userName=UserService.getSingleton().getUserById(userId).getName();
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}*/
}