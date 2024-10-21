package com.jobs.layers.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobInvitation {
    public String jobId;
    public String employeeId;
    public String token;
}
