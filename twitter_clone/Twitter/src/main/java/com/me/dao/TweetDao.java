package com.me.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.me.pojo.LikedTweet;
import com.me.pojo.Retweet;
import com.me.pojo.Tweet;
import com.me.pojo.User;

public class TweetDao extends DAO{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Tweet> getTweet(User user) {
		List<Tweet> tweets= null;
		try {
			begin();
			Query query = getSession().createQuery("from Tweet where userid="+user.getUserId()+" order by TweetedOn desc");
			tweets = query.list();
			for(Tweet tweet: tweets) {
				Hibernate.initialize(tweet.getLikes());
				Hibernate.initialize(tweet.getRetweets());
			}
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		
		return tweets;
	}


	public void addTweet(User user, Tweet tweet) {
		
		try {
			begin();
			tweet.setUser(user);
			getSession().save(tweet);
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Tweet> getFollowingTweet(List<Long> listOfFollowing) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		try {
			begin();
			for(Long useridLong : listOfFollowing) {
				Query query = getSession().createQuery("from Tweet where userid="+useridLong);
				List<Tweet> tweetsFromUser = query.list();
				for(Tweet tw: tweetsFromUser) {
					Hibernate.initialize(tw.getLikes());
					Hibernate.initialize(tw.getRetweets());
					tweets.add(tw);
				}
			}
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return tweets;
	}
	
	public Tweet getTweet(Long id) {
		return (Tweet) getSession().get(Tweet.class, id);
	}
	
	public void likedTweet(LikedTweet likedTweet) {
		try {
			begin();
			getSession().save(likedTweet);
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
	}
	
	public void retweetedTweet(Retweet retweetedTweet) {
		try {
			begin();
			getSession().save(retweetedTweet);
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean deleteIfLiked(Tweet tweet, User user_logged) {
		try {
			begin();
			Query query = getSession().createQuery("from LikedTweet where userLikedId="+user_logged.getUserId()+" and msgId="+tweet.getMsgId());
			List<LikedTweet> liked = query.list();
			if(liked.size() == 0) return false;
			LikedTweet likedTweet = liked.get(0);
			if(likedTweet !=null) {
				getSession().delete(likedTweet);
				commit();
				return true;
			}
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean deleteIfRetweeted(Tweet tweet, User user_logged) {
		try {
			begin();
			Query query = getSession().createQuery("from Retweet where RetweetId="+user_logged.getUserId()+" and msgId="+tweet.getMsgId());
			List<Retweet> retweet = query.list();
			if(retweet.size() == 0) return false;
			Retweet retweetedTweet = retweet.get(0);
			if(retweetedTweet !=null) {
				getSession().delete(retweetedTweet);
				commit();
				return true;
			}
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		
		return false;
	}
	
	public void deleteTweet(Tweet tweet) {
		try {
			begin();
			getSession().delete(tweet);
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
	}
}
