package com.code.dal.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@NamedQueries({
		@NamedQuery ( name = "Place_all", query = "SELECT p FROM Place p"  ),
		@NamedQuery ( name = "place.name",query="from Place where name = ? ")
		})

@Entity
@Table (name = "PLACES")
public class Place implements Serializable {
	private long id;
	private String name;
	private String phoneNo;
///	private Collection<PlacesItem> items = new ArrayList<PlacesItem>();

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
	@SequenceGenerator(name = "sequence_generator", sequenceName = "PLACES_SEQUENCE", allocationSize = 1)
	@Column(name = "ID" )
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name ="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name ="PHONE_NO")
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "place")
	/*
	public Collection<PlacesItem> getItems() {
		return items;
	}

	public void setItems(Collection<PlacesItem> items) {
		this.items = items;
	}
*/
	@Override
	public String toString() {
		return "Place:" + "\n\tID: " + id + "\n\tName: " + name + "\n\tPhone No.: " + phoneNo;
	}
}
