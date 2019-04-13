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
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reportId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "reportedUserId", nullable = false)
	private User reportedUserId;
	
	@Column(name = "reportedById")
	private Long reportedById;

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	public Report() {
		
	}
	
	public Report(Long reportId, User user_logged) {
		this.reportedById = reportId;
		this.reportedUserId = user_logged;
	}


	public User getReportedUserId() {
		return reportedUserId;
	}

	public void setReportedUserId(User reportedUserId) {
		this.reportedUserId = reportedUserId;
	}

	public Long getReportedById() {
		return reportedById;
	}

	public void setReportedById(Long reportedById) {
		this.reportedById = reportedById;
	}
	
	
}
