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
import com.me.pojo.LikedTweet;
import com.me.pojo.Retweet;
import com.me.pojo.Tweet;
import com.me.pojo.User;

@Controller
@RequestMapping("/tweet/*")
public class TweetController {

	@Autowired
	TweetDao tweetDao;

	@RequestMapping(value="/tweet.htm", method=RequestMethod.GET)
	public ModelAndView tweet(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged==null) return new ModelAndView("redirect:/");
		Tweet message = new Tweet();
		model.addAttribute("message", message);
		return new ModelAndView("message");
	}
	
	@RequestMapping(value="/tweet.htm", method=RequestMethod.POST)
	public String tweet(HttpServletRequest request,@ModelAttribute("message") Tweet tweet) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user_logged");
		if(user == null) return "redirect:/";
		tweet.setTimestamp(new Timestamp(System.currentTimeMillis()));
		tweetDao.addTweet(user, tweet);
		return "redirect:/profile/"+user.getHandle();
	}
	
	@RequestMapping(value="/like", method=RequestMethod.GET)
	public String like(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged == null) return "redirect:/";
		String tweet = request.getParameter("tweet");
		Tweet tweetLiked = tweetDao.getTweet(new Long(tweet));
		System.out.println(request.getParameter("handle")+"::::");
		
		if(tweetDao.deleteIfLiked(tweetLiked, user_logged)) {
			return "redirect:/profile/"+request.getParameter("handle");
		}
		LikedTweet liked = new LikedTweet();
		liked.setUserLikedId(user_logged.getUserId());
		liked.setTweetLiked(tweetLiked);
		tweetDao.likedTweet(liked);
		return "redirect:/profile/"+request.getParameter("handle");
	}
	
	@RequestMapping(value="/retweet", method=RequestMethod.GET)
	public String retweet(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged == null) return "redirect:/";
		String tweet = request.getParameter("tweet");
		Tweet tweetRetweeted = tweetDao.getTweet(new Long(tweet));
		
		if(tweetDao.deleteIfRetweeted(tweetRetweeted, user_logged)) {
			return "redirect:/profile/"+request.getParameter("handle");
		}
		
		Retweet retweet = new Retweet();
		retweet.setUserRetweetId(user_logged.getUserId());
		retweet.setTweetRetweeted(tweetRetweeted);
		tweetDao.retweetedTweet(retweet);
		return "redirect:/profile/"+request.getParameter("handle");
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged == null) return "redirect:/";
		Tweet tweet = tweetDao.getTweet(new Long(request.getParameter("tweet")));
		tweetDao.deleteTweet(tweet);
		return "redirect:/profile/"+user_logged.getHandle();
		
		
	}
}
