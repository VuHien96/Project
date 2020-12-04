package com.vuhien.application.controller.client;

import com.vuhien.application.entity.Category;
import com.vuhien.application.entity.Product;
import com.vuhien.application.model.dto.CategoryDTO;
import com.vuhien.application.model.dto.ProductDTO;
import com.vuhien.application.model.request.CategoryVM;
import com.vuhien.application.model.request.HomeVM;
import com.vuhien.application.model.request.ProductVM;
import com.vuhien.application.service.CategoryService;
import com.vuhien.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/client")
public class HomeController extends BaseController{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/home")
    public String home(Model model, @ModelAttribute("productname") ProductVM productName,
                       HttpServletResponse response,
                       HttpServletRequest request,
                       final Principal principal){

        this.checkCookie(response,request,principal);
        HomeVM vm = new HomeVM();

        List<CategoryDTO> categoryDTOS =categoryService.getListCategory();
        List<CategoryVM> categoryVMList = new ArrayList<>();
        for (CategoryDTO categoryDTO : categoryDTOS) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(categoryDTO.getCategoryId());
            categoryVM.setName(categoryDTO.getName());
            categoryVM.setDescription(categoryDTO.getDescription());
            categoryVM.setCreatedDate(categoryDTO.getCreatedDate());
            categoryVMList.add(categoryVM);
        }
        vm.setCategoryVMList(categoryVMList);

        List<ProductDTO> productDTOS = productService.getListProductNew();
        List<ProductVM> productVMList = new ArrayList<>();

        for (ProductDTO productDTO : productDTOS) {
            ProductVM productVM = new ProductVM();

            productVM.setId(productDTO.getProductId());
            productVM.setName(productDTO.getName());
            productVM.setImages(productDTO.getImages());
            productVM.setPrice(productDTO.getPrice());
            productVM.setDescription(productDTO.getDescription());
            productVM.setCreatedDate(productDTO.getCreatedDate());

            productVMList.add(productVM);
        }

        vm.setProductVMList(productVMList);

        model.addAttribute("vm", vm);

        return "client/index";
    }


}
