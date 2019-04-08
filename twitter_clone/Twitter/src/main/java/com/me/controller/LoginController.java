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
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.UserDao;
import com.me.pojo.Following;
import com.me.pojo.User;
import com.me.validator.UserValidator;



/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/*")
public class LoginController {
	
	@Autowired
	UserDao userDao;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		User user = new User();
		model.addAttribute("login",user);
		return "welcome";
	}
	
//	@RequestMapping(value="/profile.htm", method=RequestMethod.GET)
//	public ModelAndView sign(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		User user = (User) session.getAttribute("user-logged");
//		if(user==null) return new ModelAndView("redirect:/");
//		return new ModelAndView("profile","user",user);
//		
//	}
	
	@RequestMapping(value="/profile/*", method=RequestMethod.POST)
	public String signIn(@ModelAttribute("login") User logged_user, HttpServletRequest request, BindingResult results) {
		UserValidator userValid = new UserValidator();
		
		userValid.validate(logged_user, results);
		
		if(results.hasErrors()) {
			return "welcome";
		}
		User user = userDao.check(logged_user.getEmail(), logged_user.getPassword());
		HttpSession session = request.getSession();
		session.setAttribute("user-logged", user);
		
		return "redirect:/profile/"+user.getHandle();
	}
	
	
	@RequestMapping(value="/profile/*", method=RequestMethod.GET)
	public ModelAndView profile(HttpServletRequest request, Model model) {
		String user_url = request.getRequestURI();
		String[] url = user_url.split("/");
		String handle = url[url.length -1];
		User user = userDao.getUser(handle);
		if(user==null) return new ModelAndView("profile","error",handle);
		
		user.setFollowers(userDao.getNumberOfFollowers(user));
		model.addAttribute("following", new Following());
		return new ModelAndView("profile", "user", user);
	}
	
	@RequestMapping(value="/signout.htm", method=RequestMethod.GET)
	public String signOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	
	
}
