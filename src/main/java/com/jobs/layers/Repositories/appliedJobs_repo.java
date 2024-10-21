package com.jobs.layers.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobs.layers.Entities.appliedJobs;
import com.jobs.layers.Entities.jobs;
import java.util.List;

public interface appliedJobs_repo extends JpaRepository<appliedJobs,Integer> {
public List<appliedJobs> findByJobId(int currentAppliedJob);
}
