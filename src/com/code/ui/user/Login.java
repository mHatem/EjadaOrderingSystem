package com.code.ui.user;

import com.code.dal.orm.User;
import com.code.services.SessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class Login implements Serializable {
	@ManagedProperty(value = "#{sessionFactoryBean}")
	private SessionFactoryBean sessionFactoryBean;

	private String username;
	private String password;

	private String name;
	private String passwordConfirm;
	private String phoneNo;


	private UIComponent usernameField;
	private UIComponent passwordField;

	private boolean loggedIn = false;
	private boolean signingUp = false;

	public SessionFactoryBean getSessionFactoryBean() {
		return sessionFactoryBean;
	}

	public void setSessionFactoryBean(SessionFactoryBean sessionFactoryBean) {
		this.sessionFactoryBean = sessionFactoryBean;
	}

	public Login() {
	}


	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UIComponent getUsernameField() {
		return usernameField;
	}

	public void setUsernameField(UIComponent usernameField) {
		this.usernameField = usernameField;
	}

	public UIComponent getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(UIComponent passwordField) {
		this.passwordField = passwordField;
	}

	public String loginAction() {
		if (password == null || password.length() == 0)
			return null;

		boolean authenticated = false;

		SessionFactory sessionFactory = sessionFactoryBean.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("User.byUsernameAndPassword");
		query.setString("username", username);
		query.setString("password", password);

		authenticated = (query.list().size() == 1);

		session.getTransaction().commit();
		session.close();

		FacesContext context = FacesContext.getCurrentInstance();
		if (!authenticated)
			context.addMessage(passwordField.getClientId(context), new FacesMessage("Wrong password!"));

//		return (authenticated ? "index?faces-redirect=true" : null);
		if (authenticated) {
			loggedIn = true;
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

		SessionFactory sessionFactory = sessionFactoryBean.getSessionFactory();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			session.close();
		} catch (ConstraintViolationException e) {
			if (session != null)
				session.close();
			return null;
		} catch (HibernateException e) {
			e.printStackTrace();
		}

		signingUp = false;
		return null;
	}


	public String signout() {
		loggedIn = false;
		username = null;
		return "index?faces-redirect=true";
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
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

	public boolean isSigningUp() {
		return signingUp;
	}

	public void setSigningUp(boolean signingUp) {
		this.signingUp = signingUp;
	}

	public String getUsername() {
		return username;
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
}
