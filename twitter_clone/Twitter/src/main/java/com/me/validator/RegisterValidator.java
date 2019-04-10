package com.me.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.me.dao.UserDao;
import com.me.pojo.User;

public class RegisterValidator  implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "-Please enter a name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "-Please enter a password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "handle", "", "-Please enter a handle");
		User user = (User) target;
		
		if(user.getEmail().equals("")) {
			errors.rejectValue("email","", "-Enter an email address");
		}
		else if(!user.getEmail().matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$")) {
			errors.rejectValue("email", "","-Enter a valid email address!");
		}
		if(user.getPassword().length() < 6) {
			errors.rejectValue("password", "","-Password length must be at least 6");
		}else if(user.getPassword().length() >24) {
			errors.rejectValue("password", "","-Password length must be less than 24");
		}
		if(!user.getHandle().matches("^[a-zA-Z0-9]*$")) {
			errors.rejectValue("handle", "","-Only alphanumerical values for your handle");
		}
		if(errors.hasErrors()) return;
		UserDao userDao = new UserDao();
		if(userDao.alreadyExists("email", user.getEmail()) != null) {
			errors.rejectValue("email", "", "-Email already in use");
		}
		if(userDao.alreadyExists("handle", user.getHandle()) != null) {
			errors.rejectValue("handle", "", "-Handle already in use");
		}
		
	}
}
