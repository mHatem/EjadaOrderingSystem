package com.code.ui;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;






import com.code.dal.orm.*;
import com.code.services.OrderItemService;
@SuppressWarnings("serial")
@ManagedBean(name = "OrderItem")
@ViewScoped
public class OrderItemBean implements Serializable{

	private Long orderId = 9L;
	private Order order ; 
	private Place place;
	private List<OrderItemView> items;

	private List<PlacesItem> menu;
	private List<User> user ;
    private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<OrderItemView> getItems() {
		return items;
	}

	public void setItems(List<OrderItemView> items) {
		this.items = items;
	}
	public void refresh()
	{
		try{
			orderId = 1L;
			setItems(OrderItemService.getOrderListByOrderID(orderId));
			for(OrderItemView ord : items )
			{
				ord.saveHistory();
			}
			setOrder(OrderItemService.getOrderByOrderID(orderId));
			setPlace(OrderItemService.getPlaceByPlaceID(order.getPlaceID()));
			setMenu(OrderItemService.getMenuListByOrderID(place.getId()));
			setUser(OrderItemService.getAllUsers());
		}
		catch(Exception ea){}
		
	}
	@PostConstruct
	public void init() {
		
			refresh();
		
	}
	
	public String toggleSelection(OrderItemView selectedItem) {
        if(selectedItem == null)
        	return null;
        if(selectedItem.getToAdd())
        	items.remove(selectedItem);
        else if(selectedItem.getSelected())
			{selectedItem.setSelected(false);
			selectedItem.RetriveHistory();   			
			}
			
		else {
			selectedItem.setSelected(true); 
			selectedItem.saveHistory();
		}
		return null;
	}
	

	public List<PlacesItem> getMenu() {
		return menu;
	}

	public void setMenu(List<PlacesItem> menu) {
		this.menu = menu;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public String appendOrder()
	{
	   OrderItemView newOrder = new OrderItemView();  
		newOrder.setToAdd(true);
		newOrder.setSelected(true);
		items.add(newOrder);
		return null;
	}
	
	
	public String delete(OrderItemView ordItem)
	{
		errorMessage = OrderItemService.orderItemDeleteManually(ordItem); 
		if(errorMessage == null)
		{
			items.remove(ordItem);
		}
		return null;
	}
	
	private String searchUsername(Long userId)
	{
	   if(userId == null) return "";	
		for(User search  :user)
		{
			if(search.getId().equals(userId))
			{
				return search.getUsername();
			}
		}
		return "";
	}
	
	private String searchItemName(Long itemId)
	{
	   if(itemId == null) return "";	
		for(PlacesItem search  :menu)
		{
			if(search.getId().equals(itemId))
			{
				return search.getName();
			}
		}
		return "";
	}
	
	public String add(OrderItemView ordItem)
	{
		ordItem.setOrderId(order.getId());
	
		/*ordItem.getItemId();
		ordItem.getUserId();
		ordItem.getCount();*/
	    errorMessage = OrderItemService.orderItemAddManually(ordItem); 
		if(errorMessage == null)
		{
			ordItem.setSelected(false);
			ordItem.setToAdd(false);
			ordItem.setUsername(searchUsername(ordItem.getUserId()));
			ordItem.setItemName(searchItemName(ordItem.getItemId()));
			ordItem.saveHistory();
		}
		return null;
	}

	public String save(OrderItemView ordItem)
	{
		ordItem.setOrderId(order.getId());
	
		/*ordItem.getItemId();
		ordItem.getUserId();
		ordItem.getCount();*/
	    errorMessage = OrderItemService.orderItemUpdateManually(ordItem); 
		if(errorMessage == null)
		{
			ordItem.setSelected(false);
			ordItem.setToAdd(false);
			ordItem.setUsername(searchUsername(ordItem.getUserId()));
			ordItem.setItemName(searchItemName(ordItem.getItemId()));
			ordItem.saveHistory();
		}
		return null;
	}
	
	
	private Float searchPrice(Long ItemId)
	{
	   if(ItemId == null) return 0.0F;	
		for(PlacesItem search  :menu)
		{
			if(search.getId().equals(ItemId))
			{
				return search.getPrice();
			}
		}
		return 0.0F;
	}
	
	public void updatePrice(OrderItemView selectedItem)
	{
		Long ItemId  = selectedItem.getItemId();
		selectedItem.setPrice(searchPrice(ItemId));
	}


	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

}
