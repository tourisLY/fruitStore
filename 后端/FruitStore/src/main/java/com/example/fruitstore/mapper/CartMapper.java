// CartMapper.java
package com.example.fruitstore.mapper;

import com.example.fruitstore.entity.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    // 根据用户ID和商品ID查询购物车中的商品
    @Select("SELECT * FROM good_cart WHERE userId = #{userId} AND goodId = #{goodId}")
    Cart getCartByUserIdAndGoodId(int userId, int goodId);

    @Select("SELECT * FROM good_cart WHERE id = #{id} AND goodId = #{goodId}")
    Cart getCartByidAndUserId(int id, int userId);

    // 插入新的购物车项
    @Insert("INSERT INTO good_cart (userId, goodId, count, price) VALUES (#{userId}, #{goodId}, #{count}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCart(Cart cart);

    // 更新购物车中的商品数量和总金额
    @Update("UPDATE good_cart SET count = #{count}, price = #{price} WHERE userId = #{userId} AND goodId = #{goodId}")
    int updateCart(Cart cart);

    // 删除购物车中的某个商品
    @Delete("DELETE FROM good_cart WHERE userId = #{userId} AND goodId = #{goodId}")
    int deleteCartItem(int userId, int goodId);

    @Select("SELECT * FROM good_cart WHERE userId = #{userId}")
    List<Cart> getCartByUserId(int userId);

}
