package com.jobs.layers.DTOs;

import lombok.Data;

@Data
public class NewPasswordBody {
    public String newPassword;
    public  String confirmPassword;
}
