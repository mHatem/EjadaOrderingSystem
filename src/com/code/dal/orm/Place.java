package com.code.dal.orm;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@NamedQuery ( name = "Place.byName", query = "from Place where name = ? and  phoneNo =  ?" )
@Entity
@Table (name = "PLACES")
public class Place implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
	@SequenceGenerator(name = "sequence_generator", sequenceName = "PLACES_SEQUENCE", allocationSize = 1)
	@Column(name = "ID" )

	private long id;
	private String name;
	private String phoneNo;
///	private Collection<PlacesItem> items = new ArrayList<PlacesItem>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PHONE_NO", length = 50)
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
