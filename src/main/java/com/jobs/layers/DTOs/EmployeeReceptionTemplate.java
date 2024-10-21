package com.jobs.layers.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReceptionTemplate {
    public String image_address;
    public String resume_address;
    public String certificate_address;
    public String skills;

}
