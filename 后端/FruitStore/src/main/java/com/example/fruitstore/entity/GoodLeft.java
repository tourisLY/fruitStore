package com.example.fruitstore.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("GoodLeft")
public class GoodLeft {
    @TableId(value = "GoodClassifyId", type = IdType.AUTO)
    private Integer GoodClassifyId;
    private String GoodClassify;
}
