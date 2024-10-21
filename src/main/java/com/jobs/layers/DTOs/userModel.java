package com.jobs.layers.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userModel {

    public String email;
    public String first_name;
    public String last_name;
    public String password;


}
