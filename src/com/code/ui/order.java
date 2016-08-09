package com.code.ui;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.code.dal.orm.OrderView;
import com.code.services.orderService;

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
	
	public void displayAllOrders()
	{
		setOrders(orderService.getALL());
	}

	public order()
	{
		displayAllOrders();
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
