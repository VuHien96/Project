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
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(name = "name")
    private String name;

    @Column(name = "images")
    private String images;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "create_date", updatable = false)
    private Date createDate;

    @Column(name = "status")
    private int status;

//    @Column(name = "category_id", insertable = false, updatable = false)
//    private int categoryId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Images> imagesList = new ArrayList<>();
}
