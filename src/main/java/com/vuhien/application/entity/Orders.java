package com.vuhien.application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Orders implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    @Id
    private int id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "username")
    private String userName;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "price")
    private double price;

    @Column (name = "created_date")
    private Date createdDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderProduct> listProductOrders = new ArrayList<>();

}
