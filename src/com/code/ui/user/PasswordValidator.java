package com.code.ui.user;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "passwordValidator")
public class PasswordValidator implements Validator {
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		String passwordFieldId;
//		passwordFieldId = (String) facesContext.getViewRoot().getAttributes().get("passwordFieldId");
//		passwordFieldId = uiComponent.getParent().getParent().getParent().getClientId(facesContext) + ":signup_password";
		passwordFieldId = (String) uiComponent.getAttributes().get("passwordFieldId");

		UIInput passwordInput = (UIInput) facesContext.getViewRoot().findComponent(passwordFieldId);

		if (passwordInput == null)
			throw new IllegalArgumentException(String.format("Unable to find component with id %s", passwordFieldId));

		String password = (String) passwordInput.getValue();
		String passwordConfirm = (String) value;
		if (password != null && password.length() != 0 && !password.equals(passwordConfirm))
			throw new ValidatorException(new FacesMessage("Passwords do not match!"));
	}
}
