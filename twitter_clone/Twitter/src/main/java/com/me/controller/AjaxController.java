package com.me.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.me.dao.TweetDao;
import com.me.dao.UserDao;
import com.me.pojo.Tweet;
import com.me.pojo.User;

@Controller
@RequestMapping(value = "/ajax/*")
public class AjaxController {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	TweetDao tweetDao;

	@RequestMapping(value = "/aa", method=RequestMethod.GET)
	public String a() {
		return "temp";
	}
	
	
	@RequestMapping(value = "/top", method=RequestMethod.GET)
	public @ResponseBody String ajax(HttpServletRequest request, HttpServletResponse response) {
       System.out.println("HERE");
		List<Tweet> list= tweetDao.getAllTweets();
//		String[] result = new String[list.size()];
//		for(int i=0;i<list.size();i++) {
//			result[i] = list.get(i).getMessage();
//		}
		Random r = new Random();
		int i = r.nextInt(list.size());
		return list.get(i).getMessage();
	}
	@RequestMapping(value = "/check", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody String ajaxUser(HttpServletRequest request) {
		String handle = request.getParameter("user");
		User user = userDao.getUser(handle);
		List<Tweet> tweets = tweetDao.getTweet(user);
		int count = Integer.parseInt(request.getParameter("tweetCount"));
		List<Tweet> retweetedTweets = tweetDao.getRetweetedTweets(user);
		System.out.println(tweets.size()+":"+(count-retweetedTweets.size()));
		JSONObject json = new JSONObject();
		json.put("tweet",tweets.get(0));
		
		if(tweets.size() > (count-retweetedTweets.size())) {
			System.out.println("IN "+(tweets.size()-(count-retweetedTweets.size()))+" New Tweets");
			String result = (tweets.size()-(count-retweetedTweets.size()))+" New Tweets";
			return result;
		}
		return null;
	}
	
	@RequestMapping(value = "/checkHome", method=RequestMethod.POST)
	public @ResponseBody String ajaxHome(HttpServletRequest request) {
		String handle = request.getParameter("user");
		User user = userDao.getUser(handle);
		List<Long> followingList = userDao.getFollowing(user);
		List<Tweet> tweets = tweetDao.getFollowingTweet(followingList);

		int count = Integer.parseInt(request.getParameter("tweetCount"));
		System.out.println(tweets.size()+":"+count);
		if(tweets.size() > count) {
			System.out.println("IN "+(tweets.size()-count)+" New Tweets");
			String result = (tweets.size()-count)+" New Tweets";
			return result;
		}
		return null;
	}
	
	
}
