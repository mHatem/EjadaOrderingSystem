package com.code.dal.orm;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@NamedQueries({
		@NamedQuery(name = "OrderItemByOrder", query = "from com.code.dal.orm.OrderItemView AS OIV where (OIV.orderId = :OrderId or :OrderId = -1 )"),
		@NamedQuery(name = "OrderItemByUser", query = "from com.code.dal.orm.OrderItemView AS OIV where ( OIV.userId = :UserId or :UserId = -1) "),
		@NamedQuery(name = "FilterOrderItemView", query = "from com.code.dal.orm.OrderItemView AS OIV where ( :OrderId = -1 or OIV.orderId = :OrderId) and ( :Username = '-1' or lower(OIV.username) = :Username)and ( :ItemName = '-1' or lower(OIV.itemName) = :ItemName)"),
		@NamedQuery(name = "AllOrderItem", query = "from com.code.dal.orm.OrderItemView ")})
@Entity(name = "VW_ORDER_ITEM")
public class OrderItemView implements Serializable {
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

	private String userIdToCheck;

	private String countToCheck = "1";

	private String itemIdToCheck;

	private Long userIdHistory;

	private Float priceHistory;

	private Long itemIdHistory;

	private Integer countHistory;

	public void saveHistory() {
		if (userId == null)
			userIdToCheck = null;
		else
			userIdToCheck = userId.toString();
		if (count == null)
			countToCheck = null;
		else
			countToCheck = count.toString();
		if (itemId == null)
			itemIdToCheck = null;
		else
			itemIdToCheck = itemId.toString();
		userIdHistory = userId;
		priceHistory = price;
		itemIdHistory = itemId;
		countHistory = count;
	}

	public void RetriveHistory() {
		if (userIdHistory == null)
			userIdToCheck = null;
		else
			userIdToCheck = userIdHistory.toString();
		if (countHistory == null)
			countToCheck = null;
		else
			countToCheck = countHistory.toString();
		if (itemIdHistory == null)
			itemIdToCheck = null;
		else
			itemIdToCheck = itemIdHistory.toString();
		userId = userIdHistory;
		price = priceHistory;
		itemId = itemIdHistory;
		count = countHistory;
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

	@Transient
	public String getUserIdToCheck() {
		return userIdToCheck;
	}

	public void setUserIdToCheck(String userIdToCheck) {
		this.userIdToCheck = userIdToCheck;
	}

	@Transient
	public String getCountToCheck() {
		return countToCheck;
	}

	public void setCountToCheck(String countToCheck) {
		this.countToCheck = countToCheck;
	}

	@Transient
	public String getItemIdToCheck() {
		return itemIdToCheck;
	}

	public void setItemIdToCheck(String itemIdToCheck) {
		this.itemIdToCheck = itemIdToCheck;
	}

}