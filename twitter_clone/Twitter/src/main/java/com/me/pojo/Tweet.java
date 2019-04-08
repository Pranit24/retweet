package com.me.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




@Entity
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long msgId;
	
	@Column(name = "Message")
	private String message;
	
	@ElementCollection
	@CollectionTable(name = "Likes", joinColumns = @JoinColumn(name = "USERID") )
	private Set<Long> likes = new HashSet<Long>();
	
	@ElementCollection
	@CollectionTable(name = "Retweets", joinColumns = @JoinColumn(name = "USERID") )
	private Set<Long> retweets = new HashSet<Long>();;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TweetedOn")
	private Date timestamp;
	
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
	public Set<Long> getLikes() {
		return likes;
	}
	public void setLikes(Set<Long> likes) {
		this.likes = likes;
	}
	public Set<Long> getRetweets() {
		return retweets;
	}
	public void setRetweets(Set<Long> retweets) {
		this.retweets = retweets;
	}
}
