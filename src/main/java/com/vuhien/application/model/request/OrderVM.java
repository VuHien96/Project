package com.vuhien.application.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderVM {
    private int id;
    private String customerName;
    private String phoneNumber;
    private String address;
    private String email;
    private Date createdDate;
    private String price;
    private List<OrderVM> orderVMList;
}
