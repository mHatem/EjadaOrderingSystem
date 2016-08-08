package com.code.dal.orm;

import javax.persistence.*;

@Entity(name ="VW_ORDER_ITEM")
public class OrderItemView {
	private long id;
	private long orderId;
	private long userId;
	private long placeId;
	private String placeName;
	private String userName;
	private int count;
	private float price;
	private float total;

	@Id
	@Column(name = "ID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "ORDER_ID")
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "USER_ID")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "PLACE_ID")
	public long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(long placeId) {
		this.placeId = placeId;
	}

	@Column(name = "PLACE_NAME")
	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "COUNT")
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Column(name = "PRICE", columnDefinition = "NUMBER(5, 2)")
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Column(name = "TOTAL", columnDefinition = "NUMBER(5, 2)")
	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
}