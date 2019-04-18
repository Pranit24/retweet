package com.me.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import com.me.pojo.LikedTweet;
import com.me.pojo.Retweet;
import com.me.pojo.Tweet;
import com.me.pojo.User;

public class TweetDao extends DAO{

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public List<Tweet> getTweet(User user) {
		List<Tweet> tweets= new ArrayList<Tweet>();
		try {
			begin();
			Query query = getSession().createQuery("from Tweet where userid=:userid order by TweetedOn desc");
			query.setString("userid",""+user.getUserId());
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
				Query query = getSession().createQuery("from Tweet where userid=:userIdLong");
				query.setString("userIdLong", ""+useridLong);
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
		Tweet tw = (Tweet) getSession().get(Tweet.class, id);
		try {
			begin();
			tw = getSession().get(Tweet.class, id);
			Hibernate.initialize(tw.getLikes());
			Hibernate.initialize(tw.getRetweets());
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return tw;
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
			Query query = getSession().createQuery("from LikedTweet where userLikedId=:userLikedId and msgId=:msgId");
			query.setString("userLikedId", ""+user_logged.getUserId());
			query.setString("msgId", ""+tweet.getMsgId());
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
			Query query = getSession().createQuery("from Retweet where RetweetId=:retweetId and msgId=:msgId");
			query.setString("retweetId", ""+user_logged.getUserId());
			query.setString("msgId", ""+tweet.getMsgId());
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
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Tweet> searchTweet(String searchString) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		if(!searchString.contains("#")) searchString = "#"+searchString;
		try {
			begin();
			Criteria tweetCritria = getSession().createCriteria(Tweet.class);
			Query query = getSession().createQuery("from Tweet where message like :message");
			query.setString("message", "%"+searchString+"%");
			tweets = query.list();
			for(Tweet user: tweets){
				Hibernate.initialize(user.getLikes());
				Hibernate.initialize(user.getRetweets());
			}
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return tweets;
	}
	
	public void deleteTweet(Tweet tweet) {
		try {
			begin();
			getSession().clear();
			getSession().delete(tweet);
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List<Tweet> getRetweetedTweets(User user) {
		List<Tweet> ListOfMsgId = new ArrayList<Tweet>();
		try {
			begin();
			Query query = getSession().createQuery("select tweetRetweeted from Retweet where RetweetId="+user.getUserId());
			ListOfMsgId = query.list();
			for(Tweet tweet: ListOfMsgId) {
				Hibernate.initialize(tweet.getLikes());
				Hibernate.initialize(tweet.getRetweets());
			}
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return ListOfMsgId;
	}
	
	public List<Tweet> getAllTweets(){
		List<Tweet> ListOfMsgId = new ArrayList<Tweet>();
		try {
			begin();
			Query query = getSession().createQuery("from Tweet");
			ListOfMsgId = query.list();
			for(Tweet tweet: ListOfMsgId) {
				Hibernate.initialize(tweet.getLikes());
				Hibernate.initialize(tweet.getRetweets());
			}
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return ListOfMsgId;
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public List<Tweet> getLikedTweet(User user) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		try {
			begin();
			Query query = getSession().createQuery("select tweetLiked from LikedTweet where userLikedId=:userid");
			query.setString("userid", ""+user.getUserId());
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
}
