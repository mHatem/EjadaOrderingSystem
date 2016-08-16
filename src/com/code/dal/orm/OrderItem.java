package com.code.dal.orm;

import java.io.Serializable;

import javax.persistence.*;
@SuppressWarnings("serial")
@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem implements Serializable {
	private Long id;
	private Long order;
	private Long item;
	private Long user;
	private Integer count;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
	@SequenceGenerator(name = "sequence_generator", sequenceName = "ORDER_ITEMSSEQUENCE", allocationSize = 1)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(name = "ORDER_ID")
	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	
	@Column(name = "PLACE_ITEM_ID")
	public Long getItem() {
		return item;
	}

	public void setItem(Long item) {
		this.item = item;
	}


	@Column(name = "USER_ID")
	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}
    
	@Column(name = "COUNT")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/*@Override
	public String toString() {
		return "Order Item:" + "\n\tID: " + id + "\n\tOrder ID: " + order.getId() + "\n\tItem Name: " + item.getName()
				+ "\n\tUser Name: " + user.getName() + "\n\tCount: " + count;
	}*/

}
