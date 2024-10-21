package com.jobs.layers.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Data
public class employee {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @NotNull
    public String skills;


    @Column(nullable = true)
    public String certificate_addresses;

    @Column(nullable = true)
    public String photo_address;

      @Column(nullable = true)
    public String encryptedId;

    @Column(nullable = true)
    public String resume_address;

    @ManyToOne(targetEntity = user.class,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ID_EMPLOYEE"))
    public user userId;


}
