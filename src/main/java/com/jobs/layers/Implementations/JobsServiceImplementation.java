package com.jobs.layers.Implementations;
import com.jobs.helper;
import com.jobs.layers.DTOs.CompanyTransferTemplate;
import com.jobs.layers.DTOs.JobsTransferTemplate;
import com.jobs.layers.Entities.*;
import com.jobs.layers.Exceptions.TOKEN_EXPIRED;
import com.jobs.layers.Messages.StringMessages;
import com.jobs.layers.Repositories.token_repo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import com.jobs.layers.Exceptions.NoJobsFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.jobs.layers.Repositories.employee_repo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.jobs.layers.Repositories.appliedJobs_repo;
import com.jobs.layers.Services.JobsService;

@Service
public class JobsServiceImplementation implements JobsService{


    @Autowired
    private token_repo loginTokenRepo;
    @Autowired
    com.jobs.layers.Repositories.jobs_repo jobsRepo;

    @Autowired
    com.jobs.layers.Repositories.companies_repo companiesRepo;

private Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private appliedJobs_repo appliedJobsRepo;


@Autowired
private helper help;


@Autowired
private employee_repo EmployeeRepo;












    public ResponseEntity<List<JobsTransferTemplate>> fetchJobs(String query, String token,final HttpServletRequest req) throws NoJobsFoundException, TOKEN_EXPIRED, UnknownHostException{
        tokens loginToken = loginTokenRepo.findByToken(token);

        if(loginToken == null || help.isTokenExpired(loginToken)){
            loginTokenRepo.delete(loginToken);
            throw  new TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, StringMessages.TOKEN_EXPIRED);
        }
        List<jobs> fetchedJobs = jobsRepo.findAll();
        logger.info("Searching jobs for query {}", query);
        List<companies> fetchedCompanies = companiesRepo.findAll();
        List <companies> comList  = new ArrayList();
        //finds jobs by company name.
        for(int i =0;i<fetchedCompanies.size();i++){
            companies com = fetchedCompanies.get(i);
            if(com.name.contains(query)){
                comList.add(com);
            }
        }

