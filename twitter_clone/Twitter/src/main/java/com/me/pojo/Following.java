package com.me.pojo;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class Following {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long followingId;
	
	public Long getFollowingId() {
		return followingId;
	}


	public void setFollowingId(Long followingId) {
		this.followingId = followingId;
	}


	@Column(name="fId")
	private Long fId;

	public Long getfId() {
		return fId;
	}


	public void setfId(Long fId) {
		this.fId = fId;
	}


	public Following() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", nullable = false)
	private User following_user;

	public User getFollowing_user() {
		return following_user;
	}


	public void setFollowing_user(User following_user) {
		this.following_user = following_user;
	}


}
