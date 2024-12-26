package com.example.fruitstore.mapper;

import com.example.fruitstore.entity.GoodLeft;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodLeftMapper {

    @Select("SELECT * FROM GoodLeft")
    List<GoodLeft> findAll();
}
