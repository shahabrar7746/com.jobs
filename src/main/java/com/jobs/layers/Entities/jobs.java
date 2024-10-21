package com.jobs.layers.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class jobs {

    @Id
   @Column(unique = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public  int id;

@ManyToOne(targetEntity = companies.class)



    public companies company;

    public Date date;

    public String qualification;
@NotNull
    public String role;
@NotNull
    public String description;
@NotNull
    public int salary;

    public String contactPerson;

    public int contact_number;

    public String address;
    public String encryptedId;


}
