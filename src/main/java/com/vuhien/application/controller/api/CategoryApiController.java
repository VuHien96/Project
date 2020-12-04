package com.vuhien.application.controller.api;

import com.vuhien.application.model.api.BaseApiResult;
import com.vuhien.application.model.dto.CategoryDTO;
import com.vuhien.application.service.CategoryService;
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
public class CategoryApiController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Get list category", response = CategoryDTO.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code=500,message = "")
    })
    @GetMapping("/categories")
    public ResponseEntity<?> getListCategory(){
        List<CategoryDTO> categoryDTOS = categoryService.getListCategory();
        return ResponseEntity.ok(categoryDTOS);
    }

    @ApiOperation(value = "Create category", response = CategoryDTO.class)
    @ApiResponses({
            @ApiResponse(code=400,message = "Category already exists in the system"),
            @ApiResponse(code=500,message = "")
    })
    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO  categoryDTO){
        BaseApiResult result = new BaseApiResult();
        try {
            categoryService.createCategory(categoryDTO);
            result.setData(categoryDTO.getName());
            result.setMessage("Thêm mới thành công: " + categoryDTO.getName());
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id){
        BaseApiResult result= new BaseApiResult();
        try {
            CategoryDTO categoryDTO = categoryService.getCategoryById(id);
            result.setSuccess(true);
            result.setData(categoryDTO);
            result.setMessage("Success");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO,@PathVariable int id){
        BaseApiResult result = new BaseApiResult();
        try {
            categoryService.updateCategory(categoryDTO,id);
            result.setSuccess(true);
            result.setMessage("Cập nhật thành công");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id){
        BaseApiResult result = new BaseApiResult();
        try {
            categoryService.deleteCategory(id);
            result.setMessage("Xóa thành công !");
            result.setSuccess(true);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return ResponseEntity.ok(result);
    }

}
