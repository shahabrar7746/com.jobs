package com.jobs.layers.DTOs;

import com.jobs.helper;
import com.jobs.layers.Entities.companies;
import com.jobs.layers.Entities.jobs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobsTransferTemplate extends QueryResolutionTemplate {
    @Autowired
    private helper help;
    public boolean isApplied;
    public String requestId;
 public   String address, contactPerson,description,role,qualification,encryptedId;
    public int salary;
    public CompanyTransferTemplate company;
    public Date datePosted;
    public String companyUrl;
    public JobsTransferTemplate(jobs curJob,String token,boolean isApplied){
     this.role = curJob.role;
     this.salary = curJob.salary;
     this.encryptedId = curJob.encryptedId;
     this.datePosted = curJob.date;
     this.contactPerson = curJob.contactPerson;
     this.qualification = curJob.qualification;
     this.description = curJob.description;
     this.requestId = token;
this.company = new CompanyTransferTemplate(curJob.company,token);
this.address = curJob.address;
this.isApplied = isApplied;
    }
    public JobsTransferTemplate(jobs curJob,String token){
        this.role = curJob.role;
        this.salary = curJob.salary;
        this.encryptedId = curJob.encryptedId;
        this.datePosted = curJob.date;
        this.contactPerson = curJob.contactPerson;
        this.qualification = curJob.qualification;
        this.description = curJob.description;
        this.requestId = token;
        this.company = new CompanyTransferTemplate(curJob.company,token);
        this.address = curJob.address;

    }



    public JobsTransferTemplate(jobs curJob){
        this.role = curJob.role;
        this.salary = curJob.salary;
        this.encryptedId = curJob.encryptedId;
        this.datePosted = curJob.date;
        this.contactPerson = curJob.contactPerson;
        this.qualification = curJob.qualification;
        this.description = curJob.description;

        this.address = curJob.address;
    }

}
