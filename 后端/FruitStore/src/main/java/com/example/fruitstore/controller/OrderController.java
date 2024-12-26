package com.example.fruitstore.controller;

import com.example.fruitstore.entity.Order_items;
import com.example.fruitstore.entity.Orders;
import com.example.fruitstore.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    public List<Orders> getAllOrders(@RequestParam("userId")Integer userId)
    {
        return orderMapper.getAllOrders(userId);

    }

    @GetMapping("/byOrderState")
    public List<Orders> getOrdersByState(@RequestParam("orderState")String orderState,
                                         @RequestParam("userId")Integer userId){
        return orderMapper.getOrderByState(userId, orderState);
    }

    @GetMapping("/getAllOrderItems")
    public List<Order_items> getAllOrderItems(@RequestParam("orderId")Integer orderId){
        return orderMapper.getAllOrderItems(orderId);
    }

    @PostMapping("/insertOrder")
    public Integer insertOrder(@RequestParam("userId")Integer userId,
                               @RequestParam("orderAllPrice")float orderAllPrice,
                               @RequestParam("discountPrice")float discountPrice,
                               @RequestParam("orderState")String orderState,
                               @RequestParam("orderRemark")String orderRemark,
                               @RequestParam("shopName")String shopName)
    {
        Orders order = new Orders();
        order.setUserId(userId);
        order.setOrderData(LocalDateTime.now());
        order.setOrderAllPrice(orderAllPrice);
        order.setDiscountPrice(discountPrice);
        order.setOrderState(orderState);
        order.setOrderRemark(orderRemark);
        order.setShopName(shopName);
        if(orderMapper.insertOrder(order)){
            return order.getOrderId();
        }else{
            return -1;
        }
    }

    @PostMapping("/insertOrderItems")
    public boolean insertOrderItem(@RequestParam("orderId")Integer orderId,
                                   @RequestParam("goodId")Integer goodId,
                                   @RequestParam("goodPrice")float goodPrice,
                                   @RequestParam("goodNum")Integer goodNum,
                                   @RequestParam("goodSumPrice")float goodSumPrice,
                                   @RequestParam("goodName")String goodName,
                                   @RequestParam("goodImage")String goodImage)
    {
        Order_items item = new Order_items();
        item.setOrderId(orderId);
        item.setGoodId(goodId);
        item.setGoodNum(goodNum);
        item.setGoodPrice(goodPrice);
        item.setGoodSumPrice(goodSumPrice);
        item.setGoodName(goodName);
        item.setGoodImage(goodImage);
        return orderMapper.insertOrderitems(item);
    }
}
