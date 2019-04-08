package com.me.controller;



import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.TweetDao;
import com.me.pojo.Tweet;
import com.me.pojo.User;

@Controller
@RequestMapping("/tweet/*")
public class TweetController {

	@Autowired
	TweetDao msgDao;
	
	
	@RequestMapping(value="/tweet.htm", method=RequestMethod.GET)
	public ModelAndView tweet(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user-logged");
		if(user_logged==null) return new ModelAndView("redirect:/");
		Tweet message = new Tweet();
		model.addAttribute("message", message);
		return new ModelAndView("message");
	}
	
	@RequestMapping(value="/tweet.htm", method=RequestMethod.POST)
	public String tweet(HttpServletRequest request,@ModelAttribute("message") Tweet tweet) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user-logged");
		if(user == null) return "redirect:/";
		tweet.setTimestamp(new Timestamp(System.currentTimeMillis()));
		msgDao.addTweet(user, tweet);
		return "redirect:/profile/"+user.getHandle();
	}
}
