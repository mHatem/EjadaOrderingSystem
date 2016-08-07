package com.code.ui.user;

import com.code.services.SessionFactoryBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

	private UIComponent usernameField;
	private UIComponent passwordField;

	private boolean loggedIn = false;

	public SessionFactoryBean getSessionFactoryBean() {
		return sessionFactoryBean;
	}

	public void setSessionFactoryBean(SessionFactoryBean sessionFactoryBean) {
		this.sessionFactoryBean = sessionFactoryBean;
	}

	public Login() {
	}

	public String getUsername() {
		return username;
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

	public String submitAction() {
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

	public String signout() {
		loggedIn = false;
		username = null;
		return "login?faces-redirect=true";
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
