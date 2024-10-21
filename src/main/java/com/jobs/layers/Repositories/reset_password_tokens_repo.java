package com.jobs.layers.Repositories;

import com.jobs.layers.Entities.PasswordResetTokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface reset_password_tokens_repo extends JpaRepository<PasswordResetTokens, Long> {
    public PasswordResetTokens findByToken(String token);
}
