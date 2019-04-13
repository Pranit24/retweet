package com.me.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import com.me.pojo.Report;
import com.me.pojo.User;

public class ReportDao extends DAO {

	public ReportDao() {
		
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Set<Report> getReportedBy(User user){
		List<Report> reportsList = new ArrayList<Report>();
		Set<Report> reports = null;
		try {
			begin();
			Criteria criteria = getSession().createCriteria(Report.class);
			criteria.add(Restrictions.eq("userid", user.getUserId()));
			reportsList = criteria.list();
			reports = new HashSet<Report>(reportsList);
			commit();
		}catch(HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return reports;
	}
	
	public void report(Report report){
		try {
			begin();
			getSession().save(report);
			commit();
		}catch(HibernateException e) {
			rollback();
		}finally {
			close();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean checkIfReported(User user_logged, User user) {
		List<Report> report = new ArrayList<Report>();
		try {
			begin();
			Query query = getSession().createQuery("from Report where reportedById="+user_logged.getUserId()+" "
					+ "and reportedUserId="+user.getUserId());
			report = query.list();
			if(report.size() == 0) return false;
			commit();
		}catch (HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public void removeReport(User user_logged, User user) {
		try {
			begin();
			Query query = getSession().createQuery("from Report where reportedById="+user_logged.getUserId()+" "
					+ "and reportedUserId="+user.getUserId());
			Report report = (Report) query.uniqueResult();
			getSession().delete(report);
			commit();
		}catch(HibernateException e) {
			rollback();
		}finally {
			close();
		}
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Set<User> getAllUserReports(){
		List<User> reportsList = new ArrayList<User>();
		Set<User> reports = null;
		try {
			begin();
			Criteria criteria = getSession().createCriteria(User.class);
			Criteria reportCriteria = criteria.createCriteria("reports");
			reportsList = reportCriteria.list();
			reports = new HashSet<User>(reportsList);
			commit();
		}catch(HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return reports;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Set<Report> getAllreports(){
		List<Report> reportsList = new ArrayList<Report>();
		Set<Report> reports = null;
		try {
			begin();
			Criteria criteria = getSession().createCriteria(Report.class);
			reportsList = criteria.list();
			System.out.println(reportsList.size());
			reports = new HashSet<Report>(reportsList);
			commit();
		}catch(HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return reports;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean check(User user) {
		boolean exists = false;
		try {
			begin();
			Query query = getSession().createQuery("from Report where reportedUserId="+user.getUserId());
			if(query.list().size() > 0) return true;
			commit();
		}catch(HibernateException e) {
			rollback();
		}finally {
			close();
		}
		return exists;
	}
}
