package com.code.dal.orm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "ORDERS")
public class Order {
	private Long id;
	private String name;
	private User owner;
	private String status;
	private Place place;
	private Date date;
	private Float totalPrice;
	private Collection<OrderItem> orderItems = new ArrayList<OrderItem>();

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
	@SequenceGenerator(name = "sequence_generator", sequenceName = "ORDERS_SEQUENCE", allocationSize = 1)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER_USER_ID")
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PLACE_ID")
	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	@Column(name = "ORDER_DATE")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	public Collection<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Collection<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Transient
	public Float getTotalPrice() {
		totalPrice = 0.0F;
		for (OrderItem orderItem : getOrderItems())
			totalPrice += orderItem.getItem().getPrice() * orderItem.getCount();

		return totalPrice;
	}

	@Override
	public String toString() {
		return "Order:" + "\n\tID: " + id + "\n\tName: " + name + "\n\tOwner Name: " + owner.getName() + "\n\tStatus: "
				+ status + "\n\tPlace Name: " + place.getName() + "\n\tDate: " + date;
	}

}
