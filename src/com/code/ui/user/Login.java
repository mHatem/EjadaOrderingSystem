package com.code.ui.user;

import com.code.dal.orm.*;
import com.code.services.PlaceService;
import com.code.services.UserService;
import com.code.services.orderService;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
	private String unmatchedPasswordMessage;
	private String usernameFieldMessage;

	private Long loggedUserId;
	private String loggedUserName;
	private String loggedUsername;

	private boolean loggedIn = false;
	private boolean signingUp = false;
	private boolean editing = false;

	private Collection<Place> places;
	private Collection<PlacesItem> placeItems;
	private Long selectedPlaceId;
	private Long selectedPlaceItemId;

	private Collection<OrderView> orders;

	private boolean admin = false;

	public Login() {
	}

	@PostConstruct
	public void init() {
		Long userId = getUserIdFromSessionMap();

		if (userId != null) {
			User user = UserService.getSingleton().getUserById(userId);

			if (user != null) {
				updateViewLoginData(user);
				checkAdmin();
			}

			updateListsAndTables();
		}
	}

	public String loginAction() {
		clearAllFieldsErrorMessages();

		if (password == null || password.length() == 0)
			return null;

		loggedIn = false;

		User user = UserService.getSingleton().authenticateUser(username, password);

		if (user != null) {
			updateViewLoginData(user);

			Long userId = user.getId();
			String userRole = UserService.getSingleton().getUserRole(userId);

			putUserIdIntoSessionMap(userId);
			putUserRoleIntoSessionMap(userRole);

			checkAdmin();
			updateListsAndTables();
		} else {
			invalidPasswordMessage = "Wrong password!";
		}

		password = null;
		return null;
	}

	public String signupAction() {
		if (!password.equals(passwordConfirm))
			return null;

		User user = new User();
		user.setUsername(username);
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
		putUserIdIntoSessionMap(null);
		putUserRoleIntoSessionMap(null);

		clearAllFieldsErrorMessages();
		resetFilters();

		loggedIn = false;
		username = null;
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
		}

		Long userId = getUserIdFromSessionMap();

		try {
			User user = UserService.getSingleton().getUserById(userId);
			user.setUsername(username);
			if (password != null)
				user.setPassword(password);
			user.setName(name);
			user.setPhoneNo(phoneNo);

			UserService.getSingleton().updateUser(user);

			updateViewLoginData(user);
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

	public void filterAction() {
		orders = updateOrders();
	}

	public String resetFilterAction() {
		resetFilters();
		return null;
	}

	private void resetFilters() {
		selectedPlaceId = null;
		selectedPlaceItemId = null;
	}

	private void checkAdmin() {
		String userRole = getUserRoleFromSessionMap();
		if (userRole != null && userRole.equals(UserRole.ADMIN)) {
			admin = true;
		} else {
			admin = false;
		}
	}


	public String loginButton() {
		signingUp = false;
		password = null;
		return null;
	}

	public String signupButton() {
		signingUp = true;
		password = null;
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
		return null;
	}

	private void clearAllFieldsErrorMessages() {
		usernameFieldMessage = null;
		invalidPasswordMessage = null;
		unmatchedPasswordMessage = null;
	}

	private void updateListsAndTables() {
		places = PlaceService.retrievePlaces();
		placeItems = new ArrayList<PlacesItem>(); //TODO

		orders = updateOrders();
	}

	private void updateViewLoginData(User user) {
		loggedIn = true;
		loggedUserId = user.getId();
		loggedUserName = user.getName();
		loggedUsername = user.getUsername();
	}

	public String getPageTitle() {
		String titleExpression;
		if (loggedIn)
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

	public Collection<OrderItem> getOrderItems() {
		return new ArrayList<OrderItem>(); //TODO
	}

	private Collection<OrderView> updateOrders() {
		Collection<OrderView> orderViews;
		Long userId = getUserIdFromSessionMap();
		String userRole = getUserRoleFromSessionMap();
		// Don't filter by user id if admin is logged in
		if (userRole.equals(UserRole.ADMIN))
			userId = null;

		orderViews = orderService.find(null, null, null, null, selectedPlaceId, userId);
		return orderViews;
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

	public Long getLoggedUserId() {
		return loggedUserId;
	}

	public void setLoggedUserId(Long loggedUserId) {
		this.loggedUserId = loggedUserId;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	public String getLoggedUsername() {
		return loggedUsername;
	}

	public void setLoggedUsername(String loggedUsername) {
		this.loggedUsername = loggedUsername;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
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

	public Collection<Place> getPlaces() {
		return places;
	}

	public void setPlaces(Collection<Place> places) {
		this.places = places;
	}

	public Long getSelectedPlaceId() {
		return selectedPlaceId;
	}

	public void setSelectedPlaceId(Long selectedPlaceId) {
		this.selectedPlaceId = selectedPlaceId;
	}

	public Collection<PlacesItem> getPlaceItems() {
		return placeItems;
	}

	public void setPlaceItems(Collection<PlacesItem> placeItems) {
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
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
