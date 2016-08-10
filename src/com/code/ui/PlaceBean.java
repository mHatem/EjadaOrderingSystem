package com.code.ui;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.code.dal.orm.Place;
import com.code.services.PlaceService;

@SuppressWarnings("serial")
@ManagedBean(name = "place")
@SessionScoped

public class PlaceBean implements Serializable {
	private String name;
	private String phoneNo;
	private List<Place> places;
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
}