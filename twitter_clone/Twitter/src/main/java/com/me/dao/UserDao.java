package com.me.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.hibernate.query.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import com.me.pojo.Following;
import com.me.pojo.User;

public class UserDao extends DAO{

	public UserDao() {
		
	}
	
	public boolean register(User u) {
		boolean saved = false;
		try {
			begin();
			getSession().save(u);
			commit();
			saved = true;
		}catch(HibernateException hb) {
			rollback();
		}finally {
			close();
		}
		return saved;
	}
	
	public boolean alreadyExists(String field, String value) {
		boolean exists = false;
		try {
			begin();
			@SuppressWarnings("deprecation")
			Criteria criteria = getSession().createCriteria(User.class);
			criteria.add(Restrictions.eq(field, value));
			criteria.setMaxResults(1);
			User user = (User) criteria.uniqueResult();
			if(user != null) return true;
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return exists;
	}
	
	public User check(String email, String password) {
		User user = null;
		try {
			begin();
			@SuppressWarnings("deprecation")
			Criteria criteria = getSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("email", email));
			criteria.add(Restrictions.eq("password", password));
			criteria.setMaxResults(1);
			user = (User) criteria.uniqueResult();
			if(user == null) return null;
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		
		TweetDao tweetDao = new TweetDao();
		user.setListOfTweets(tweetDao.getTweet(user));
		
		return user;
	}
	
	public User getUser(Long id) {
		return (User) getSession().get(User.class, id);
	}
	
	@SuppressWarnings("deprecation")
	public User getUser(String handle) {
		User user = null;
		try {
			begin();
			Criteria criteria = getSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("handle", handle));
			criteria.setMaxResults(1);
			user = (User) criteria.uniqueResult();
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}	
		
		if(user!=null) {
		TweetDao tweetDao = new TweetDao();
		user.setListOfTweets(tweetDao.getTweet(user));
		}
		return user;
	}
	
	public void editUser(User user) {
		try {
			begin();
			getSession().update(user);
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public User follow(User user, Following follow) {
		try {
			begin();
			String hql = "from Following where userid="+user.getUserId();
			Query query = getSession().createQuery(hql);
			List<Following> followingList = query.list();
			followingList.add(follow);
			System.out.println(follow.getfId());
			Set<Following> followings = new HashSet<Following>(followingList);
			System.out.println("LIST SIZE BEFORE: "+user.getFollowing().size());
			System.out.println(followings.size());
			user.setFollowing(followings);
			System.out.println("LIST SIZE :"+user.getFollowing().size()+":"+user.getUserId()+":"+user.getHandle());
			getSession().update(user);
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return user;
	}
	
	@SuppressWarnings("rawtypes")
	public Integer getNumberOfFollowers(User user) {
		Integer num = null;
		try {
			begin();
			Query query = getSession().createQuery("from Following where fid="+user.getUserId());
			num = query.list().size();
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}	
		return num;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Long> getFollowing(User user) {
		List<Long> list = null;
		try {
			begin();
			Query query = getSession().createQuery("select fId from Following where userid="+user.getUserId());
			list = query.list();
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}	
		
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean checkIfFollowing(User user_logged, User user) {
		List<Following> list = null;
		try {
			begin();
			Query query = getSession().createQuery("from Following where userid="+user_logged.getUserId()+" and fId="+user.getUserId());
			list = query.list();
			if(list.size() == 0) return false;
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public User search(String searchString) {
		User user = null;
		try {
			begin();
//			Query query = getSession().createQuery("from User where handle="+searchString);
			Criteria criteria = getSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("handle",searchString));
			criteria.setMaxResults(1);
			user = (User) criteria.uniqueResult();
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return user;
	}
}
