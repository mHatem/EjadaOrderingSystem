package com.code.dal.orm;

import java.io.Serializable;

import javax.persistence.*;
@SuppressWarnings("serial")
@NamedQueries({
	@NamedQuery ( name = "OrderItemByOrder", query = "from com.code.dal.orm.OrderItemView AS OIV where OIV.orderId = :OrderId "  ),
	@NamedQuery ( name = "OrderItemByUser",query= "from com.code.dal.orm.OrderItemView AS OIV where OIV.userId = :UserId ")
	})
@Entity(name = "VW_ORDER_ITEM")
public class OrderItemView implements Serializable{
	private Long id;
	private Long orderId;
	private Long userId;
	private Long placeId;
	private String placeName;
	private String userName;
	private String username;
	private Integer count = 1;
	private Float price;
	private Float total;
	private Long itemId;
	private String itemName;
	private Boolean selected = false;
	private Boolean toAdd = false;
	@Transient
	private Long userIdHistory;
	@Transient
	private Float priceHistory;
	@Transient
	private Long itemIdHistory;
	@Transient
    private Integer countHistory;

	public void saveHistory()
	{
		userIdHistory = userId;
		priceHistory = price;
		itemIdHistory = itemId;
		countHistory = count;
	}
	public void RetriveHistory()
	{
		 userId = userIdHistory ;
		 price = priceHistory ;
		 itemId = itemIdHistory ;
		 count  = countHistory ;
		
	}
	
	
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

	@Column(name = "ITEM_NAME")
	public String getItemName() {
		return itemName;
	}

	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "ITEM_ID")
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	@Transient
	public Boolean getToAdd() {
		return toAdd;
	}

	public void setToAdd(Boolean toAdd) {
		this.toAdd = toAdd;
	}

	@Transient
	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	@Column(name = "USERNAME")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}