package com.jobs.layers.Repositories;
import com.jobs.layers.Entities.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface user_repo extends JpaRepository<user, Integer> {
    public user findByEmail(String email);
}
