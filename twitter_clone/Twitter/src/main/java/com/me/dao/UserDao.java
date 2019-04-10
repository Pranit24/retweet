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
	

	@SuppressWarnings("deprecation")
	public User alreadyExists(String field, String value) {
		User user = null;
		try {
			begin();
			Criteria criteria = getSession().createCriteria(User.class);
			criteria.add(Restrictions.eq(field, value));
			criteria.setMaxResults(1);
			user = (User) criteria.uniqueResult();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return user;
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
			getSession().merge(user);
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}	
	}


	public void follow(Following follow) {
		try {
			begin();
			getSession().save(follow);
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void unfollow(Following follow, User user) {
		try {
			begin();
			Query query = getSession().createQuery("from Following where fId="+follow.getfId()+" and userid="+user.getUserId());
			Following follower = (Following) query.uniqueResult();
			getSession().delete(follower);
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
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
	public Set<Following>  getListOfFollowing(User user) {
		Set<Following> following = null;
		try {
			begin();
			Query query = getSession().createQuery("from Following where userid="+user.getUserId());
			List<Following> list = query.list();
			following = new HashSet<Following>(list);
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}	
		
		return following;
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
	
	public void deleteUser(User user) {
		try {
			begin();
			getSession().delete(user);
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
	}
}