        List<JobsTransferTemplate> queryResolutionList = new ArrayList<>();
        for(int i =0;i<comList.size();i++){

            List<jobs> jobList =  jobsRepo.findByCompanyId(comList.get(i).id);
            for(jobs foundJob : jobList) {
                if (foundJob != null) {
                    JobsTransferTemplate jobTemplate = new JobsTransferTemplate(foundJob,token);
                    companies com = foundJob.company;
                    CompanyTransferTemplate companyTemplate = new CompanyTransferTemplate(foundJob.company,token);
                    jobTemplate.requestId = token;
                    queryResolutionList.add(jobTemplate);
                }
            }


        }
        //finds job by provided query or designation.
       for(int i =0;i<fetchedJobs.size();i++) {
    	   if(fetchedJobs.get(i).role.contains(query)) {
               jobs foundJob = fetchedJobs.get(i);
               JobsTransferTemplate jobTemplate = new JobsTransferTemplate(foundJob,token);
               companies com = foundJob.company;

               CompanyTransferTemplate companyTemplate = new CompanyTransferTemplate(com,token);

                   jobTemplate.requestId = token;
               jobTemplate.companyUrl = help.generateHosturl(req) + "/company/" + foundJob.company.encryptedId;

               queryResolutionList.add(jobTemplate);
    	   }
       }
        if(queryResolutionList.size() == 0){
            logger.info("No jobs found for Query {}",query);
            throw new NoJobsFoundException(StringMessages.NO_JOB_FOUND, HttpStatus.NOT_FOUND);
        }
        loginTokenRepo.save(help.increaseExpirationTimeOfLoginTokens(loginToken));
        return ResponseEntity.ok(queryResolutionList);

    }

    @Override
    public void save(jobs curJobPost) {
        jobsRepo.save(curJobPost);
    }

    @Override
    public ResponseEntity<JobsTransferTemplate> findJobByEncryptedId(String encryptedId, String token,final HttpServletRequest req) throws TOKEN_EXPIRED, NoJobsFoundException, UnknownHostException {
        tokens curToken = loginTokenRepo.findByToken(token);

        if(curToken == null || help.isTokenExpired(curToken)){
            loginTokenRepo.delete(curToken);
            throw  new TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, StringMessages.TOKEN_EXPIRED);

        }



        jobs curJob = jobsRepo.findByEncryptedId(encryptedId);
        if(curJob == null){
            throw new NoJobsFoundException(StringMessages.NO_JOB_FOUND, HttpStatus.NOT_FOUND);
        }
        List<appliedJobs> curAppliedJob = appliedJobsRepo.findByJobId(curJob.id);
        boolean isApplied = true;
        List<appliedJobs> currentAppliedJobs = appliedJobsRepo.findByJobId(curJob.id);
        user empployeUserObject = curToken.userObj;
        employee curLoginEmplpoyee = null;
        for(employee curEmployee : EmployeeRepo.findAll()){
            if(curEmployee.userId.id == empployeUserObject.id){
                curLoginEmplpoyee = curEmployee;
                break;
            }
        }

        for(appliedJobs currentAppliedJob : currentAppliedJobs){
            if(currentAppliedJob.id == curJob.id && currentAppliedJob.employee.id == curLoginEmplpoyee.id){
                isApplied = true;
                break;
            }
        }
        curToken = help.increaseExpirationTimeOfLoginTokens(curToken);
        loginTokenRepo.save(curToken);
        JobsTransferTemplate template = new JobsTransferTemplate(curJob,token,isApplied);
        template.requestId = token;
        template.companyUrl = help.generateHosturl(req) + "/company/" + curJob.company.encryptedId;

        return ResponseEntity.ok(template);
    }


    @Override
    public ResponseEntity<JobsTransferTemplate> findJobByEncryptedId(String id, final HttpServletRequest req) throws NoJobsFoundException, UnknownHostException {
        jobs curJob = jobsRepo.findByEncryptedId(id);
        if(curJob == null){
            throw new NoJobsFoundException(StringMessages.NO_JOB_FOUND,HttpStatus.NOT_FOUND);
        }
        JobsTransferTemplate template = new JobsTransferTemplate(curJob);
        template.companyUrl = help.generateHosturl(req) + "/company/" + curJob.company.encryptedId;
        return ResponseEntity.ok(template);
    }

    @Override
    public ResponseEntity<JobsTransferTemplate> apply(String token, String encryptedJobId) throws TOKEN_EXPIRED, NoJobsFoundException {
        tokens currentLoginToken = loginTokenRepo.findByToken(token);
        if(currentLoginToken == null || help.isTokenExpired(currentLoginToken)){
            throw new TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, StringMessages.TOKEN_EXPIRED);
        }
        jobs currentJob = jobsRepo.findByEncryptedId(encryptedJobId);
        if(currentJob == null){
            throw new NoJobsFoundException(StringMessages.NO_JOB_FOUND, HttpStatus.NOT_FOUND);
        }
        boolean isApplied = false;
        List<appliedJobs> currentAppliedJobs = appliedJobsRepo.findByJobId(currentJob.id);
        user empployeUserObject = currentLoginToken.userObj;


        employee curLoginEmplpoyee = null;

        for(employee curEmployee : EmployeeRepo.findAll()){

            if(curEmployee.userId.id == empployeUserObject.id){
                curLoginEmplpoyee = curEmployee;
                break;
            }
        }
        if(curLoginEmplpoyee == null)
        {
            throw new TOKEN_EXPIRED(HttpStatus.BAD_REQUEST,StringMessages.TOKEN_EXPIRED);
        }
        for(appliedJobs currentAppliedJob : currentAppliedJobs){
            if(currentAppliedJob.id == currentJob.id && currentAppliedJob.employee.id == curLoginEmplpoyee.id){
                isApplied = true;
                break;
            }
        }
        //increase expiration time of login token.
        currentLoginToken = help.increaseExpirationTimeOfLoginTokens(currentLoginToken);
        loginTokenRepo.save(currentLoginToken);
        //saves and applies for job.
        appliedJobs job = new appliedJobs();
        job.employee = curLoginEmplpoyee;
        job.job = currentJob;
        job.status = "SUBMITTED";
        job.applied_date = new Date();
try {
    appliedJobsRepo.save(job);
}catch (Exception e){
    isApplied = true;
}
        //prepare jobTransfer template.
        JobsTransferTemplate template = new JobsTransferTemplate(currentJob, token,isApplied);
        return ResponseEntity.ok(template);
    }


}
