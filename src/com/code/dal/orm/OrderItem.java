package com.code.dal.orm;

import javax.persistence.*;

@Entity(name = "ORDER_ITEMS")
public class OrderItem {
	private long id;
	private Long orderId;
	private Long placeItemId;
	private Long userId;
	private int count;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
	@SequenceGenerator(name = "sequence_generator", sequenceName = "ORDER_ITEMSSEQUENCE", allocationSize = 1)
	@Column(name = "ID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "ORDER_ID")
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "PLACE_ITEM_ID")
	public Long getPlaceItemId() {
		return placeItemId;
	}

	public void setPlaceItemId(Long placeItemId) {
		this.placeItemId = placeItemId;
	}

	@Column(name = "USER_ID")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "COUNT")
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
