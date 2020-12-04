package com.vuhien.application.controller.client;

import com.vuhien.application.entity.Category;
import com.vuhien.application.entity.Product;
import com.vuhien.application.model.request.HomeVM;
import com.vuhien.application.model.request.ProductVM;
import com.vuhien.application.service.CategoryService;
import com.vuhien.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String product(Model model,
                          @Valid @ModelAttribute("productname") ProductVM productName,
                          @RequestParam(name = "categoryId", required = false) Integer categoryId,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "4") Integer size,
                          @RequestParam(name = "sortByPrice", required = false) String sort
                          ) {

        HomeVM vm = new HomeVM();

        Sort sortable =Sort.by(Sort.Direction.ASC,"id");
        if(sort != null) {
            if (sort.equals("ASC")) {
                sortable =  Sort.by(Sort.Direction.ASC,"price");
            }else {
                sortable = Sort.by(Sort.Direction.DESC,"price");
            }
        }

        Pageable pageable = PageRequest.of(page,size,sortable);

        Page<Product> productPage = null;

        if (categoryId != null) {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable, categoryId, null);
            Category category = categoryService.getOne(categoryId);
            vm.setKeyWord(category.getName());
        } else if (productName.getName() != null && !productName.getName().isEmpty()) {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable, null, productName.getName().trim());
            vm.setKeyWord("Kết quả tìm kiếm cho: " + productName.getName());
        } else {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable, null, null);
        }
        List<ProductVM> productVMList = new ArrayList<>();

        for (Product product : productPage.getContent()) {
            ProductVM productVM = new ProductVM();
            if (product.getCategory() == null) {
                productVM.setCategoryName("Unknown");
            } else {
                productVM.setCategoryName(product.getCategory().getName());
            }
            productVM.setId(product.getProductId());
            productVM.setName(product.getName());
            productVM.setImages(product.getImages());
            productVM.setPrice(product.getPrice());
            productVM.setDescription(product.getDescription());
            productVM.setCreatedDate(product.getCreateDate());

            productVMList.add(productVM);
        }

        vm.setProductVMList(productVMList);
        if (productVMList.size() == 0) {
            vm.setKeyWord("Không tìm thấy");
        }


        model.addAttribute("vm", vm);
        model.addAttribute("page", productPage);
        return "client/listProduct";
    }
}
