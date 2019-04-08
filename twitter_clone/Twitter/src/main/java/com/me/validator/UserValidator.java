package com.me.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.me.dao.UserDao;
import com.me.pojo.User;

public class UserValidator implements Validator{

	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	
	@Override
	public void validate(Object target, Errors errors) {
		User logged_user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "-Please enter a password");
		if(logged_user.getEmail().equals("")) {
			errors.rejectValue("email", "", "-Please enter an email address");
		}
		else if(!logged_user.getEmail().matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$")) {
			errors.rejectValue("email", "","-Enter a valid email address!");
		}
		if(errors.hasErrors()) return;
		UserDao userDao = new UserDao();
		User user = userDao.check(logged_user.getEmail(), logged_user.getPassword());
		if(user==null) {
			errors.rejectValue("email","","-The email or password is wrong");
		}
		
	}

}
