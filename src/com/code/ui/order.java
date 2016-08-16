package com.code.ui;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.code.OrderStatusEnum;
import com.code.dal.orm.Order;
import com.code.dal.orm.OrderView;
import com.code.dal.orm.Place;
import com.code.dal.orm.User;
import com.code.services.PlaceService;
import com.code.services.UserService;
import com.code.services.orderService;
import com.code.ui.user.Login;

@SuppressWarnings("serial")
@ManagedBean(name = "order")
// don't forget to change it
@SessionScoped
public class order implements Serializable {
	private long id;
	private String name;
	private long ownerID;

	private String status;
	private Long placeID;
	private Date date;
	private float totalPrice;
	private List<OrderView> orders;
	private String selectedPlace;
	private String ownerName;
	private String SUName;
	private String SPName;
	private String SNAme;
	private Long SID;
	private String errorMsg;
	private Collection <User> userList;
	private List <Place> placeList;
	
	public void addItem(OrderView ord)
	{
		Long orderId = ord.getId();
		try{
		FacesContext.getCurrentInstance().getExternalContext().redirect("orderItem.jsf?ORDERID="+orderId);
		return;
		}catch(Exception ea){return;}
	}

	public order() {
		displayAllOrders();
		userList = UserService.getSingleton().getAllUsers();
		placeList = PlaceService.retrievePlaces(); 
	}

	public void edit(OrderView o) {
		OrderView.edit(o);
	}

	public long extractPlaceID(String n) {
		PlaceBean bean = new PlaceBean();
		for (Place p : bean.getPlaces()) {
			if (p.getName() != null && p.getName().equals(n)) {
				return p.getId();
			}
		}
		return 0;
	}

	public void search() {
		setOrders(orderService.find(SUName, SPName, SNAme, SID, null, null));

	}

	public void displayAllOrders() {
		SUName = null;
		SPName = null;
		SNAme = null;
		SID = null;
		setOrders(orderService.getALL());
	}

	public void add() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Date dat = new Date();
		Order order = new Order();
		order.setName(this.name);
		order.setPlaceID(extractPlaceID(selectedPlace));
		order.setStatus(OrderStatusEnum.OPENED.getCode());
		order.setOwnerID((Long) sessionMap.get(Login.SESSION_KEY_USER_ID));
		order.setDate(dat);
		orderService.insert(order);
		displayAllOrders();
		

	}

	public void cancel(Order ToCancel) {
		ToCancel.setStatus(OrderStatusEnum.CANCELED.getCode());
		orderService.update(ToCancel);
		displayAllOrders();
	}

	public void save(OrderView Toupdate) {

		orderService.update(Toupdate.getOrder());
		OrderView.edit(Toupdate);
		displayAllOrders();

	}

	public void delete(Order r) {
		try {
			errorMsg="";
			orderService.delete(r);
			orders.remove(r);
		} catch (Exception e) {
			errorMsg="cant delete this order it has open items";
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getSNAme() {
		return SNAme;
	}

	public void setSNAme(String sNAme) {
		SNAme = sNAme;
	}

	public Long getSID() {
		return SID;
	}

	public void setSID(Long sID) {
		SID = sID;
	}

	public String getSelectedPlace() {
		return selectedPlace;
	}

	public void setSelectedPlace(String selectedPlace) {
		this.selectedPlace = selectedPlace;
	}

	public long getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(long ownerID) {
		this.ownerID = ownerID;
	}

	public Long getPlaceID() {
		return placeID;
	}

	public void setPlaceID(Long placeID) {
		this.placeID = placeID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderView> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderView> orders) {
		this.orders = orders;
	}

	public String getSUName() {
		return SUName;
	}

	public void setSUName(String sUName) {
		SUName = sUName;
	}

	public String getSPName() {
		return SPName;
	}

	public void setSPName(String sPName) {
		SPName = sPName;
	}

	public String getOpenedEnum() {
		return OrderStatusEnum.OPENED.getCode();
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public Collection <User> getUserList() {
		return userList;
	}

	public List<Place> getPlaceList() {
		return placeList;
	}

	public void setPlaceList(List <Place> placeList) {
		this.placeList = placeList;
	}




}
