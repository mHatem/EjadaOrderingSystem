package com.code.ui;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;;

@ManagedBean(name="index")
@ViewScoped
public class IndexView 
{
	private Long PlaceID;
	
	public Long getPlaceID() {
		return PlaceID;
	}

	public void setPlaceID(Long placeID) {
		PlaceID = placeID;
	}

	public String goToItem()
	{
		if(PlaceID == null)
		{
			return "index";
		}
		else
		{
			System.out.println(":::::"+PlaceID);
			return "Items?faces-redirect=true&id="+PlaceID;
		}
	}
}
