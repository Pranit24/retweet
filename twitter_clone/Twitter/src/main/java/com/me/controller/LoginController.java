package com.me.controller;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

import com.me.dao.TweetDao;
import com.me.dao.UserDao;
import com.me.pojo.Following;
import com.me.pojo.Tweet;
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
	
	@Autowired
	TweetDao msgDao;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user-logged");
		if(user_logged==null) {
			User user = new User();
			model.addAttribute("login",user);
			return "welcome";
		}
		List<Long> followingList = userDao.getFollowing(user_logged);
		List<Tweet> tweets = msgDao.getFollowingTweet(followingList);
		Collections.sort(tweets, new Comparator<Tweet>() {
			  public int compare(Tweet o1, Tweet o2) {
			      return o2.getTimestamp().compareTo(o1.getTimestamp());
			  }
			});
		model.addAttribute("followingTweets",tweets);
		return "home";
	}
	
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
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user-logged");
		boolean alreadyFollowing = userDao.checkIfFollowing(user_logged, user);
		System.out.println(alreadyFollowing);
		user.setFollowers(userDao.getNumberOfFollowers(user));
		model.addAttribute("following", new Following());
		model.addAttribute("alreadyFollowing",alreadyFollowing);
		return new ModelAndView("profile", "user", user);
	}
	
	@RequestMapping(value="/signout.htm", method=RequestMethod.GET)
	public String signOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	
	
}
