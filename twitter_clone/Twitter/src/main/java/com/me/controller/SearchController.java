package com.me.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.UserDao;
import com.me.pojo.User;

@Controller
@RequestMapping("/search/*")
public class SearchController {
	
	@Autowired
	UserDao userDao;

	@RequestMapping(value="/*", method=RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request) {
		String search = request.getParameter("search");
		String searchString = search.replace("@", "");
		User user = userDao.search(searchString);
		if(user == null) return new ModelAndView("profile","error", search);
		return new ModelAndView("redirect:/profile/"+user.getHandle());
	}
}
