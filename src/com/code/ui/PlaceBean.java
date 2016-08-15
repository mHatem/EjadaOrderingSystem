package com.code.ui;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.hibernate.exception.ConstraintViolationException;

import com.code.dal.orm.Place;
import com.code.dal.orm.UserRole;
import com.code.services.PlaceService;
import com.code.services.UserService;
import com.code.services.orderService;
import com.code.ui.user.Login;

@SuppressWarnings("serial")
@ManagedBean(name = "place")
@SessionScoped

public class PlaceBean implements Serializable {
	private String name;
	private String phoneNo;
	private List<Place> places;
	private String userName;
	private UIComponent addButton;
	private boolean flagDeleteUsedPlace;
	
	public boolean isFlagDeleteUsedPlace() {
		return flagDeleteUsedPlace;
	}

	public void setFlagDeleteUsedPlace(boolean flagDeleteUsedPlace) {
		this.flagDeleteUsedPlace = flagDeleteUsedPlace;
	}

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
		if (name == null || name.trim().equals(""))
		{
			FacesMessage message = new FacesMessage("Invalid add");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(addButton.getClientId(context), message);

		}
		else
		{	
		Place insertedPlace = new Place();
		insertedPlace.setName(name);
		insertedPlace.setPhoneNo(phoneNo);
		PlaceService.insertPlace(insertedPlace);
		places.add(insertedPlace);
		name = null;
		phoneNo = null;}
		return null;
	}
	
	public UIComponent getaddButton() {
		return addButton;
	}

	public void setaddButton(UIComponent addButton) {
		this.addButton = addButton;
	}

	// show the list
	public List<Place> showAll() {
		places = PlaceService.retrievePlaces();
		return places;

	}
	
	public void resetAllInputText(){
		name=null;
		phoneNo=null;
	}
	
	
	//search by name and phone 
	public List<Place> searchPlace() {
			places = PlaceService.finalSearch(name, phoneNo);
			return places;
	}
		
	public void deletePlace(Place deletedPlace) {
		flagDeleteUsedPlace = false;
		try {
		PlaceService.deletePlace(deletedPlace);
		places.remove(deletedPlace);
		} catch (ConstraintViolationException e){
			System.err.println("this place is used already, u can't delete it now");
			flagDeleteUsedPlace=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	
	
	public String redirectToItems(Place place)
	{	
		Long placeId=place.getId();
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("placeId", placeId);
		return "Items?faces-redirct=true&id="+placeId;
	}
	private Map<String, Object> getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}
	public String signOut(){
		getSessionMap().put(Login.SESSION_KEY_USER_ID, null);
		getSessionMap().put(Login.SESSION_KEY_USER_ROLE, null);
		return "index?faces-redirect=true";
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
	
	public boolean adminPlaces(){
		Map<String, Object> sessionMap=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		String userRole = (String) sessionMap.get(Login.SESSION_KEY_USER_ROLE);
		return userRole.equals(UserRole.ADMIN_PLACES);
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