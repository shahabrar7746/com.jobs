package com.jobs.layers.Services;

import com.jobs.layers.DTOs.JobsTransferTemplate;
import com.jobs.layers.Exceptions.NoJobsFoundException;
import com.jobs.layers.Entities.jobs;
import com.jobs.layers.Exceptions.TOKEN_EXPIRED;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.net.UnknownHostException;
import java.util.List;

public interface JobsService
{
    public ResponseEntity<List<JobsTransferTemplate>> fetchJobs(String query,String token,final HttpServletRequest req) throws NoJobsFoundException, TOKEN_EXPIRED, UnknownHostException;

    void save(jobs curJobPost);

public     ResponseEntity<JobsTransferTemplate> findJobByEncryptedId(String encryptedId, String token, final HttpServletRequest req) throws TOKEN_EXPIRED, NoJobsFoundException, UnknownHostException;


public ResponseEntity<JobsTransferTemplate> findJobByEncryptedId(String id, final HttpServletRequest req) throws  NoJobsFoundException, UnknownHostException;
    ResponseEntity<JobsTransferTemplate> apply(String token, String encryptedJobId) throws TOKEN_EXPIRED, NoJobsFoundException;
}
