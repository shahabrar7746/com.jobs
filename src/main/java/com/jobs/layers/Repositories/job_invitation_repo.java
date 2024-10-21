package com.jobs.layers.Repositories;

import com.jobs.layers.Entities.jobInvitations;
import com.jobs.layers.Entities.jobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface job_invitation_repo extends JpaRepository<jobInvitations, Integer> {
    public List<jobInvitations> findByJobId(jobs curJob);
}
