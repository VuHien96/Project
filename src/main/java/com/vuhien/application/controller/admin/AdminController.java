package com.vuhien.application.controller.admin;

import com.vuhien.application.entity.*;
import com.vuhien.application.model.dto.CategoryDTO;
import com.vuhien.application.model.request.*;
import com.vuhien.application.service.CategoryService;
import com.vuhien.application.service.ProductService;
import com.vuhien.application.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/home")
    public String home() {
        return "admin/home";
    }

    @GetMapping("/categories")
    public String category(Model model,
                           @Valid @ModelAttribute("categoryname") CategoryVM categoryName,
                           @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                           @RequestParam(name = "size", required = false, defaultValue = "4") Integer size) {

        CategoryVM vm = new CategoryVM();

        Pageable pageable;
        pageable = PageRequest.of(page, size);

        Page<Category> categoryPage = null;

        if (categoryName.getName() != null && !categoryName.getName().isEmpty()) {
            categoryPage = categoryService.getListCategoryByCategoryNameContaining(pageable, categoryName.getName().trim());
            vm.setKeyWord("Kết quả tìm kiếm cho: " + categoryName.getName());
        } else categoryPage = categoryService.getListCategoryByCategoryNameContaining(pageable, null);

        List<CategoryVM> categoryVMList = new ArrayList<>();
        for (Category category : categoryPage.getContent()) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getCategoryId());
            categoryVM.setName(category.getName());
            categoryVM.setDescription(category.getDescription());
            categoryVM.setCreatedDate(category.getCreatedDate());
            categoryVMList.add(categoryVM);
        }
        vm.setCategoryVMList(categoryVMList);


        model.addAttribute("vm", vm);
        model.addAttribute("page", categoryPage);

        return "/admin/category";

    }

    @GetMapping("/products")
    public String product(Model model,
                          @Valid @ModelAttribute("productname") ProductVM productName,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "8") Integer size) {

        ProductVM vm = new ProductVM();

        List<CategoryDTO> categoryList = categoryService.getListCategory();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for (CategoryDTO categoryDTO : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(categoryDTO.getCategoryId());
            categoryVM.setName(categoryDTO.getName());
            categoryVMList.add(categoryVM);
        }

        Pageable pageable;
        pageable = PageRequest.of(page, size);

        Page<Product> productPage = null;

        if (productName.getName() != null && !productName.getName().isEmpty()) {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable, null, productName.getName().trim());
            vm.setKeyWord("Kết quả tìm kiếm cho: " + productName.getName());
        } else {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable, null, null);
        }

        List<ProductVM> productVMList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("###,###.###");

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

        vm.setCategoryVMList(categoryVMList);
        vm.setProductVMList(productVMList);
        if (productVMList.size() == 0) {
            vm.setKeyWord("Không tìm thấy : ");
        }
        model.addAttribute("vm", vm);
        model.addAttribute("page", productPage);
        return "admin/product";
    }

    @GetMapping("/images/{productId}")
    public String image(Model model, @PathVariable int productId) {
        ImageVM vm = new ImageVM();

        Product product = productService.getOne(productId);

        List<ImageVM> imageVMS = new ArrayList<>();
        for (Images images : product.getImagesList()) {
            ImageVM imageVM = new ImageVM();
            imageVM.setId(images.getImageId());
            imageVM.setLink(images.getLink());
            imageVM.setCreatedDate(images.getCreatedDate());
            imageVMS.add(imageVM);
        }

        vm.setImageVMList(imageVMS);
        vm.setProductId(productId);

        model.addAttribute("vm", vm);

        return "admin/product-image";

    }

    @GetMapping("/order")
    public String getListOrder(Model model,
                               @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(name = "size", required = false, defaultValue = "8") Integer size) {
        OrderVM vm = new OrderVM();

        Pageable pageable = PageRequest.of(page, size);
        DecimalFormat df = new DecimalFormat("###,###.###");

        Page<Orders> ordersPage = orderService.getListOrder(pageable);

        List<OrderVM> orderVMS = new ArrayList<>();
        for (Orders orders : ordersPage.getContent()){
            OrderVM orderVM = new OrderVM();
            orderVM.setId(orders.getId());
            orderVM.setCustomerName(orders.getCustomerName());
            orderVM.setEmail(orders.getEmail());
            orderVM.setAddress(orders.getAddress());
            orderVM.setPhoneNumber(orders.getPhoneNumber());
            orderVM.setCreatedDate(orders.getCreatedDate());
            orderVM.setPrice(df.format(orders.getPrice()));
            orderVMS.add(orderVM);
        }
        vm.setOrderVMList(orderVMS);
        model.addAttribute("vm",vm);
        model.addAttribute("page", ordersPage);
        return "admin/order";
    }

    @GetMapping("/order-detail/{orderId}")
    public String getOrderDetail(Model model, @PathVariable("orderId") int orderId) {

        OrderDetailVM vm = new OrderDetailVM();

        DecimalFormat df = new DecimalFormat("###,###.###");

        double totalPrice = 0;

        List<OrderProductVM> orderProductVMS = new ArrayList<>();

        Orders orderEntity = orderService.findOne(orderId);

        if(orderEntity != null) {
            for(OrderProduct orderProduct : orderEntity.getListProductOrders()) {
                OrderProductVM orderProductVM = new OrderProductVM();

                orderProductVM.setProductId(orderProduct.getProduct().getProductId());
                orderProductVM.setMainImage(orderProduct.getProduct().getImages());
                orderProductVM.setAmount(orderProduct.getAmount());
                orderProductVM.setName(orderProduct.getProduct().getName());

                orderProductVM.setPrice(df.format(orderProduct.getPrice()));

                totalPrice += orderProduct.getPrice();

                orderProductVMS.add(orderProductVM);
            }
        }

        vm.setOrderProductVMS(orderProductVMS);
        vm.setTotalPrice(df.format(totalPrice));
        vm.setTotalProduct(orderProductVMS.size());

        model.addAttribute("vm",vm);

        return "admin/order-detail";
    }

    @GetMapping("/chart")
    public String chart(Model model) {

        ChartVM vm = new ChartVM();

        List<ChartDataVM> chartDataVMS = new ArrayList<>();

        List<Category> categories = categoryService.findAll();

        for(Category category : categories) {
            chartDataVMS.add(new ChartDataVM (category.getName(), (long) category.getProducts().size()));
        }

        vm.setChartDataVMS(chartDataVMS);

        model.addAttribute("vm", vm);

        return "/admin/chart";
    }

}
