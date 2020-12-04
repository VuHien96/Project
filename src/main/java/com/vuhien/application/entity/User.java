package com.vuhien.application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

//    @Column(name = "username", length = 45)
//    private String userName;

    @Column(name = "name",length = 45)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "password", length = 60)
    private String password;

    @Column(name = "created_date", updatable = false)
    private Date createdDate;

//    @ManyToMany
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private List<Role> roles = new ArrayList<>();

    @NotNull
    @Column(name = "role", columnDefinition = "varchar(255) default 'USER'")
    private String role;

//    @OneToMany(mappedBy = "user")
//    private List<Orders> orders = new ArrayList<>();
}
