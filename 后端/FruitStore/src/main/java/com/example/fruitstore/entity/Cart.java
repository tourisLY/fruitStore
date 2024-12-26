package com.example.fruitstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Setter;

@Data
@TableName("good_cart")
public class Cart {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    private int userId;

    private int goodId;


    private int count;


    private float price;

    // Getters and Setters
}