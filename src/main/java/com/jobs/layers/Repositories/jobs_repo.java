package com.jobs.layers.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jobs.layers.Entities.jobs;
import
        java.util.List;
@Repository
public interface jobs_repo extends JpaRepository<jobs,Integer> {


    public List<jobs> findByCompanyId(int id);

    jobs findByEncryptedId(String encryptedId);
}
