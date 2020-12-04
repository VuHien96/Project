package com.vuhien.application.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ImageVM {
    private int id;
    private String link;
    private Date createdDate;
    private List<ImageVM> imageVMList;
    private int productId;
}
