package com.tmax.eTest.Common.model.uk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tmax.eTest.Common.model.problem.Problem;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@IdClass(ProbUKCompositeKey.class)
//@ToString(exclude = "probID")
@Table(name="PROBLEM_UK_REL")
public class ProblemUKRelation{
	@Id
	@ManyToOne
	@JoinColumn(name="PROB_ID")
	private Problem probID;
	
	@Id
	@ManyToOne
	@JoinColumn(name="UK_ID")
	private UkMaster ukId;
	
	@Column(name = "PROB_ID", insertable = false, updatable = false)
	private Integer probIDOnly;
	
	@Column(name = "UK_ID", insertable = false, updatable = false)
	private Integer ukIDOnly;
	
}
