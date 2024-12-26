package com.example.fruitstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order_items")
public class Order_items {
    @TableId(value = "orderItemId", type = IdType.AUTO)
    private Integer orderItemId;
    private Integer orderId;
    private Integer goodId;
    private float goodPrice;
    private Integer goodNum;
    private float goodSumPrice;
    private String goodName;
    private String goodImage;
}
