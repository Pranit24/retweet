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
	
	@RequestMapping(value="/follow.htm", method=RequestMethod.POST)
	public String follow(HttpServletRequest request, @ModelAttribute("following") Following follow) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged==null) return "redirect:/";
		follow.setFollowing_user(user_logged);
		userDao.follow(follow);
		
		return "redirect:/profile/"+request.getParameter("profile");
	}
	
	@RequestMapping(value="/unfollow.htm", method=RequestMethod.POST)
	public String unfollow(HttpServletRequest request, @ModelAttribute("following") Following follow) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged==null) return "redirect:/";
		follow.setFollowing_user(user_logged);
		System.out.println(follow.getfId()+":"+follow.getFollowingId());
		userDao.unfollow(follow, user_logged);
		
		return "redirect:/profile/"+request.getParameter("profile");
	}
}
