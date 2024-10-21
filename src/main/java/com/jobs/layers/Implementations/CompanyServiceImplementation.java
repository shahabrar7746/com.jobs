package com.jobs.layers.Implementations;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.jobs.helper;
import com.jobs.layers.DTOs.CompanyTransferTemplate;
import com.jobs.layers.DTOs.EmployeeTransferTemplate;
import com.jobs.layers.DTOs.JobInvitation;
import com.jobs.layers.DTOs.JobPostTemplate;
import com.jobs.layers.Entities.*;
import com.jobs.layers.Events.JobInviteEvent;
import com.jobs.layers.Exceptions.*;
import com.jobs.layers.Messages.Mails.MailBodies;
import com.jobs.layers.Messages.Mails.MailSubjects;
import com.jobs.layers.Messages.StringMessages;
import com.jobs.layers.Repositories.*;
import com.jobs.layers.Services.CompanyService;
import com.jobs.layers.Services.EmployeService;
import com.jobs.layers.Services.JobsService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

@Service
public class CompanyServiceImplementation implements CompanyService {
    @Autowired
    private companies_repo comRepo;
    @Autowired
    private token_repo loginTokenRepo;

    @Autowired
    private JobsService jobsService;

@Autowired
private jobs_repo jobRepo;
     @Autowired
    private EmployeService employeService;

@Autowired
private job_invitation_repo invitationRepo;
     @Autowired
     private employee_repo empRepo;

@Autowired
private user_repo userRepo;
@Autowired
helper help;
@Autowired
private ApplicationEventPublisher publisher;


    public String save(companies com) {
        comRepo.save(com);
        return "Saved!";
    }

    @Override
    public CompanyTransferTemplate fetchByEncrypetdId(String id) throws InvalidEncryptedIdException {
           companies com = comRepo.findByEncryptedId(id);
           if(com == null){
               throw new InvalidEncryptedIdException(HttpStatus.BAD_REQUEST, StringMessages.INAVLID_ENCRYPTED_ID);
           }

        CompanyTransferTemplate companyTemplate = new CompanyTransferTemplate(com);
           user userObject = com.userObject;
           companyTemplate.companyName = com.name;
           companyTemplate.email = userObject.email;
           companyTemplate.address = com.address;
           companyTemplate.encryptedId = com.encryptedId;
           companyTemplate.rating = com.rating;
           companyTemplate.joinedSince = userObject.createdAt;
           return companyTemplate;
    }

    @Override
    public ResponseEntity<String> postJob(JobPostTemplate job) throws TOKEN_EXPIRED {
        tokens curLoginToken = loginTokenRepo.findByToken(job.loginToken);
        if(curLoginToken == null){
            throw new TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, StringMessages.TOKEN_EXPIRED);
        }
        jobs curJobPost = new jobs();
        companies jobCompany = comRepo.findByUserObject(curLoginToken.userObj);

        curJobPost.company = jobCompany;
        curJobPost.address = job.address;
        curJobPost.contactPerson = job.contactPerson;
        curJobPost.description = job.description;
        curJobPost.contact_number = job.contactNumber;
        curJobPost.date = new Date();
        curJobPost.qualification = job.qualification;
        curJobPost.role = job.role;
        curJobPost.encryptedId = help.getNewUUID();
        curJobPost.salary = job.salary;
        jobsService.save(curJobPost);
        //increase expiration time of token.
        loginTokenRepo.save(help.increaseExpirationTimeOfLoginTokens(curLoginToken));
        return ResponseEntity.ok(curLoginToken.token);
    }

    @Override
    public ResponseEntity<List<EmployeeTransferTemplate>> findAllEmployeeByQuery(String token, String query, final HttpServletRequest req) throws TOKEN_EXPIRED, UnknownHostException {
      tokens curToken = loginTokenRepo.findByToken(token);
      if(curToken == null || help.isTokenExpired(curToken)){
          throw new TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, StringMessages.TOKEN_EXPIRED);
      }
      companies com = comRepo.findByUserObject(curToken.userObj);

     List<EmployeeTransferTemplate> resolutionList = employeService.findEmployeeByQuery(query,com,req);

     loginTokenRepo.save(help.increaseExpirationTimeOfLoginTokens(curToken));
        return ResponseEntity.ok(resolutionList);

    }

    @Override
    public ResponseEntity<String> invite(JobInvitation invite, final HttpServletRequest req) throws TOKEN_EXPIRED, NoJobsFoundException, NoEmployeeFoundException, InvalidEncryptedIdException, InviteAlreadySentException, UnknownHostException {
      tokens curToken = loginTokenRepo.findByToken(invite.token);

      if(curToken == null || help.isTokenExpired(curToken)){
          throw new TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, StringMessages.TOKEN_EXPIRED);
      }
        loginTokenRepo.save(help.increaseExpirationTimeOfLoginTokens(curToken));
     employee emp = empRepo.findByEncryptedId(invite.employeeId);
      if(emp == null){
          throw  new NoEmployeeFoundException(HttpStatus.NOT_FOUND, StringMessages.NO_EMPLOYEE_FOUND);
      }
      jobs invitedJob = jobRepo.findByEncryptedId(invite.jobId);

      if(invitedJob == null){
          throw new NoJobsFoundException( StringMessages.NO_JOB_FOUND,HttpStatus.NOT_FOUND);
      }
   List<jobInvitations> invites = invitationRepo.findByJobId(invitedJob);
      for (jobInvitations savedInvites : invites){
          if(savedInvites.employeeId.id == emp.id){
       // throws a new exception for already invite sent
              throw new InviteAlreadySentException(HttpStatus.IM_USED,StringMessages.INVITE_ALREADY_SENT);
          }



        }
        jobInvitations invitationsEntityObject = new jobInvitations();
        invitationsEntityObject.employeeId = emp;
        invitationsEntityObject.jobId = invitedJob;
        invitationRepo.save(invitationsEntityObject);
        String jobUrl = help.generateHosturl(req) + "/job/" + invitedJob.encryptedId;
        JobInviteEvent event = new JobInviteEvent(emp.userId.email, jobUrl);
        publisher.publishEvent(event);

        return ResponseEntity.ok(curToken.token);



    }

}
