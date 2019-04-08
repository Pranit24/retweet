package com.me.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addTweet(User user, Tweet tweet) {
		
		try {
			begin();
			Query query = getSession().createQuery("from Tweet where userid="+user.getUserId()+" order by TweetedOn desc");
			List<Tweet> tweets = query.list();
			tweets.add(0,tweet);
			user.setListOfTweets(tweets);
			getSession().update(user);
			for(Tweet tw: tweets) {
				Hibernate.initialize(tw.getLikes());
				Hibernate.initialize(tw.getRetweets());
			}
			commit();
			
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
	}
}
