package com.me.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.me.dao.UserDao;
import com.me.pojo.User;
import com.me.validator.RegisterValidator;

/**
 * @author Pranit
 *
 */
@Controller
@RequestMapping("/register/*")
public class RegisterController {

	@Autowired
	UserDao userDao;
	
	@RequestMapping(value="/create.htm", method=RequestMethod.GET)
	public String register(Model model) {
		User user = new User();
		model.addAttribute("register",user);
		return "register";
	}
	
	@RequestMapping(value="/create.htm", method=RequestMethod.POST)
	public String register(Model model, HttpServletRequest request,@ModelAttribute("register") User user, BindingResult results) {
		//TODO Validate inputs and register user
		RegisterValidator regValid = new RegisterValidator();
		regValid.validate(user, results);
		if(results.hasErrors()) {
			return "register";
		}
		userDao.register(user);
		HttpSession session = request.getSession();
		session.setAttribute("user-logged", user);
		
		return "redirect:/";
		
	}
	
	@RequestMapping(value="/edit.htm", method=RequestMethod.GET)
	public String edit(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user-logged");
		if(user_logged==null) return "redirect:/";
		System.out.println(user_logged.getUserId());
		model.addAttribute("update", user_logged);
		return "editProfile";
	}
	
	@RequestMapping(value="/edit.htm", method=RequestMethod.POST)
	public String edit(HttpServletRequest request, @ModelAttribute("update") User updated_user) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user-logged");
		if(user_logged==null) return "redirect:/";
		updateUser(user_logged, updated_user);
		userDao.editUser(user_logged);
		return "redirect:/profile/"+user_logged.getHandle();
	}
	
	
	/**
	 * @param user_logged - Currect user logged to the site
	 * @param updated_user - User's updated profile
	 */
	public void updateUser(User user_logged, User updated_user) {
		user_logged.setEmail(updated_user.getEmail());
		user_logged.setPassword(updated_user.getPassword());
		user_logged.setName(updated_user.getName());
		user_logged.setHandle(updated_user.getHandle());
		user_logged.setDescription(updated_user.getDescription());
	}
	
}
