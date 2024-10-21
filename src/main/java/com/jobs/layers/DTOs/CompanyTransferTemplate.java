package com.jobs.layers.DTOs;

import com.jobs.layers.Entities.companies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyTransferTemplate extends QueryResolutionTemplate{
    public String companyName;
    public String email;
    public Date joinedSince;
    public int rating;
    public String encryptedId;
    public String requestId;
    public String address;
    public  CompanyTransferTemplate(companies com,String token){
        this.encryptedId = com.encryptedId;
        this.companyName = com.name;
        this.email = com.userObject.email;

        this.requestId = token;
        this.rating = com.rating;
        this.joinedSince = com.userObject.createdAt;
        this.address = com.address;
    }
public CompanyTransferTemplate(companies com){

    this.encryptedId = com.encryptedId;
    this.companyName = com.name;
    this.email = com.userObject.email;


    this.rating = com.rating;
    this.joinedSince = com.userObject.createdAt;
    this.address = com.address;
}

}
