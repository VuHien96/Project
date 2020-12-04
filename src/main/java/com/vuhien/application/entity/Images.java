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
@Table(name = "images")
public class Images implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @Column(name = "link")
    private String link;

    @Column(name = "createddate", updatable = false)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
