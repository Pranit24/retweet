package com.me.pojo;

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
	private Long fId;
	
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name="userid", nullable=false)
//	private User user_following;
//
//	public User getUser_following() {
//		return user_following;
//	}
//
//	public void setUser_following(User user_following) {
//		this.user_following = user_following;
//	}

	public Long getfId() {
		return fId;
	}


	public void setfId(Long fId) {
		this.fId = fId;
	}


	public Following() {
	}
	
//	public Following(User u) {
//		this.user_following=u;
//	}


}
