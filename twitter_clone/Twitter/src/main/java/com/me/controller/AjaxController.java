package com.me.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		
		if(tweets.size() > (count-retweetedTweets.size())) {
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
		if(tweets.size() > count) {
			String result = (tweets.size()-count)+" New Tweets";
			return result;
		}
		return null;
	}
	
	@RequestMapping(value = "/getPopular", method=RequestMethod.POST)
	public @ResponseBody String ajaxPopular() {
		System.out.println("HERE??");
		List<Object[]> popularUsers = userDao.getPopularUsers();
		Map<Long, Long> userIdtoNumberOfFollowers = new HashMap<Long, Long>();
		for(Object[] obj: popularUsers) {
			userIdtoNumberOfFollowers.put((Long) obj[1],(Long) obj[0]);
		}
		
		Long first = findMaxInMap(userIdtoNumberOfFollowers).getKey();
		userIdtoNumberOfFollowers.remove(first);
		Long second = findMaxInMap(userIdtoNumberOfFollowers).getKey();
		userIdtoNumberOfFollowers.remove(second);
		Long third = findMaxInMap(userIdtoNumberOfFollowers).getKey();
		userIdtoNumberOfFollowers.remove(third);
		User mostPopular1 = userDao.getUser(first);
		User mostPopular2 = userDao.getUser(second);
		User mostPopular3 = userDao.getUser(third);
		JSONObject json = new JSONObject();
		
		json.put("firstUserName", mostPopular1.getName());
		json.put("firstUserHandle", mostPopular1.getHandle());
		json.put("secondUserName", mostPopular2.getName());
		json.put("secondUserHandle", mostPopular2.getHandle());
		json.put("thirdUserName", mostPopular3.getName());
		json.put("thirdUserHandle", mostPopular3.getHandle());
		
		return json.toJSONString();
	}
	
	public Map.Entry<Long, Long> findMaxInMap(Map<Long, Long> map) {
		Map.Entry<Long, Long> maxEntry = null;
		for(Map.Entry<Long, Long> entry: map.entrySet()) {
			if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		return maxEntry;
	}
	
}
