package com.code.dal.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "PLACE_ITEM")
@NamedQueries({ @NamedQuery(name = "getItems", query = "from PlacesItem pt where pt.placeId =?"),
	@NamedQuery(name = "getOpenOrders", query = "Select distinct o.status from Order o , OrderItem oi , PlacesItem pt "
			+ "where oi.order=o.id and pt.id=oi.item and pt.id=?") })
public class PlacesItem {
	private Long id;
	private Long placeId;
	private String name;
	private String description;
	private Float price;
	private boolean updateButtonClicked;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
	@SequenceGenerator(name = "sequence_generator", sequenceName = "PLACE_ITEMS_SEQUENCE", allocationSize = 1)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "place_id")
	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "PRICE")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Transient
	public boolean isUpdateButtonClicked() {
		return updateButtonClicked;
	}

	public void setUpdateButtonClicked(boolean updateButtonClicked) {
		this.updateButtonClicked = updateButtonClicked;
	}

}
