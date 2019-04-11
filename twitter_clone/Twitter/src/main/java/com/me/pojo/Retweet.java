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
public class Retweet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long retweetPrimaryId;
	
	@Column(name="RetweetId")
	private Long userRetweetId;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "msgId", nullable = false)
	private Tweet tweetRetweeted;

	
	public Retweet() {
		
	}
	
	public Long getRetweetPrimaryId() {
		return retweetPrimaryId;
	}

	public void setRetweetPrimaryId(Long retweetPrimaryId) {
		this.retweetPrimaryId = retweetPrimaryId;
	}


	public Long getUserRetweetId() {
		return userRetweetId;
	}

	public void setUserRetweetId(Long userRetweetId) {
		this.userRetweetId = userRetweetId;
	}

	public Tweet getTweetRetweeted() {
		return tweetRetweeted;
	}

	public void setTweetRetweeted(Tweet tweetRetweeted) {
		this.tweetRetweeted = tweetRetweeted;
	}
	
}
