package com.me.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.query.Query;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
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
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void unfollow(Following follow, User user) {
		try {
			begin();
			Query query = getSession().createQuery("from Following where fId=:fId "
					+ "and userid=:userid");
			query.setString("fId", ""+follow.getfId());
			query.setString("userid",""+user.getUserId());
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
			Query query = getSession().createQuery("from Following where fid=:fId");
			query.setString("fId", ""+user.getUserId());
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
			Query query = getSession().createQuery("from Following where userid=:userid");
			query.setString("userid", ""+user.getUserId());
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
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public List<Long> getFollowing(User user) {
		List<Long> list = new ArrayList<Long>();
		try {
			begin();
			Query query = getSession().createQuery("select fId from Following where userid=:userid");
			query.setString("userid", ""+user.getUserId());
			list = query.list();
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}	
		
		return list;
	}
	
	public void initializeLazy(User user) {
		try {
			begin();
			Hibernate.initialize(user.getListOfTweets());
			
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}	
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<User> getFollowers(User user){
		List<User> list = new ArrayList<User>();
		try {
			begin();
			Query query = getSession().createQuery("select following_user from Following where fId=:fId");
			query.setString("fId", ""+user.getUserId());
			
			list = query.list();
			for(User id : list) {
				System.out.println(id.getUserId());
			}
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		
		return list;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public boolean checkIfFollowing(User user_logged, User user) {
		List<Following> list = null;
		try {
			begin();
			Query query = getSession().createQuery("from Following where userid=:userid and fId=:fId");
			query.setString("userid", ""+user_logged.getUserId());
			query.setString("fId", ""+user.getUserId());
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
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<User> searchUserByAt(String searchString) {
		List<User> users = new ArrayList<User>();
		try {
			begin();
			Criteria criteria = getSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("handle",searchString));
			users = criteria.list();
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return users;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<User> searchUserName(String searchString) {
		List<User> users = new ArrayList<User>();
		try {
			begin();
			Query query = getSession().createQuery("from User where name like :name");
			query.setString("name", "%"+searchString+"%");
			users = query.list();
			for(User user: users){
				Hibernate.initialize(user.getListOfTweets());
				Hibernate.initialize(user.getFollowing());
			}
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return users;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<User> searchHandle(String searchString) {
		List<User> users = new ArrayList<User>();
		try {
			begin();
			Query query = getSession().createQuery("from User where handle=:handle");
			query.setString("handle", searchString);
			users = query.list();
			for(User user: users){
				Hibernate.initialize(user.getListOfTweets());
				Hibernate.initialize(user.getFollowing());
			}
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return users;
	}
	
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void deleteFollowing(User user) {
		try {
			begin();
			Query query = getSession().createQuery("delete from Following where following_user=:userid");
			query.setString("userid", ""+user.getUserId());
			query.executeUpdate();
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void deleteFollowingOfReported(User user) {
		try {
			begin();
			Query query = getSession().createQuery("delete from Following where fId=:fId");
			query.setString("fId", ""+user.getUserId());
			query.executeUpdate();
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
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
	
	public List<Object[]> getPopularUsers() {
		List<Object[]> l = null;
		try {
			begin();

			Criteria criteria = getSession().createCriteria(Following.class);
			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.count("fId"));
			pList.add(Projections.groupProperty("fId"));
			criteria.setProjection(pList);
			l = criteria.list();
//			for(Object[] obj : l) {
//			System.out.println(obj);
//			}
			
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		
		return l;
	}
}
