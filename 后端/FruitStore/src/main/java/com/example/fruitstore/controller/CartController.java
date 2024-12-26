package com.example.fruitstore.controller;

import com.example.fruitstore.entity.Cart;
import com.example.fruitstore.mapper.CartMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartMapper cartMapper;

    // 添加商品到购物车
    @PostMapping("/add")
    public boolean addToCart(@RequestParam("userId") Integer userId,
                             @RequestParam("goodId") Integer goodId,
                             @RequestParam("quantity") Integer quantity,
                             @RequestParam("price") float price) {
        // 查询购物车是否已存在该商品
        Cart existingCart = cartMapper.getCartByUserIdAndGoodId(userId, goodId);
        if (existingCart != null) {
            existingCart.setCount(quantity);
            System.out.println("更新商品数量：" + existingCart.getCount());  // 调试日志
            cartMapper.updateCart(existingCart);
        } else {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setGoodId(goodId);
            newCart.setCount(quantity);
            newCart.setPrice(price);
            System.out.println("插入新商品：" + newCart);  // 调试日志
            cartMapper.insertCart(newCart);
        }
        return true;  // 返回成功
    }



    // 从购物车中移除商品
    @PostMapping("/delete")
    public Boolean removeFromCart(@RequestParam("userId") Integer userId, @RequestParam("goodId") Integer goodId) {
        cartMapper.deleteCartItem(userId, goodId);
        return true;
    }

    // 获取某用户购物车信息
    @GetMapping("/get")
    public List<Cart> getCart(@RequestParam("userId")Integer userId) {
        // 强制将 userId 设置为 1
//        Integer userId = 1;

        // 获取该 userId 的购物车数据
        List<Cart> cartList = cartMapper.getCartByUserId(userId);

        // 遍历每个 Cart，根据 id 和 userId 查找对应的 goodId
//        for (Cart cart : cartList) {
//            cart.setUserId(1);  // 强制设置 userId 为 1
//
//            // 根据主 id 和 userId 查找对应的 goodId
//            Cart cartWithGoodId = cartMapper.getCartByUserIdAndGoodId(userId, cart.getId());
//
//            // 如果找到对应的 cart，就把对应的 goodId 赋值给当前 cart
//            if (cartWithGoodId != null) {
//                cart.setGoodId(cartWithGoodId.getGoodId());  // 从查询结果中获取 goodId
//            }
//        }

        // 打印查看结果
        System.out.println("Cart items for userId " + userId + ": " + cartList);

        // 返回购物车数据
        return cartList;
    }




}
