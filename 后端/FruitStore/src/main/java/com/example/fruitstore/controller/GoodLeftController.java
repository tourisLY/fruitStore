package com.example.fruitstore.controller;

import com.example.fruitstore.entity.GoodLeft;
import com.example.fruitstore.mapper.GoodLeftMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goodLeft")
public class GoodLeftController {
    private static final Logger logger = LoggerFactory.getLogger(GoodLeftController.class);

    @Autowired
    private GoodLeftMapper goodLeftMapper;

    @GetMapping
    public List<GoodLeft> getAllGoodLeft(){         //查找所有商品种类
        List<GoodLeft> goodLefts =goodLeftMapper.findAll();
        for(GoodLeft goodLeft:goodLefts){
            logger.info("找到产品种类列表:{}", goodLeft);
        }

        return goodLefts;
    }
}
