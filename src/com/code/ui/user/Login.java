package com.code.ui.user;

import com.code.dal.orm.User;
import com.code.services.UserService;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

@ManagedBean
@ViewScoped
public class Login implements Serializable {
	private String username;
	private String password;

	private String name;
	private String passwordConfirm;
	private String phoneNo;

	private String invalidPasswordMessage;
	private String unmatchedPasswordMessage;
	private String usernameFieldMessage;

	private String loggedUserName;
	private String loggedUsername;

	private boolean loggedIn = false;
	private boolean signingUp = false;
	private boolean editing = false;

	public static final String SESSION_KEY_USER_ID = "user_id";
	public static final String SESSION_KEY_USER_ROLE = "user_role";

	public Login() {
	}

	@PostConstruct
	public void init() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		if (sessionMap.containsKey(SESSION_KEY_USER_ID)) {
			Long userId = (Long) sessionMap.get(SESSION_KEY_USER_ID);
			User user = UserService.getSingleton().getUserById(userId);

			if (user != null)
				updateViewLoginData(user);

		}
	}

	private void updateViewLoginData(User user) {
		loggedIn = true;
		loggedUserName = user.getName();
		loggedUsername = user.getUsername();
	}

	public String loginAction() {
		clearAllFieldsErrorMessages();

		if (password == null || password.length() == 0)
			return null;

		loggedIn = false;

		User user = UserService.getSingleton().authenticateUser(username, password);

		if (user != null) {
			updateViewLoginData(user);

			String userRole = UserRole.NORMAL; //TODO get role from database

			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put(SESSION_KEY_USER_ID, user.getId());
			sessionMap.put(SESSION_KEY_USER_ROLE, userRole);
		} else {
			invalidPasswordMessage = "Wrong password!";
		}

		password = null;
		return null;
	}

	public String signupButton() {
		signingUp = true;
		password = null;
		return null;
	}

	public String loginButton() {
		signingUp = false;
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

		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Long userId = (Long) sessionMap.get(SESSION_KEY_USER_ID);

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

	public String signout() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.remove(SESSION_KEY_USER_ID);

		loggedIn = false;
		username = null;
		return null;
	}

	public String editButton() {
		editing = true;

		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		if (sessionMap.containsKey(SESSION_KEY_USER_ID)) {
			Long userId = (Long) sessionMap.get(SESSION_KEY_USER_ID);

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

	public static class UserRole {
		public static final String NORMAL = "normal";
		public static final String ADMIN = "admin";
		public static final String ADMIN_PLACES = "admin_places";
		public static final String ADMIN_ITEMS = "admin_items";
	}

	private void clearAllFieldsErrorMessages() {
		usernameFieldMessage = null;
		invalidPasswordMessage = null;
		unmatchedPasswordMessage = null;
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
}
