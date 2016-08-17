package com.code.ui.user;

import com.code.dal.orm.*;
import com.code.services.*;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class Login implements Serializable {
	public static final String SESSION_KEY_USER_ID = "user_id";
	public static final String SESSION_KEY_USER_ROLE = "user_role";

	private String username;
	private String password;
	private String name;
	private String passwordConfirm;
	private String phoneNo;

	private String invalidPasswordMessage;
	private String invalidPhoneNoMessage;
	private String unmatchedPasswordMessage;
	private String usernameFieldMessage;

	private User loggedUser;

	private boolean signingUp = false;
	private boolean editing = false;

	private String alertMessage = null;

	private List<Place> places;
	private List<PlacesItem> placeItems;
	private Long selectedPlaceId;
	private Long selectedPlaceItemId;

	private Collection<OrderView> orders;
	private Collection<OrderItemView> orderItemViews;

	public Login() {
	}

	@PostConstruct
	public void init() {
		Long userId = getUserIdFromSessionMap();
		if (userId != null) {
			User user = UserService.getSingleton().getUserById(userId);
			updateLoggedUser(user);

			updateListsAndTables();
		}
	}

	public String loginAction() {
		clearAllFieldsErrorMessages();

		if (password == null || password.length() == 0) {
			updateLoggedUser(null);
			return null;
		}

		User user = UserService.getSingleton().authenticateUser(username, password);
		updateLoggedUser(user);

		if (user != null) {
			updateListsAndTables();
		} else {
			invalidPasswordMessage = "Wrong password!";
		}

		password = null;
		return null;
	}

	public String signupAction() {
		clearAllFieldsErrorMessages();

		if (!isValidPhoneNumber(phoneNo)) {
			invalidPhoneNoMessage = "Invalid phone number.";
			return null;
		}

		if (!password.equals(passwordConfirm))
			return null;

		User user = new User();
		user.setUsername(username.toLowerCase());
		user.setPassword(password);
		user.setName(name);
		user.setPhoneNo(phoneNo);

		try {
			UserService.getSingleton().saveUser(user);
		} catch (ConstraintViolationException e) {
			if (e.getConstraintName().equals(UserService.UNIQUE_USERNAME_CONSTRAINT_NAME)) {
				usernameFieldMessage = "Username already taken";
			} else {
				e.printStackTrace();
			}
			return null;
		} catch (HibernateException e) {
			e.printStackTrace();
		}

		signingUp = false;
		return null;
	}

	public String signoutAction() {
		updateLoggedUser(null);

		resetAll();
		// TODO invalidate session
		return null;
	}

	public String updateAction() {
		clearAllFieldsErrorMessages();

		if ((password == null || password.length() == 0) &&
			(passwordConfirm == null || passwordConfirm.length() == 0)) {
			// No password change
			password = null;
		} else if (password != null && !password.equals(passwordConfirm)) {
			unmatchedPasswordMessage = "Passwords do not match";
			return null;
		} else if (!isValidPhoneNumber(phoneNo)) {
			invalidPhoneNoMessage = "Invalid phone number.";
			return null;
		}

		Long userId = getUserIdFromSessionMap();

		try {
			User user = UserService.getSingleton().getUserById(userId);
			user.setUsername(username.toLowerCase());
			if (password != null)
				user.setPassword(password);
			user.setName(name);
			user.setPhoneNo(phoneNo);

			UserService.getSingleton().updateUser(user);

			updateLoggedUser(user);
		} catch (ConstraintViolationException e) {
			if (e.getConstraintName().equals(UserService.UNIQUE_USERNAME_CONSTRAINT_NAME)) {
				usernameFieldMessage = "Username already taken";
			} else {
				e.printStackTrace();
			}
			return null;
		} catch (HibernateException e) {
			e.printStackTrace();
		}

		editing = false;
		return null;
	}

	private boolean isValidPhoneNumber(String phoneNo) {
		for (int i = 0; i < phoneNo.length(); i++) {
			char c = phoneNo.charAt(i);
			if (!(c == ' ' || c == '-' || (c >= '0' && c <= '9')))
				return false;
		}
		return true;
	}

	public void filterAction() {
		updateListsAndTables();
		updateTables();
	}

	public String resetFilterAction() {
		resetFilters();
		return null;
	}

	private void resetFilters() {
		selectedPlaceId = null;
		selectedPlaceItemId = null;
	}

	public String loginButton() {
		signingUp = false;

		resetDataFields();
		clearAllFieldsErrorMessages();
		return null;
	}

	public String signupButton() {
		signingUp = true;

		resetDataFields();
		clearAllFieldsErrorMessages();
		return null;
	}

	public String editButton() {
		editing = true;

		Long userId = getUserIdFromSessionMap();
		if (userId != null) {
			User user = UserService.getSingleton().getUserById(userId);
			if (user != null) {
				username = user.getUsername();
				password = user.getPassword();
				passwordConfirm = password;
				name = user.getName();
				phoneNo = user.getPhoneNo();

				UserService.getSingleton().updateUser(user);
			}
		}

		return null;
	}

	public String editCancelButton() {
		editing = false;

		resetDataFields();
		clearAllFieldsErrorMessages();
		return null;
	}

	private void clearAllFieldsErrorMessages() {
		usernameFieldMessage = null;
		invalidPasswordMessage = null;
		invalidPhoneNoMessage = null;
		unmatchedPasswordMessage = null;
		alertMessage = null;
	}

	private void updateListsAndTables() {
		places = PlaceService.retrievePlaces();
		Collections.sort(places, new Comparator<Place>() {
			@Override
			public int compare(Place o1, Place o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		updatePlaceItemsMenu();

		updateTables();
	}

	public void updatePlaceItemsMenu() {
		if (selectedPlaceId != null) {
			placeItems = Service.getItemsList(selectedPlaceId);
		} else {
			placeItems = Service.getAllPlaceItems();
		}
		Collections.sort(placeItems, new Comparator<PlacesItem>() {
			@Override
			public int compare(PlacesItem o1, PlacesItem o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
	}

	private void updateTables() {
		Long userId = getUserIdFromSessionMap();
		String userRole = getUserRoleFromSessionMap();

		// Don't filter by user id if admin is logged in
		if (userRole.equals(UserRole.ADMIN))
			userId = null;

		orders = orderService.find(null, null, null, null, selectedPlaceId, userId);
		orderItemViews = OrderItemService.getSingleton().getOrderItemByUserIdOrPlaceIdOrPlaceItemId(
			userId,
			selectedPlaceId,
			selectedPlaceItemId
		);
	}

	private void updateLoggedUser(User user) {
		loggedUser = user;

		Long userId = loggedUser != null ? loggedUser.getId() : null;
		String userRole = userId != null ? UserService.getSingleton().getUserRole(userId) : null;

		putUserIdIntoSessionMap(userId);
		putUserRoleIntoSessionMap(userRole);
	}

	public String getPageTitle() {
		String titleExpression;
		if (isLoggedIn())
			titleExpression = "#{msgs.welcomeHeading}";
		else {
			if (signingUp)
				titleExpression = "#{msgs.signup}";
			else
				titleExpression = "#{msgs.login}";
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		String title = (String) facesContext.getApplication().evaluateExpressionGet(
			facesContext,
			titleExpression,
			String.class
		);
		return title;
	}

	private Map<String, Object> getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}

	private void putUserIdIntoSessionMap(Long userId) {
		getSessionMap().put(SESSION_KEY_USER_ID, userId);
	}

	private Long getUserIdFromSessionMap() {
		Map<String, Object> sessionMap = getSessionMap();

		if (sessionMap.containsKey(SESSION_KEY_USER_ID))
			return (Long) sessionMap.get(SESSION_KEY_USER_ID);

		return null;
	}

	private void putUserRoleIntoSessionMap(String userRole) {
		getSessionMap().put(SESSION_KEY_USER_ROLE, userRole);
	}

	private String getUserRoleFromSessionMap() {
		Map<String, Object> sessionMap = getSessionMap();

		if (sessionMap.containsKey(SESSION_KEY_USER_ROLE))
			return (String) sessionMap.get(SESSION_KEY_USER_ROLE);

		return null;
	}

	public boolean isLoggedIn() {
		return loggedUser != null && getUserIdFromSessionMap() != null;
	}

	private void resetAll() {
		editing = false;
		signingUp = false;
		clearAllFieldsErrorMessages();
		resetFilters();
		resetDataFields();
	}

	private void resetDataFields() {
		username = null;
		password = null;
		name = null;
		passwordConfirm = null;
		phoneNo = null;
	}

	public String deleteOrder(OrderView orderView) {
		alertMessage = null;

		if (!isAdmin()) {
			alertMessage = "Action not allowed";
			System.err.println(alertMessage);
			return null;
		}

		Long orderId = orderView.getId();
		List list = orderService.find(null, null, null, orderId, null, null);
		if (list.size() == 1) {
			Order order = ((OrderView) list.get(0)).getOrder();
			try {
				orderService.delete(order);
				orders.remove(orderView);
			} catch (ConstraintViolationException e) {
				// TODO correct constraint name
				if (true || e.getConstraintName().equals(UserService.SCHEMA_NAME + "." + "SYS_C008103")) {
					// TODO warning message
					alertMessage = "Cannot delete this order. One or more items are in this order.";
					System.err.println(alertMessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String deleteOrderItemView(OrderItemView orderItemView) {
		alertMessage = null;

		if (!isAdmin()) {
			alertMessage = "Action not allowed";
			return null;
		}

		Long orderItemId = orderItemView.getId();
		String result = OrderItemService.getSingleton().orderItemDeleteManually(orderItemView);
		if (result != null) {
			alertMessage = "Cant delete this order item: " + result;
			System.err.println(alertMessage);
		} else {
			orderItemViews.remove(orderItemView);
		}

		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getInvalidPasswordMessage() {
		return invalidPasswordMessage;
	}

	public void setInvalidPasswordMessage(String invalidPasswordMessage) {
		this.invalidPasswordMessage = invalidPasswordMessage;
	}

	public String getUnmatchedPasswordMessage() {
		return unmatchedPasswordMessage;
	}

	public void setUnmatchedPasswordMessage(String unmatchedPasswordMessage) {
		this.unmatchedPasswordMessage = unmatchedPasswordMessage;
	}

	public String getUsernameFieldMessage() {
		return usernameFieldMessage;
	}

	public void setUsernameFieldMessage(String usernameFieldMessage) {
		this.usernameFieldMessage = usernameFieldMessage;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public boolean isSigningUp() {
		return signingUp;
	}

	public void setSigningUp(boolean signingUp) {
		this.signingUp = signingUp;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	public Long getSelectedPlaceId() {
		return selectedPlaceId;
	}

	public void setSelectedPlaceId(Long selectedPlaceId) {
		this.selectedPlaceId = selectedPlaceId;
	}

	public List<PlacesItem> getPlaceItems() {
		return placeItems;
	}

	public void setPlaceItems(List<PlacesItem> placeItems) {
		this.placeItems = placeItems;
	}

	public Long getSelectedPlaceItemId() {
		return selectedPlaceItemId;
	}

	public void setSelectedPlaceItemId(Long selectedPlaceItemId) {
		this.selectedPlaceItemId = selectedPlaceItemId;
	}

	public Collection<OrderView> getOrders() {
		return orders;
	}

	public void setOrders(Collection<OrderView> orders) {
		this.orders = orders;
	}

	public boolean isAdmin() {
		String userRole = getUserRoleFromSessionMap();
		return userRole != null && userRole.equals(UserRole.ADMIN);
	}

	public Collection<OrderItemView> getOrderItemViews() {
		return orderItemViews;
	}

	public void setOrderItemViews(Collection<OrderItemView> orderItemViews) {
		this.orderItemViews = orderItemViews;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public String getInvalidPhoneNoMessage() {
		return invalidPhoneNoMessage;
	}

	public void setInvalidPhoneNoMessage(String invalidPhoneNoMessage) {
		this.invalidPhoneNoMessage = invalidPhoneNoMessage;
	}
}
