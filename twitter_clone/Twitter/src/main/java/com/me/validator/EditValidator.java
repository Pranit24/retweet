package com.me.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.me.dao.UserDao;
import com.me.pojo.User;

public class EditValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "-Please enter a name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "-Please enter a password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "handle", "", "-Please enter a handle");
		User editedUser = (User) target;
		
		if(editedUser.getEmail().equals("")) {
			errors.rejectValue("email","", "-Enter an email address");
		}
		else if(!editedUser.getEmail().matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$")) {
			errors.rejectValue("email", "","-Enter a valid email address!");
		}
		if(editedUser.getPassword().length() < 6) {
			errors.rejectValue("password", "","-Password length must be at least 6");
		}else if(editedUser.getPassword().length() >24) {
			errors.rejectValue("password", "","-Password length must be less than 24");
		}
		if(!editedUser.getHandle().matches("^[a-zA-Z0-9]*$")) {
			errors.rejectValue("handle", "","-Only alphanumerical values for your handle");
		}
		if(errors.hasErrors()) return;
		UserDao userDao = new UserDao();
		if(userDao.alreadyExists("email", editedUser.getEmail()) != null) {
			User user = userDao.getUser(editedUser.getUserId());
			if(!user.getEmail().equals(editedUser.getEmail())) {
				System.out.println(user.getEmail()+":"+editedUser.getEmail());
				errors.rejectValue("email", "", "-Email already in use");
			}
		}
		if(userDao.alreadyExists("handle", editedUser.getHandle()) != null) {
			User user = userDao.getUser(editedUser.getUserId());
			if(!user.getHandle().equals(editedUser.getHandle())) {
				errors.rejectValue("handle", "", "-Handle already in use");
			}
		}
		
	}
}
