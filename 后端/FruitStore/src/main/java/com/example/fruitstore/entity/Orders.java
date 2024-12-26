package com.example.fruitstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Orders {
    @TableId(value = "orderId", type = IdType.AUTO)
    private Integer orderId;
    private Integer userId;
    private LocalDateTime orderData;
    private float orderAllPrice;
    private float discountPrice;
    private String orderState;
    private String orderRemark;
    private LocalDateTime createData;
    private LocalDateTime finishData;
    private String shopName;
}
