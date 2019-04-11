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
public class LikedTweet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likeId;
	
	@Column(name="userLikedId")
	private Long userLikedId;
	
	public LikedTweet(){
		
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "msgId", nullable = false)
	private Tweet tweetLiked;

	public Tweet getTweetLiked() {
		return tweetLiked;
	}

	public void setTweetLiked(Tweet tweetLiked) {
		this.tweetLiked = tweetLiked;
	}

	public Long getLikeId() {
		return likeId;
	}

	public void setLikeId(Long likeId) {
		this.likeId = likeId;
	}

	public Long getUserLikedId() {
		return userLikedId;
	}

	public void setUserLikedId(Long userLikedId) {
		this.userLikedId = userLikedId;
	}

	
	
	
	
}
