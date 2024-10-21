package com.jobs.layers.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor
@NoArgsConstructor
public class CompanyReceptionTemplate {
    public String GSTIN;
    public String name;
    public String address;
}
