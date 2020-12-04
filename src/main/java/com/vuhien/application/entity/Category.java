package com.vuhien.application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "description", length = 45)
    private String description;

    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

}