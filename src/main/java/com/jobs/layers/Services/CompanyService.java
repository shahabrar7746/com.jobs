package com.jobs.layers.Services;

import com.jobs.layers.DTOs.EmployeeTransferTemplate;
import com.jobs.layers.DTOs.JobInvitation;
import com.jobs.layers.DTOs.JobPostTemplate;
import com.jobs.layers.Entities.companies;
import com.jobs.layers.Exceptions.*;
import com.jobs.layers.Responses.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.jobs.layers.DTOs.CompanyTransferTemplate;

import java.net.UnknownHostException;
import java.util.List;

public interface CompanyService {
    public Response<String> save(companies com);

    Response<CompanyTransferTemplate> fetchByEncrypetdId(String id) throws InvalidEncryptedIdException;

   public ResponseEntity<Response<String>> postJob(JobPostTemplate job) throws TOKEN_EXPIRED;

    ResponseEntity<Response<List<EmployeeTransferTemplate>>> findAllEmployeeByQuery(String token, String query, HttpServletRequest req) throws TOKEN_EXPIRED, UnknownHostException;

    public ResponseEntity<Response<String>> invite(JobInvitation invite, final HttpServletRequest req) throws TOKEN_EXPIRED, NoJobsFoundException, NoEmployeeFoundException, InvalidEncryptedIdException, InviteAlreadySentException, UnknownHostException;

}
