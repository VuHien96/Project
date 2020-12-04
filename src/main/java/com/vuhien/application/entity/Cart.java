package com.vuhien.application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    @Id
    private int id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "username")
    private String userName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private List<CartProduct> listCartProducts = new ArrayList<>();
}
