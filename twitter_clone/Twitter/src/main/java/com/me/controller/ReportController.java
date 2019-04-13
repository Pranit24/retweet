package com.me.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.me.dao.ReportDao;
import com.me.dao.UserDao;
import com.me.pojo.Report;
import com.me.pojo.User;

@Controller
@RequestMapping("/profile/report/*")
public class ReportController {

	@Autowired
	UserDao userDao;
	
	@Autowired
	ReportDao reportDao;
	
	@RequestMapping(value="/report", method=RequestMethod.POST)
	public String report(HttpServletRequest request, Model model, @ModelAttribute("report") Report report, HttpSession session) {
		String handle = request.getParameter("handle");
		User user = userDao.getUser(handle);
		if(user == null) return "redirect:/";
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged == null) return "redirect:/";
		report.setReportedById(user_logged.getUserId());
		report.setReportedUserId(user);
		reportDao.report(report);
		return "redirect:/profile/"+handle;
	}
	
	@RequestMapping(value="/unreport", method=RequestMethod.POST)
	public String unreport(HttpServletRequest request, Model model, @ModelAttribute("report") Report report, HttpSession session) {
		String handle = request.getParameter("handle");
		User user = userDao.getUser(handle);
		if(user == null) return "redirect:/";
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged == null) return "redirect:/";
		report.setReportedById(user_logged.getUserId());
		report.setReportedUserId(user);
		reportDao.removeReport(user_logged, user);
		return "redirect:/profile/"+handle;
	}

}
