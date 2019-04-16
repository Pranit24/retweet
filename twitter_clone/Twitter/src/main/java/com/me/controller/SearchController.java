package com.me.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.TweetDao;
import com.me.dao.UserDao;
import com.me.pojo.User;

@Controller
@RequestMapping("/search/*")
public class SearchController {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	TweetDao tweetDao;

	@RequestMapping(value="/*", method=RequestMethod.GET)
	public ModelAndView searchUser(HttpServletRequest request, Model model) {
		String search = request.getParameter("search");
		
		if(search.contains("@")) {
			List<User> users = new ArrayList<User>();
			String searchString = search.replace("@", "");
			users = userDao.searchUserByAt(searchString);
			if(users.size() == 0) return new ModelAndView("profile","error", search);
			return new ModelAndView("redirect:/profile/"+users.get(0).getHandle());
		}else {
			List<User> UsersFoundByHandle = userDao.searchHandle(search);
			List<User> UsersFoundByName = userDao.searchUserName(search);
			getFollowers(UsersFoundByHandle);
			getFollowers(UsersFoundByName);
			
			model.addAttribute("UsersFoundByHandle", UsersFoundByHandle);;
			model.addAttribute("UsersFoundByName", UsersFoundByName);
			model.addAttribute("TweetsFoundByHashtag",tweetDao.searchTweet(search));
			

			
			return new ModelAndView("searchResult");
		}
	}
	
	public void getFollowers(List<User> usersFound) {
		for(User user: usersFound) {
			user.setFollowers(userDao.getNumberOfFollowers(user));
		}
	}
	
	
}
