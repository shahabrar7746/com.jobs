package com.jobs.layers.Controllers;

import com.jobs.layers.DTOs.JobsTransferTemplate;
import com.jobs.layers.Exceptions.NoJobsFoundException;
import com.jobs.layers.Responses.Response;
import com.jobs.layers.Services.JobsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
public class JobController {

    @Autowired
    private JobsService service;

    @GetMapping("/job/{id}")
    public ResponseEntity<Response<JobsTransferTemplate>> findJobByEncryptedId(@PathVariable("id") String id, final HttpServletRequest req) throws NoJobsFoundException, UnknownHostException {
        return service.findJobByEncryptedId(id,req);
    }
}
