package com.jobs.layers.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobs.layers.Entities.tokens;
import org.springframework.stereotype.Repository;

@Repository
public interface token_repo extends JpaRepository<tokens, Integer> {
    public tokens findByToken(String token);

}
