package com.me.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Following {

	@Id
	private Long fId;
	

	public Long getfId() {
		return fId;
	}


	public void setfId(Long fId) {
		this.fId = fId;
	}


	public Following() {
	}



}
