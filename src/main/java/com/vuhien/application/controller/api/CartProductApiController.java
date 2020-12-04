package com.vuhien.application.controller.api;

import com.vuhien.application.entity.Cart;
import com.vuhien.application.entity.CartProduct;
import com.vuhien.application.entity.Product;
import com.vuhien.application.model.api.BaseApiResult;
import com.vuhien.application.model.dto.CartProductDTO;
import com.vuhien.application.service.ProductService;
import com.vuhien.application.service.impl.CartProductService;
import com.vuhien.application.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartProductApiController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartProductService cartProductService;

    @PostMapping("/carts")
    public BaseApiResult addCart(@RequestBody CartProductDTO cartProductDTO) {
        BaseApiResult result = new BaseApiResult();
        try {
            if ( cartProductDTO.getAmount() > 0 & cartProductDTO.getProductId() > 0) {
                Cart cart = cartService.findByUserName(cartProductDTO.getUserName());
                Product product = productService.getOne(cartProductDTO.getProductId());
                if (cart != null & product != null) {
                    CartProduct cartProduct = cartProductService.findFirstCartProductByCartIdAndProductId(cart.getId(), product.getProductId());
                    if (cartProduct != null) {
                        cartProduct.setAmount(cartProduct.getAmount() + cartProductDTO.getAmount());
                        cartProductService.updateCartProduct(cartProduct);
                    } else {
                        CartProduct cartProduct2 = new CartProduct();
                        cartProduct2.setAmount(cartProductDTO.getAmount());
                        cartProduct2.setCart(cart);
                        cartProduct2.setProduct(product);
                        cartProductService.createCartProduct(cartProduct2);
                    }
                    result.setMessage("Add to cart successfully!");
                    result.setSuccess(true);
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setMessage("Bạn cần đăng nhập!");
        result.setSuccess(false);
        return result;
    }

    @PutMapping("/carts")
    public BaseApiResult updateCartProduct(@RequestBody CartProductDTO dto) {
        BaseApiResult result = new BaseApiResult();

        try {
            if(dto.getId()>0 && dto.getAmount() > 0) {
                CartProduct cartProductEntity = cartProductService.findOne(dto.getId());

                if(cartProductEntity != null) {
                    cartProductEntity.setAmount(dto.getAmount());
                    cartProductService.updateCartProduct(cartProductEntity);
                    result.setMessage("Update Cart Product success !");
                    result.setSuccess(true);
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setMessage("Fail!");
        result.setSuccess(false);
        return result;
    }

    @DeleteMapping("/carts/{id}")
    public BaseApiResult deleteCartProduct(@PathVariable int id) {
        BaseApiResult result = new BaseApiResult();

        try {
            if(cartProductService.deleteCartProduct(id) == true) {
                result.setMessage("Delete success");
                result.setSuccess(true);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setSuccess(false);
        result.setMessage("Fail!");
        return result;
    }
}
