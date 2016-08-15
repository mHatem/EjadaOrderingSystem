package com.code.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.code.OrderStatusEnum;
import com.code.dal.orm.*;
import com.code.services.OrderItemService;
import com.code.services.UserService;
import com.code.ui.user.Login;

@SuppressWarnings("serial")
@ManagedBean(name = "OrderItem")
@ViewScoped
public class OrderItemBean implements Serializable {

	private Long orderId;
	private Order order;
	private Place place;
	private List<OrderItemView> items;
	private OrderItemService orderItemService = null;
	private List<PlacesItem> menu;
	private ArrayList<User> user;
	private String errorMessage;
	private Boolean isAdmin = false;
	private User loggedUser;
	private Long userId;
	private String searchParameterUsername;
	private String searchParameterItemName;
	private Boolean isOpened = false;

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

	public String resetData() {
		searchParameterItemName = null;
		searchParameterUsername = null;
		filterData();
		return null;
	}

	public String filterData() {
		if (searchParameterItemName != null)
			searchParameterItemName = searchParameterItemName.trim()
					.toLowerCase();
		if (searchParameterItemName != null)
			searchParameterUsername = searchParameterUsername.trim()
					.toLowerCase();
		items = orderItemService.getOrderedItemsFiltered(orderId,
				searchParameterItemName, searchParameterUsername);
		return null;
	}

	public void refresh() {
		try {

			String orderIdString = ((HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest())
					.getParameter("ORDERID");
			orderId = Long.parseLong(orderIdString);
            if(orderId == null || orderId == -1)
            {
            	Logout();
            }
			setItems(orderItemService.getOrderListByOrderID(orderId));
			for (OrderItemView ord : items) {
				ord.saveHistory();
			}
			setOrder(orderItemService.getOrderByOrderID(orderId));
			if (order.getStatus().trim().toLowerCase()
					.equals(OrderStatusEnum.OPENED.getCode()))
				isOpened = true;
			else
				isOpened = false;
			setPlace(orderItemService.getPlaceByPlaceID(order.getPlaceID()));
			setMenu(orderItemService.getMenuListByOrderID(place.getId()));
			UserService userService = UserService.getSingleton();

			Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			String userRole = (String) sessionMap
					.get(Login.SESSION_KEY_USER_ROLE);
			userId = (Long) sessionMap
					.get(Login.SESSION_KEY_USER_ID);
			if(userId == null || userRole == null)
            {
            	Logout();
            }
			if (userRole.equals(UserRole.ADMIN))
				isAdmin = true;
			else
				isAdmin = false;
			//userId = Long.parseLong(userIdTransefer);

			if (isAdmin) {
				user = new ArrayList<User>(userService.getAllUsers());
			}
			loggedUser = userService.getUserById(userId);

		} catch (Exception ea) {
			Logout();
			return;
		}

	}

	@PostConstruct
	public void init() {

		orderItemService = OrderItemService.getSingleton();
		refresh();

	}
	
	public void Logout()
	{
		try{
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			sessionMap.put(Login.SESSION_KEY_USER_ROLE, null);
			sessionMap.put(Login.SESSION_KEY_USER_ID, null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			}
			catch(Exception ca){return;}
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
		newOrder.setUserId(userId);
		newOrder.setUserIdToCheck(userId.toString());
		newOrder.setUsername(loggedUser.getUsername());
		if (menu.size() > 0 && menu.get(0).getId() != null) {
			newOrder.setItemId(menu.get(0).getId());
			newOrder.setItemIdToCheck(menu.get(0).getId().toString());
			newOrder.setPrice(menu.get(0).getPrice());
		}

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
			return null;
		for (User search : user) {
			if (search.getId().equals(userId)) {
				return search.getUsername();
			}
		}
		return null;
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
			if (isAdmin)
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
			if (isAdmin)
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
				selectedItem.setItemId(ItemId);
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

	public ArrayList<User> getUser() {
		return user;
	}

	public void setUser(ArrayList<User> user) {
		this.user = user;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getSearchParameterUsername() {
		return searchParameterUsername;
	}

	public void setSearchParameterUsername(String searchParameterUserId) {
		this.searchParameterUsername = searchParameterUserId;
	}

	public String getSearchParameterItemName() {
		return searchParameterItemName;
	}

	public void setSearchParameterItemName(String searchParameterItemId) {
		this.searchParameterItemName = searchParameterItemId;
	}

	public Boolean getIsOpened() {
		return isOpened;
	}

	public void setIsOpened(Boolean isOpened) {
		this.isOpened = isOpened;
	}

}
