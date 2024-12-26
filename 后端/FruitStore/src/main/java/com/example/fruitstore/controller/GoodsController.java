package com.example.fruitstore.controller;

import com.example.fruitstore.entity.Goods;
import com.example.fruitstore.mapper.GoodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    private  static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodMapper goodMapper;

    @GetMapping
    public List<Goods> getAllGoods(){               //查找所有实际商品
        List<Goods> goodsList = goodMapper.getAllGoods();
        logger.info("当前正在执行命令：查找所有实际商品。 准备开始输出商品信息！");
        for(Goods goods:goodsList){
            logger.info("找到了实际商品：{}", goods);
        }
        logger.info("当前正在执行命令：查找所有实际商品。输出结束！");

        return goodsList;
    }

    @GetMapping("/byClassifyId")
    public List<Goods> getGoodsByClassifyId(@RequestParam("ClassifyId")int ClassifyId){     //查找对应编号的所有商品
        List<Goods> goodsList = goodMapper.getGoodByClassifyId(ClassifyId);
        logger.info("当前正在执行命令：根据商品种类查找实际商品。 准备开始输出商品信息！");
        for(Goods goods:goodsList){
            logger.info("找到了实际商品：{}", goods);
        }
        logger.info("当前正在执行命令：根据商品种类查找实际商品。输出结束！");

        return goodsList;
    }

    @GetMapping("/byGoodId")
    public List<Goods> getGoodByGoodId(@RequestParam("goodId")int goodId){
        List<Goods> goodsList = goodMapper.getGoodByGoodId(goodId);
        return goodsList;
    }

    @GetMapping("/byGoodName")
    public List<Goods> getGoodByGoodName(@RequestParam("goodName")String goodName){
        List<Goods> goodsList = goodMapper.getGoodByGoodName(goodName);
        return goodsList;
    }
}
