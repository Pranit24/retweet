package com.me.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.me.dao.UserDao;
import com.me.pojo.Following;
import com.me.pojo.User;

@Controller
@RequestMapping("/profile/follow/*")
public class FollowController {

	@Autowired
	UserDao userDao;
	
	@RequestMapping(value="/*", method=RequestMethod.POST)
	public String follow(HttpServletRequest request, @ModelAttribute("following") Following follow) {
		System.out.println(follow.getfId());
		String url = "redirect:/profile/"+request.getParameter("profile");
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user-logged");
		if(user_logged==null) return url;
		userDao.follow(user_logged, follow);
		
		
		return url;
	}
}
