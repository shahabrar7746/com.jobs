package com.jobs.layers.Entities;

import java.util.Date;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Data
public class appliedJobs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;

			@ManyToOne(targetEntity = jobs.class)

	public jobs job;
	@ManyToOne(targetEntity = employee.class)
			//@JoinColumn(unique = false,name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "FK_EMPLOYEE_ID"))

	public employee employee;
	@NotNull
	 public Date applied_date;
	@NotNull
	 @Value("Applied")
	 public String status;
	

}
