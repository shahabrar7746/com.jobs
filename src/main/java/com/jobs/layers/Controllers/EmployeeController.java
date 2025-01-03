package com.jobs.layers.Controllers;

import com.jobs.layers.DTOs.EmployeeTransferTemplate;
import com.jobs.layers.DTOs.JobsTransferTemplate;
import com.jobs.layers.Exceptions.InvalidEncryptedIdException;
import com.jobs.layers.Exceptions.NoJobsFoundException;
import com.jobs.layers.Exceptions.TOKEN_EXPIRED;
import com.jobs.layers.Responses.Response;
import com.jobs.layers.Services.JobsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jobs.layers.Services.EmployeService;

import java.net.UnknownHostException;
import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeService emplService;


    @Autowired
    private JobsService jobsServices;

    @GetMapping("/employee/{encryptedId}")
    public ResponseEntity<Response<EmployeeTransferTemplate>> getEmployee(@PathVariable("encryptedId") String id) throws InvalidEncryptedIdException {
        return ResponseEntity.ok(emplService.getEmployeeByEncryptedId(id));
    }
    @GetMapping("/employee/search/jobs/{token}")
    public ResponseEntity<Response<List<JobsTransferTemplate>>> findJobs(@RequestParam("query") String query, @PathVariable("token") String token, final  HttpServletRequest req) throws NoJobsFoundException, TOKEN_EXPIRED, UnknownHostException {

        return jobsServices.fetchJobs(query,token,req);
    }
    @GetMapping("/jobs/{token}/{encryptedId}")
    public ResponseEntity<Response<JobsTransferTemplate>> findJobByEncryptedId(@PathVariable("encryptedId") String encryptedId, @PathVariable("token") String token, final HttpServletRequest req) throws NoJobsFoundException, TOKEN_EXPIRED, UnknownHostException {
        return jobsServices.findJobByEncryptedId(encryptedId,token,req);
    }
    @PostMapping("/apply/jobs/{token}")
    public ResponseEntity<Response<JobsTransferTemplate>> applyForJob(@PathVariable("token") String token, @RequestParam("id") String encryptedJobId) throws NoJobsFoundException, TOKEN_EXPIRED {
        return jobsServices.apply(token, encryptedJobId);
    }

}
