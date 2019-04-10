package com.me.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




@Entity
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long msgId;
	
	@Column(name = "Message")
	private String message;
	
	@OneToMany(mappedBy = "tweetLiked")
//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name = "msgID", nullable = false)
	private Set<LikedTweet> likes = new HashSet<LikedTweet>();
	
	@OneToMany(mappedBy = "tweetRetweeted")
	private Set<Retweet> retweets = new HashSet<Retweet>();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TweetedOn")
	private Date timestamp;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "USERID", nullable = false)
	private User tweet_user;
	
	
	public User getUser() {
		return tweet_user;
	}

	public void setUser(User user) {
		this.tweet_user = user;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Tweet() {
		
	}
	public long getMsgId() {
		return msgId;
	}
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Set<LikedTweet> getLikes() {
		return likes;
	}

	public void setLikes(Set<LikedTweet> likes) {
		this.likes = likes;
	}

	public Set<Retweet> getRetweets() {
		return retweets;
	}

	public void setRetweets(Set<Retweet> retweets) {
		this.retweets = retweets;
	}
}
