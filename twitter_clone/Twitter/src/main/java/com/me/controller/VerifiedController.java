package com.me.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.me.dao.UserDao;
import com.me.pojo.User;

@Controller
@RequestMapping("/admin")
public class VerifiedController {
	
	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/verify", method=RequestMethod.GET)
	public String verify(HttpServletRequest request, HttpSession session) {
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged == null) return "redirect:/";
		String handle = request.getParameter("handle");
		User user = userDao.getUser(handle);
		if(user == null) return "redirect:/";
		user.setVerified(true);
		userDao.editUser(user);
		return "redirect:/profile/"+handle;
	}
	
	@RequestMapping(value = "/unverify", method=RequestMethod.GET)
	public String unverify(HttpServletRequest request, HttpSession session) {
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged == null) return "redirect:/";
		String handle = request.getParameter("handle");
		User user = userDao.getUser(handle);
		if(user == null) return "redirect:/";
		user.setVerified(false);
		userDao.editUser(user);
		return "redirect:/profile/"+handle;
	}
}
