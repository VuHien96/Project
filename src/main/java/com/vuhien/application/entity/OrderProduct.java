package com.vuhien.application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_product")
public class OrderProduct implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_product_id")
    @Id
    private int id;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private double price;

}
