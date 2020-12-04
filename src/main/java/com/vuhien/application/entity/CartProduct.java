package com.vuhien.application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_product")
public class CartProduct {

    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column(name = "cart_product_id")
    @Id
    private int id;


    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "cart_id", insertable = false, updatable = false)
    private int cartId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_id", insertable = false, updatable = false)
    private int productId;

    @Column(name = "amount")
    private int amount;


}
