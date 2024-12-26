package com.example.fruitstore.mapper;

import com.example.fruitstore.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodMapper {
    @Select("SELECT * FROM goods WHERE goodClassifyId = #{ClassifyId}")
    List<Goods> getGoodByClassifyId(int ClassifyId);

    @Select("SELECT * FROM goods")
    List<Goods> getAllGoods();

    @Select("SELECT * FROM goods WHERE goodId = #{goodId}")
    List<Goods> getGoodByGoodId(int goodId);

    @Select("SELECT * FROM goods WHERE goodName = #{goodId}")
    List<Goods> getGoodByGoodName(String goodId);
}
