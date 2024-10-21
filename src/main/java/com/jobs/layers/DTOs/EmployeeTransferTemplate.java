package com.jobs.layers.DTOs;

import com.jobs.layers.Entities.employee;
import com.jobs.layers.Entities.user;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public class EmployeeTransferTemplate extends QueryResolutionTemplate {

    public String firstName;
    public String lastName;
    public String image_address;
    public String resume_address;
    public String certificate_address;
    public String skills;
    public String email;
    public String encrypetdId;
public Date joined;

public EmployeeTransferTemplate(employee emp, user curUser){
    this.certificate_address = emp.certificate_addresses;
    this.email = curUser.email;
    this.encrypetdId = emp.encryptedId;
    this.firstName = curUser.first_name;
    this.lastName = curUser.last_name;
    this.image_address = emp.photo_address;
    this.joined = curUser.createdAt;
    this.skills = emp.skills;
    this.resume_address = emp.resume_address;
}

    public EmployeeTransferTemplate() {

    }
}
