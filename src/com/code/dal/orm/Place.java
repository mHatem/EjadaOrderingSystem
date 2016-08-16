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
import javax.persistence.Transient;

@SuppressWarnings("serial")
@NamedQueries({
		@NamedQuery ( name = "Place_all", query = "SELECT p FROM Place p"  ),
		@NamedQuery ( name = "Place.name",query="from Place where name = ? "),
		@NamedQuery(name = "placeSearchByNameAndPhone", 
				query = "SELECT p FROM Place p WHERE  (:name = '-1' OR p.name =:name ) "
						+ "AND (:phoneNo = '-1' or p.phoneNo = :phoneNo )"),
		@NamedQuery(name = "placeItem", 
				query = "SELECT p FROM Place p ,PlacesItem pi WHERE  (pi.placeId=p.id) "
							+ "AND (:itemName = '-1' or pi.name = :itemName )")})
@Entity
@Table (name = "PLACES")
public class Place implements Serializable {
	private Long id;
	private String name;
	private String phoneNo;
	private Boolean selected;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
	@SequenceGenerator(name = "sequence_generator", sequenceName = "PLACES_SEQUENCE", allocationSize = 1)
	@Column(name = "ID" )
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@Override
	public String toString() {
		return "Place:" + "\n\tID: " + id + "\n\tName: " + name + "\n\tPhone No.: " + phoneNo;
	}

	@Transient
	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
