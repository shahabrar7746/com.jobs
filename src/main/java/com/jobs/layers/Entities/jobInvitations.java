package com.jobs.layers.Entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@NoArgsConstructor
public class jobInvitations {

	@Id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	public int id;
	
	@ManyToOne(targetEntity = jobs.class)
	@JoinColumn(
			name = "job_id",
			foreignKey =  @ForeignKey (name = "FK_JOB_INVITATION_JOB_ID")
	)
	public jobs jobId;

	@ManyToOne(targetEntity = employee.class)
	@JoinColumn(
			name = "employee_id",
			foreignKey =  @ForeignKey (name = "FK_JOB_INVITATION_EMPLOYEE_ID")
	)

	public employee employeeId;

	public String status = "NOT APPLIED";
	public Date invitation_date = new Date();

	
	
}
