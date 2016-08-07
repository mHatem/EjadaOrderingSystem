package com.code.ui.user;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class LoginCheckBean implements Serializable {

	@ManagedProperty(value = "#{login}")
	private Login login;

	public void checkLoggedIn() throws IOException {
		if (!isLoggedIn()) {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
		}
	}

	private boolean isLoggedIn() {
		return login != null && login.getUsername() != null && login.getUsername().length() > 0;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
}
