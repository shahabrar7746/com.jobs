package com.jobs.layers.Controllers;

import com.jobs.layers.DTOs.EmployeeTransferTemplate;
import com.jobs.layers.DTOs.JobInvitation;
import com.jobs.layers.DTOs.JobPostTemplate;
import com.jobs.layers.Entities.companies;
import com.jobs.layers.Exceptions.*;
import com.jobs.layers.Services.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jobs.layers.DTOs.CompanyTransferTemplate;

import java.net.UnknownHostException;
import java.util.List;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @GetMapping("/company/{encryptedId}")
    public ResponseEntity<CompanyTransferTemplate> fetchCompanyById(@PathVariable("encryptedId") String id) throws InvalidEncryptedIdException {
        return ResponseEntity.ok(companyService.fetchByEncrypetdId(id));
    }
    @PostMapping("opening/jobs")
    public ResponseEntity<String> postJob(@RequestBody JobPostTemplate job) throws TOKEN_EXPIRED {
        return companyService.postJob(job);
    }
    @GetMapping("/{token}/employees")
    public ResponseEntity<List<EmployeeTransferTemplate>> findEmployees(@PathVariable("token") String token, @RequestParam("search") String query, final HttpServletRequest req) throws TOKEN_EXPIRED, UnknownHostException {
        return companyService.findAllEmployeeByQuery(token,query,req);
    }

    @PostMapping("/invite")
    public ResponseEntity<String> inviteApplicants(@RequestBody JobInvitation invite, final HttpServletRequest req) throws NoJobsFoundException, TOKEN_EXPIRED, NoEmployeeFoundException, InvalidEncryptedIdException, InviteAlreadySentException, UnknownHostException {
return companyService.invite(invite,req);
    }
}
