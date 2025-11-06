package com.example.AmazonClone.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Column(name = "role")
    private String role;

    private String email;

    @Column(name = "create_dt")
    @JsonIgnore
    private Date createDt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Authority> authorities;


}
