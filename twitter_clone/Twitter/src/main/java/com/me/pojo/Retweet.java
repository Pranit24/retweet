package com.me.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TweetedOn")
	private Date retweet_timestamp;
	
	
	public Date getRetweet_timestamp() {
		return retweet_timestamp;
	}

	public void setRetweet_timestamp(Date retweet_timestamp) {
		this.retweet_timestamp = retweet_timestamp;
	}

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
