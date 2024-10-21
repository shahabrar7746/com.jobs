package com.jobs.layers.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobs.layers.Entities.verificationTokens;
import org.springframework.stereotype.Repository;

@Repository
public interface verification_token_repo extends JpaRepository<verificationTokens,Long> {
    public verificationTokens findByToken(String token);
    public verificationTokens deleteByToken(String token);
}
