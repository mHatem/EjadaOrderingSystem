package com.code.dal.orm;

import javax.persistence.*;

@Entity(name ="VW_ORDER_ITEM")
public class OrderItemView {
	private Long id;
	private Long orderId;
	private Long userId;
	private Long placeId;
	private String placeName;
	private String userName;
	private Integer count;
	private Float price;
	private Float total;

	@Id
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ORDER_ID")
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "USER_ID")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "PLACE_ID")
	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
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
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "PRICE", columnDefinition = "NUMBER(5, 2)")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Column(name = "TOTAL", columnDefinition = "NUMBER(5, 2)")
	public float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}
}