package com.jobs.layers.Repositories;

import com.jobs.layers.Entities.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobs.layers.Entities.companies;

@Repository
public interface companies_repo extends JpaRepository<companies,Integer> {
    public companies findByEncryptedId(String id);
    public companies findByUserObject(user userObj);
}
