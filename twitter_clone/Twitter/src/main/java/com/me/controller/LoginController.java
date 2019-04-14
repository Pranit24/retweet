package com.me.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.ReportDao;
import com.me.dao.TweetDao;
import com.me.dao.UserDao;
import com.me.pojo.Following;
import com.me.pojo.Report;
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
	TweetDao tweetDao;
	
	@Autowired
	ReportDao reportDao;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user_logged");
		createStaff();
		if(user_logged==null) {
			User user = new User();
			model.addAttribute("login",user);
			return "welcome";
		}
		List<Long> followingList = userDao.getFollowing(user_logged);
		List<Tweet> tweets = tweetDao.getFollowingTweet(followingList);
		Collections.sort(tweets, new Comparator<Tweet>() {
			  public int compare(Tweet o1, Tweet o2) {
			      return o2.getTimestamp().compareTo(o1.getTimestamp());
			  }
			});
		user_logged.setListOfTweets(tweetDao.getTweet(user_logged));
		userDao.initializeLazy(user_logged);
		user_logged.setFollowing(userDao.getListOfFollowing(user_logged));
		user_logged.setFollowers(userDao.getNumberOfFollowers(user_logged));
		model.addAttribute("followingTweets",tweets);
		
		return "home";
	}
	

	public void createStaff() {
		if(userDao.getUser(new Long(1)) == null) {
			User staff = new User();
			staff.setName("Pranit Staff");
			staff.setRole(true);
			staff.setVerified(true);
			staff.setEmail("pranit@g.com");
			staff.setPassword("pranit");
			staff.setHandle("pranitstaff");
			userDao.register(staff);
		}
	
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
		session.setAttribute("user_logged", user);
		
		return "redirect:/profile/"+user.getHandle();
	}
	
	@RequestMapping(value="/profile/*", method=RequestMethod.GET)
	public ModelAndView profile(HttpServletRequest request, Model model, HttpSession session) {
		String user_url = request.getRequestURI();
		String[] url = user_url.split("/");

		User user_logged = (User) session.getAttribute("user_logged");
		String handle = url[url.length -1];
		if(handle.equals("profile")) return new ModelAndView("redirect:/");

		User user = userDao.getUser(handle);
		
		if(user==null) return new ModelAndView("profile","error",handle);
		
		boolean alreadyFollowing = false;
		if(user_logged != null) {
			alreadyFollowing = userDao.checkIfFollowing(user_logged, user);
		}
		boolean alreadyReported = false;
		if(user_logged != null) {
			alreadyReported = reportDao.checkIfReported(user_logged, user);
		}
		user.setFollowing(userDao.getListOfFollowing(user));
		user.setFollowers(userDao.getNumberOfFollowers(user));
		if(user_logged != null && user_logged.getHandle().equals(user.getHandle()) && user_logged.isRole()) user.setReports(reportDao.getAllreports());
		
		// Get all retweeted tweets
		List<Tweet> retweetedTweets = tweetDao.getRetweetedTweets(user);
//		List<Tweet> retweetedTweets = tweetDao.getFollowingTweet(tweetId);
		user.setListOfTweets(joinAndSortLists(user.getListOfTweets(), retweetedTweets));
		model.addAttribute("sizeOfRetweets", retweetedTweets.size());
		model.addAttribute("following", new Following());
		model.addAttribute("report", new Report(user.getUserId(), user_logged));
		model.addAttribute("alreadyFollowing",alreadyFollowing);
		model.addAttribute("alreadyReported", alreadyReported);
		return new ModelAndView("profile", "user", user);
	}
	
	public List<Tweet> joinAndSortLists(List<Tweet> userTweets, List<Tweet> retweetedTweets) {
		List<Tweet> sorted = new ArrayList<Tweet>();
		sorted.addAll(userTweets);
		sorted.addAll(retweetedTweets);
		Collections.sort(sorted, new Comparator<Tweet>() {
			  public int compare(Tweet o1, Tweet o2) {
			      return o2.getTimestamp().compareTo(o1.getTimestamp());
			  }
			});
		
		return sorted;
	}
	
	@RequestMapping(value="/signout.htm", method=RequestMethod.GET)
	public String signOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged == null) return "redirect:/";
		userDao.deleteFollowing(user_logged);
		userDao.deleteUser(user_logged);
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value="/deleteReported", method=RequestMethod.POST)
	public String deleteUser(HttpServletRequest request, HttpSession session) {
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged==null) return "redirect:/";
		String handle = request.getParameter("toDelete");
		User user = userDao.getUser(handle);
		if(!reportDao.check(user)) return "redirect:/profile/"+user_logged.getHandle()+"/reportPage";
		userDao.deleteFollowingOfReported(user);
		userDao.deleteUser(user);
		
		return "redirect:/profile/"+user_logged.getHandle()+"/reportPage";
	}
	
	@RequestMapping(value="/profile/*/following")
	public String followingPage(HttpServletRequest request, Model model, HttpSession session) {
		String user_url = request.getRequestURI();
		String[] url = user_url.split("/");
		String handle = url[url.length -2];

		if(handle.equals("profile")) return "redirect:/";
		User user = userDao.getUser(handle);
		User user_logged = (User) session.getAttribute("user_logged");
		if(user == null) return "redirect:/";
		model.addAttribute("user",user);
		
		user.setFollowing(userDao.getListOfFollowing(user));
		user.setFollowers(userDao.getNumberOfFollowers(user));
		model.addAttribute("following", new Following());
		
		boolean alreadyFollowing = false;
		if(user_logged != null) {
			alreadyFollowing = userDao.checkIfFollowing(user_logged, user);
		}
		boolean alreadyReported = false;
		if(user_logged != null) {
			alreadyReported = reportDao.checkIfReported(user_logged, user);
		}
		if(user_logged.getHandle().equals(user.getHandle()) && user_logged.isRole()) user.setReports(reportDao.getAllreports());
		model.addAttribute("alreadyFollowing",alreadyFollowing);
		
		List<Long> usersFollowingId = userDao.getFollowing(user);
		List<User> usersFollowing = new ArrayList<User>();
		for(Long id :usersFollowingId) {
			usersFollowing.add(userDao.getUser(id));
		}
		
		getFollowers(usersFollowing);
		model.addAttribute("ListOfUsersFollowing",usersFollowing);
		model.addAttribute("alreadyReported", alreadyReported);
		model.addAttribute("user", user);
		return "followPage";
	}
	
	public void getFollowers(List<User> usersFound) {
		for(User user: usersFound) {
			user.setFollowers(userDao.getNumberOfFollowers(user));
		}
	}
	
	@RequestMapping(value="/profile/*/followers")
	public String followerPage(HttpServletRequest request, Model model, HttpSession session) {
		String user_url = request.getRequestURI();
		String[] url = user_url.split("/");
		String handle = url[url.length -2];

		if(handle.equals("profile")) return "redirect:/";
		User user = userDao.getUser(handle);
		User user_logged = (User) session.getAttribute("user_logged");
		if(user == null) return "redirect:/";
		model.addAttribute("user",user);
		System.out.println("HERE");
		user.setFollowing(userDao.getListOfFollowing(user));
		user.setFollowers(userDao.getNumberOfFollowers(user));
		model.addAttribute("following", new Following());
		
		boolean alreadyFollowing = false;
		if(user_logged != null) {
			alreadyFollowing = userDao.checkIfFollowing(user_logged, user);
		}
		boolean alreadyReported = false;
		if(user_logged != null) {
			alreadyReported = reportDao.checkIfReported(user_logged, user);
		}
		if(user_logged.getHandle().equals(user.getHandle()) && user_logged.isRole()) user.setReports(reportDao.getAllreports());
		model.addAttribute("alreadyFollowing",alreadyFollowing);
		
		
		List<User> usersFollowing = userDao.getFollowers(user);
		getFollowers(usersFollowing);
		model.addAttribute("ListOfUsersFollowing",usersFollowing);
		model.addAttribute("alreadyReported", alreadyReported);
		model.addAttribute("user", user);
		return "followPage";
	}

	
	@RequestMapping(value="/profile/*/reportPage", method=RequestMethod.GET)
	public String reportPage(HttpServletRequest request, Model model, HttpSession session) {
		String user_url = request.getRequestURI();
		String[] url = user_url.split("/");
		String handle = url[url.length -2];

		if(handle.equals("profile")) return "redirect:/";
		User user = userDao.getUser(handle);
		User user_logged = (User) session.getAttribute("user_logged");
		if(user == null) return "redirect:/";
		if(user_logged == null) return "redirect:/";
		model.addAttribute("user",user);
		user.setFollowing(userDao.getListOfFollowing(user));
		user.setFollowers(userDao.getNumberOfFollowers(user));
		model.addAttribute("following", new Following());


		if(user_logged.getHandle().equals(user.getHandle()) && user_logged.isRole()) user.setReports(reportDao.getAllreports());
		
		
		Set<User> reports = reportDao.getAllUserReports();
		for(User usersReported: reports) {
			usersReported.setFollowers(userDao.getNumberOfFollowers(usersReported));
		}
		model.addAttribute("user", user);
		model.addAttribute("ListOfUsersFollowing",reports);
		return "reportPage";
	}
	
}
