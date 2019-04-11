package com.me.controller;

import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.me.dao.UserDao;
import com.me.pojo.User;
import com.me.validator.EditValidator;
import com.me.validator.RegisterValidator;

/**
 * @author Pranit
 *
 */
@Controller
@RequestMapping("/register/*")
public class RegisterController {

	@Autowired
	UserDao userDao;
	
	@RequestMapping(value="/create.htm", method=RequestMethod.GET)
	public String register(Model model) {
		User user = new User();
		model.addAttribute("register",user);
		return "register";
	}
	
	@RequestMapping(value="/create.htm", method=RequestMethod.POST)
	public String register(Model model, HttpServletRequest request,@ModelAttribute("register") User user, BindingResult results) {
		RegisterValidator regValid = new RegisterValidator();
		regValid.validate(user, results);
		if(results.hasErrors()) {
			return "register";
		}
		userDao.register(user);
//		HttpSession session = request.getSession();
//		session.setAttribute("user_logged", user);
		
		return "redirect:/";
		
	}
	
	@RequestMapping(value="/edit.htm", method=RequestMethod.GET)
	public String edit(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged==null) return "redirect:/";
		model.addAttribute("update", user_logged);
		return "editProfile";
	}
	

	// TODO check if its an image
	// do it in validator?
	@RequestMapping(value="/edit.htm", method=RequestMethod.POST)
	public String edit(HttpServletRequest request, @ModelAttribute("update") User updated_user, BindingResult results, HttpSession session,
			@RequestPart(value = "profile", required = false) MultipartFile profileImage,
			@RequestPart(value = "profileBanner", required = false) MultipartFile profileBanner) {
		User user_logged = (User) session.getAttribute("user_logged");
		if(user_logged==null) return "redirect:/";
		
		// Check if uploaded file is JPG, BMP, GIF, PNG
		if(!profileImage.isEmpty()) {
			try {
				ImageIO.read(profileImage.getInputStream()).toString();
			}catch (Exception e) {
				System.out.println("NOT IMAGE");
				results.rejectValue("profileImage", "","-The profile image you uploaded was not an image!");
			}
		}
		if(!profileBanner.isEmpty()) {
			try {
				ImageIO.read(profileBanner.getInputStream()).toString();
			}catch (Exception e) {
				System.out.println("NOT IMAGE");
				results.rejectValue("profileBanner", "","-The profile banner you uploaded was not an image!");
			}
		}
		
		
		
		String removeProfile = request.getParameter("removeProfile");
		String removeBanner = request.getParameter("removeBanner");
		updated_user.setUserId(user_logged.getUserId());
		EditValidator editValid = new EditValidator();
		editValid.validate(updated_user, results);
		if(results.hasErrors()) {
			return "editProfile";
		}
		updateUser(user_logged, updated_user);
		// Profile image
		if(!profileImage.isEmpty() && removeProfile == null) {
			user_logged.setProfileImage(uploadImage(user_logged, profileImage));
		}
		if(removeProfile != null && removeProfile.equals("true")){
			user_logged.setProfileImage(null);
		}
		// Profile banner
		if(!profileBanner.isEmpty() && removeBanner == null) {
			user_logged.setProfileBackgroundImage(uploadImage(user_logged, profileBanner));
		}
		if(removeBanner != null && removeBanner.equals("true")){
			user_logged.setProfileBackgroundImage(null);
		}
		
		userDao.editUser(user_logged);

		return "redirect:/profile/"+user_logged.getHandle();
	}
	
	public byte[] uploadImage(User user, MultipartFile uploadedFile) {

        byte[] bFile = null;
		try {
			bFile = uploadedFile.getBytes();
		} catch (IOException e) {
			user.setProfileImage(null);
			return null;
		}
        bFile = Base64.getEncoder().encode(bFile);
        return bFile;
	}
	
	/**
	 * @param user_logged - Currect user logged to the site
	 * @param updated_user - User's updated profile
	 */
	public void updateUser(User user_logged, User updated_user) {
		user_logged.setEmail(updated_user.getEmail());
		user_logged.setPassword(updated_user.getPassword());
		user_logged.setName(updated_user.getName());
		user_logged.setHandle(updated_user.getHandle());
		user_logged.setDescription(updated_user.getDescription());
	}
	
}
