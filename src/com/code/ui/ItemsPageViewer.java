package com.code.ui;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.*;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import com.code.dal.orm.*;
import com.code.services.Service;
@ManagedBean (name="item")
@ViewScoped
public class ItemsPageViewer 
{
	IndexView IV=new IndexView();
	private String itemName;
	private Float priceFrom;
	private Float priceTo;
	private List <PlacesItem> itemList;
	
	
	public ItemsPageViewer() {
		System.out.println("inside constructor");
	}
	public IndexView getIV() {
		return IV;
	}
	public void setIV(IndexView iV) {
		IV = iV;
	}
	public List<PlacesItem> getItemList() {
		String idParameter=((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("id");
		if(idParameter != null)
		{
		Long placeID=Long.valueOf(idParameter);
		itemList=Service.getItemsList(placeID.longValue());
		}
		return itemList;
	}
	public void setItemList(List<PlacesItem> itemList) {
		this.itemList = itemList;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Float getPriceFrom() {
		return priceFrom;
	}
	public void setPriceFrom(Float priceFrom) {
		this.priceFrom = priceFrom;
	}
	public Float getPriceTo() {
		return priceTo;
	}
	public void setPriceTo(Float priceTo) {
		this.priceTo = priceTo;
	}
	public List <PlacesItem> Search()
	{
		return itemList;
	}
	
}
