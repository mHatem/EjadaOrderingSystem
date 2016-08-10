package com.code.ui;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.facelets.FaceletContext;

import com.code.dal.orm.Order;
import com.code.dal.orm.OrderView;
import com.code.dal.orm.Place;
import com.code.services.orderService;

import oracle.jdbc.util.Login;

@SuppressWarnings("serial")
@ManagedBean(name = "order")
// don't forget to change it
@SessionScoped
public class order implements Serializable {
	private long id;
	private String name;
	private long ownerID;
	private String status;
	private long placeID;
	private Date date;
	private float totalPrice;
	private List<OrderView> orders;
	private String selectedPlace;
	
	
	public long extractPlaceID(String n)
	{
		PlaceBean bean= new PlaceBean();
		for(Place p : bean.getPlaces())
		{
		 if(p.getName() != null && p.getName().equals(n))
		 {
			 return p.getId();
		 }
		}
		return 0;
	}
	public void displayAllOrders()
	{
		setOrders(orderService.getALL());
	}

	public String add()
	{
		Map<String, Object> sessionMap=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Date dat =new Date();
		Order order = new Order();
		order.setName(this.name);
		order.setPlaceID(extractPlaceID(selectedPlace));
		order.setStatus(this.status);
		order.setOwnerID(sessionMap.get(Login.SESSION_KEY_USER_ID));
		order.setDate(dat);
		orderService.insert(order);
		return null;
	}
	public order()
	{
		displayAllOrders();
	}
	
	public String getSelectedPlace() {
		return selectedPlace;
	}
	public void setSelectedPlace(String selectedPlace) {
		this.selectedPlace = selectedPlace;
	}
	public long getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(long ownerID) {
		this.ownerID = ownerID;
	}

	public long getPlaceID() {
		return placeID;
	}

	public void setPlaceID(long placeID) {
		this.placeID = placeID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderView> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderView> orders) {
		this.orders = orders;
	}
	
	
}
