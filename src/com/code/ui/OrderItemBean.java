package com.code.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.code.dal.orm.*;
import com.code.services.OrderItemService;
import com.code.services.UserService;
import java.util.Collection;

@SuppressWarnings("serial")
@ManagedBean(name = "OrderItem")
@ViewScoped
public class OrderItemBean implements Serializable {

	private Long orderId = 9L;
	private Order order;
	private Place place;
	private List<OrderItemView> items;
	private OrderItemService orderItemService = null;
	private List<PlacesItem> menu;
	private Collection<User> user;
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

	public void refresh() {
		try {
			orderId = 1L;
			setItems(orderItemService.getOrderListByOrderID(orderId));
			for (OrderItemView ord : items) {
				ord.saveHistory();
			}
			setOrder(orderItemService.getOrderByOrderID(orderId));
			setPlace(orderItemService.getPlaceByPlaceID(order.getPlaceID()));
			setMenu(orderItemService.getMenuListByOrderID(place.getId()));
			UserService userService = UserService.getSingleton();
			user = userService.getAllUsers();

		} catch (Exception ea) {
			return;
		}

	}

	@PostConstruct
	public void init() {

		orderItemService = OrderItemService.getSingleton();
		refresh();

	}

	public String toggleSelection(OrderItemView selectedItem) {
		if (selectedItem == null)
			return null;
		if (selectedItem.getToAdd())
			items.remove(selectedItem);
		else if (selectedItem.getSelected()) {
			selectedItem.setSelected(false);
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

	public String appendOrder() {
		OrderItemView newOrder = new OrderItemView();
		newOrder.setToAdd(true);
		newOrder.setSelected(true);
		items.add(newOrder);
		return null;
	}

	public String delete(OrderItemView ordItem) {
		errorMessage = orderItemService.orderItemDeleteManually(ordItem);
		if (errorMessage == null) {
			items.remove(ordItem);
		}
		return null;
	}

	private String searchUsername(Long userId) {
		if (userId == null)
			return "";
		for (User search : user) {
			if (search.getId().equals(userId)) {
				return search.getUsername();
			}
		}
		return "";
	}

	private String searchItemName(Long itemId) {
		if (itemId == null)
			return "";
		for (PlacesItem search : menu) {
			if (search.getId().equals(itemId)) {
				return search.getName();
			}
		}
		return "";
	}

	public String add(OrderItemView ordItem) {

		if (!checkInput(ordItem)) {
			return null;
		}
		errorMessage = orderItemService.orderItemAddManually(ordItem);
		if (errorMessage == null) {
			ordItem.setSelected(false);
			ordItem.setToAdd(false);
			ordItem.setUsername(searchUsername(ordItem.getUserId()));
			ordItem.setItemName(searchItemName(ordItem.getItemId()));
			ordItem.saveHistory();
		}
		return null;
	}

	public String save(OrderItemView ordItem) {
		ordItem.setOrderId(order.getId());
		if (!checkInput(ordItem)) {
			return null;
		}
		errorMessage = orderItemService.orderItemUpdateManually(ordItem);
		if (errorMessage == null) {
			ordItem.setSelected(false);
			ordItem.setToAdd(false);
			ordItem.setUsername(searchUsername(ordItem.getUserId()));
			ordItem.setItemName(searchItemName(ordItem.getItemId()));
			ordItem.saveHistory();
		}
		return null;
	}

	private Float searchPrice(Long ItemId) {
		if (ItemId == null)
			return 0.0F;
		for (PlacesItem search : menu) {
			if (search.getId().equals(ItemId)) {
				return search.getPrice();
			}
		}
		return 0.0F;
	}

	public boolean checkValuesMatterToPrice(OrderItemView selectedItem) {
		try {
			Long ItemId = Long.parseLong(selectedItem.getItemIdToCheck());
			Integer count = Integer.parseInt(selectedItem.getCountToCheck());
			if (ItemId == null || count == null || count == 0)
				return false;
			else {
				selectedItem.setCount(count);
				selectedItem.setPrice(searchPrice(ItemId));
				return true;
			}
		} catch (Exception ea) {
			return false;
		}
	}

	public boolean checkInput(OrderItemView ordItem) {
		if (!checkValuesMatterToPrice(ordItem)) {
			errorMessage = "Please check on the count to be positive integer and the item selected";
			return false;
		}
		try {
			Long userId = Long.parseLong(ordItem.getUserIdToCheck());
			ordItem.setUserId(userId);
		} catch (Exception ea) {
			errorMessage = "Please select user";
			return false;
		}
		ordItem.setOrderId(order.getId());
		return true;
	}

	public void updatePrice(OrderItemView selectedItem) {
		if (!checkValuesMatterToPrice(selectedItem)) {
			selectedItem.setPrice(0F);
			selectedItem.setCount(0);
		}
	}

	public Collection<User> getUser() {
		return user;
	}

	public void setUser(Collection<User> user) {
		this.user = user;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

}
