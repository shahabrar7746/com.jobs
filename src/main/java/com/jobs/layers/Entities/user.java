package com.jobs.layers.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class user {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    public int id;

    @NotNull
    public String first_name;
    @NotNull
    public String last_name;


    @Column(unique = true, nullable = false)
    public String email;

    @NotNull
    public String encrypted_password;


    @NotNull

    public Date createdAt;









    public String status = "UNAUTHENTICATED";

public String isRegistrationComplete = "NO";
    public String type = "employee";


}
