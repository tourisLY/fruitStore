package com.example.fruitstore.mapper;

import com.example.fruitstore.entity.Order_items;
import com.example.fruitstore.entity.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("SELECT * FROM orders WHERE userId = #{userId}")
    List<Orders> getAllOrders(Integer userId);

    @Select("SELECT * FROM orders WHERE orderState = #{OrderState} AND userId = #{userId}")
    List<Orders> getOrderByState(Integer userId, String OrderState);

    @Select("SELECT * FROM order_items WHERE orderId = #{orderId}")
    List<Order_items> getAllOrderItems(Integer orderId);

    @Insert("INSERT INTO orders (userid, orderData, orderAllPrice, discountPrice, orderState, orderRemark)" +
            "VALUES (#{userId}, #{orderData}, #{orderAllPrice}, #{discountPrice}, #{orderState}, #{orderRemark})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    Boolean insertOrder(Orders order);

    @Insert("INSERT INTO order_items (orderId, goodId, goodPrice, goodNum, goodSumPrice,goodName, goodImage)" +
            "VALUES (#{orderId}, #{goodId}, #{goodPrice}, #{goodNum}, #{goodSumPrice}, #{goodName}, #{goodImage})")
    Boolean insertOrderitems(Order_items item);

}
