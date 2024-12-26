package com.example.fruitstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Value;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@TableName("goods")
public class Goods {
    @TableId(value = "goodId", type = IdType.AUTO)
    private Integer goodId;
    private String goodName;
    private String goodMsg;
    private float goodPrice;
    private Integer GoodClassifyId;
    private String goodHeat;
    private String goodSugar;
    private String goodImage;
}
