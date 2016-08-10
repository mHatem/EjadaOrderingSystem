package com.code.dal.orm;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class Order {
	private Long id;
	private String name;
	private Long ownerID;
	private String status;
	private Long placeID;
	private Date date;

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

	
	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@Column(name = "ORDER_DATE")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	@Column(name = "OWNER_USER_ID")
	public Long getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(Long ownerID) {
		this.ownerID = ownerID;
	}


	@Column(name = "PLACE_ID")
	public Long getPlaceID() {
		return placeID;
	}

	public void setPlaceID(Long placeID) {
		this.placeID = placeID;
	}
	
//@Transient
//	public float getTotalPrice() {
//		totalPrice = 0;
//		for (OrderItem orderItem : getOrderItems())
//			totalPrice += orderItem.getItem().getPrice() * orderItem.getCount();
//
//		return totalPrice;
//	}

//	@Override
//	public String toString() {
//		return "Order:" + "\n\tID: " + id + "\n\tName: " + name + "\n\tOwner Name: " + owner.getName() + "\n\tStatus: "
//				+ status + "\n\tPlace Name: " + place.getName() + "\n\tDate: " + date;
//	}

	
}
