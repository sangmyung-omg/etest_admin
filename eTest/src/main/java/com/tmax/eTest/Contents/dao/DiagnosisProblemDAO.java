package com.tmax.eTest.Contents.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="DIAGNOSIS_PROBLEM")
public class DiagnosisProblemDAO {
	
	@Id
	@Column(name="PROB_ID")
	private long probID;
	
	@Column(name="SET_NUM")
	private long setNum;
	
	@ManyToOne
	@JoinColumn(name="PROB_ID", nullable=true, insertable = false, updatable = false)
	private ProblemDAO problem;
	
	
	
}
