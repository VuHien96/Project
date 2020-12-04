package com.vuhien.application.controller.client;

import com.vuhien.application.entity.Images;
import com.vuhien.application.entity.Product;
import com.vuhien.application.model.dto.ProductDTO;
import com.vuhien.application.model.request.ImageVM;
import com.vuhien.application.model.request.ProductDetailVM;
import com.vuhien.application.model.request.ProductVM;
import com.vuhien.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductDetailController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public String productDetail(@PathVariable Integer productId, Model model,
                                @Valid @ModelAttribute("productname") ProductVM productName){
        ProductDetailVM vm = new ProductDetailVM();
        Product product = productService.getOne(productId);
        ProductVM productVM = new ProductVM();
        if(product!=null) {
            productVM.setId(product.getProductId());
            productVM.setName(product.getName());
            productVM.setImages(product.getImages());
            productVM.setDescription(product.getDescription());
            productVM.setPrice(product.getPrice());

            /**
             * set list product image vm
             */
            List<ImageVM> imageVMS = new ArrayList<>();
            for(Images images : product.getImagesList()) {
                ImageVM imageVM = new ImageVM();
                imageVM.setLink(images.getLink());

                imageVMS.add(imageVM);
            }

            productVM.setImageVMS(imageVMS);
        }

        List<ProductDTO> productDTOS = productService.getListProductNew();
        List<ProductVM> productVMList = new ArrayList<>();

        for (ProductDTO productDTO : productDTOS) {
            ProductVM productVM1 = new ProductVM();

            productVM1.setId(productDTO.getProductId());
            productVM1.setName(productDTO.getName());
            productVM1.setImages(productDTO.getImages());
            productVM1.setPrice(productDTO.getPrice());
            productVM1.setDescription(productDTO.getDescription());
            productVM1.setCreatedDate(productDTO.getCreatedDate());

            productVMList.add(productVM1);
        }

        vm.setProductVMList(productVMList);

        vm.setProductVM(productVM);

        model.addAttribute("vm",vm);

        return "client/single-product";
    }
}
