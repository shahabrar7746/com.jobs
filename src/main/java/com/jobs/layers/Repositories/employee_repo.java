package com.jobs.layers.Repositories;

import com.jobs.layers.Entities.employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface employee_repo extends JpaRepository<employee,Integer> {
    public employee findByEncryptedId(String id);


}
