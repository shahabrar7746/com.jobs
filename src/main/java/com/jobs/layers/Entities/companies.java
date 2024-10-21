package com.jobs.layers.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class companies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

@NotNull
    public String name;
    @NonNull
    public String GSTIN;

 @NotNull
 @OneToOne(targetEntity = user.class)
@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ID_COMPANY"))
 public user userObject;

    @NotNull
    public int rating = 0;

    public String encryptedId;
    public String address;




}
