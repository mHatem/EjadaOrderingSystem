package com.code.dal.orm;

import javax.persistence.*;

@Entity(name = "ORDER_ITEMS")
public class OrderItem {
	private long id;
	private Order order;
	private PlacesItem item;
	private User user;
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

	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "ORDER_ID")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "PLACE_ITEM_ID")
	public PlacesItem getItem() {
		return item;
	}

	public void setItem(PlacesItem item) {
		this.item = item;
	}

	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "COUNT")
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Order Item:" + "\n\tID: " + id + "\n\tOrder ID: " + order.getId() + "\n\tItem Name: " + item.getName()
				+ "\n\tUser Name: " + user.getName() + "\n\tCount: " + count;
	}

}
