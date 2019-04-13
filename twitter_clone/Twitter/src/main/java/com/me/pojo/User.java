package com.me.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name="Email")
	private String email;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Handle")
	private String handle;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tweet_user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tweet> listOfTweets = new ArrayList<Tweet>();
	
	@Column(name="Followers")
	private Integer followers;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "following_user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Following> following = new HashSet<Following>();
	
	@Column(name = "Role")
	private boolean role=false;
	
	@Column(name = "Verified")
	private boolean verified=false;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="ProfileImage", length = 100000)
	private byte[] profileImage;
	
	@Column(name="BackgroundImage", length = 100000)
	private byte[] profileBackgroundImage;
	
	@OneToMany(mappedBy = "reportedUserId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Report> reports = new HashSet<Report>();
	
	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports = reports;
	}

	public byte[] getProfileBackgroundImage() {
		return profileBackgroundImage;
	}

	public void setProfileBackgroundImage(byte[] profileBackgroundImage) {
		this.profileBackgroundImage = profileBackgroundImage;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}
	
	public String getProfileImageAsString() {
		return new String(getProfileImage());
	}

	public String getProfileBackgroundImageAsString() {
		return new String(getProfileBackgroundImage());
	}
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public User() {
		
	}
	
	public User(Long id) {
		this.userId = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	public List<Tweet> getListOfTweets() {
		
		return listOfTweets;
	}

	public void setListOfTweets(List<Tweet> listOfTweets) {
		this.listOfTweets = listOfTweets;
	}

	public Integer getFollowers() {
		return followers;
	}

	public void setFollowers(Integer followers) {
		this.followers = followers;
	}

	public Set<Following> getFollowing() {
		return following;
	}
	public void setFollowing(Set<Following> following) {
		this.following = following;
	}

	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
}
