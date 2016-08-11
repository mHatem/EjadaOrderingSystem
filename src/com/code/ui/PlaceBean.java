package com.code.ui;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.code.dal.orm.Place;
import com.code.services.PlaceService;
import com.code.services.UserService;
import com.code.ui.user.Login;

@SuppressWarnings("serial")
@ManagedBean(name = "place")
@SessionScoped

public class PlaceBean implements Serializable {
	private String name;
	private String phoneNo;
	private List<Place> places;
	private String userName;
	
	///////////////////////////////////////////////////////// edit 
	public void editPlace(Place place) {
		place.setSelected(true);
	}

	public void savePlace(Place place) {
	    PlaceService.updatePlace(place);
	    place.setSelected(false);
	}
	//////////////////////////////////////////////////////////////////
	
	public PlaceBean() {
		
		places = null;
		showAll();
	}

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

	public List<Place> showAll() {
		places = PlaceService.retrievePlaces();
		return places;

	}
	
	public List<Place> searchPlaceName() {
		places = PlaceService.searchPlaces(name);
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
		return "Items";
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

	public String getUserName() {
		Map<String, Object> sessionMap=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Long userId=(Long) sessionMap.get(Login.SESSION_KEY_USER_ID);
		userName=UserService.getSingleton().getUserById(userId).getName();
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}