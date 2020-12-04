package com.vuhien.application.controller.api;

import com.vuhien.application.model.api.BaseApiResult;
import com.vuhien.application.model.dto.ProductDTO;
import com.vuhien.application.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getListProduct() {
        List<ProductDTO> productDTOS = productService.getListProduct();
        return ResponseEntity.ok(productDTOS);
    }

    @ApiOperation(value = "Create product", response = ProductDTO.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Product already exists in the system"),
            @ApiResponse(code = 500, message = "")
    })
    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        BaseApiResult result = new BaseApiResult();
        try {
            productService.createProduct(productDTO);
            result.setData(productDTO.getName());
            result.setMessage("Thêm mới thành công: " + productDTO.getName());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable int id) {
        BaseApiResult result = new BaseApiResult();
        try {
            productService.updateProduct(productDTO, id);
            result.setSuccess(true);
            result.setMessage("Cập nhật thành công");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id){
        BaseApiResult result = new BaseApiResult();
        try {
            productService.deleteProduct(id);
            result.setMessage("Xóa thành công !");
            result.setSuccess(true);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id){
        BaseApiResult result= new BaseApiResult();
        try {
            ProductDTO productDTO = productService.getProductById(id);
            result.setSuccess(true);
            result.setData(productDTO);
            result.setMessage("Success");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

}
