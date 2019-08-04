package com.shopping.base.domain;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 7060381435224185276L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	public Long id;
	@Column(name="addtime")
	public Date addTime;

	@Column(name="deletestatus")
	public boolean deleteStatus;

	public boolean isDeleteStatus() {
		return this.deleteStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAddTime() {
		return addTime;
	}


	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}


	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

}