package com.jobs.layers.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public class JobPostTemplate   extends QueryResolutionTemplate{
    public   String address, contactPerson,description,role,qualification;
    public int salary;
    public String loginToken;

    public int contactNumber;
}
