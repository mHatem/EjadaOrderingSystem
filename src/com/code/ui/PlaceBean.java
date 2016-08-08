package com.code.ui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.code.services.PlaceService;


@ManagedBean (name = "place")
@SessionScoped

public class PlaceBean implements Serializable{
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
	
	public String addPlace(){
		PlaceService place = new PlaceService(); 
		place.insert(name, phoneNo);
		return "index";
	}
}
