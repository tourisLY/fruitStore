package com.example.fruitstore.mapper;

import com.example.fruitstore.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    //账号密码登录功能
    @Select("SELECT * FROM user WHERE userAccount = #{userAccount} AND userPassword = #{userPassword}")
    List<User> loginUserByUserAccount(String userAccount, String userPassword);

    //手机号密码登录功能
    @Select("SELECT * FROM user WHERE userPhoneNumber = #{userPhoneNumber} AND userPassword = #{userPassword}")
    List<User> loginUserByUserPhoneNumber(String userPhoneNumber, String userPassword);

    @Select("SELECT * FROM user WHERE userAccount = #{userAccount} ")
    List<User> getUserByUserAccount(String userAccount);

    @Select("SELECT * FROM user WHERE userId = #{userId} ")
    List<User> getUserByUserId(String userId);
    //手机号密码登录功能
    @Select("SELECT * FROM user WHERE userPhoneNumber = #{userPhoneNumber} ")
    List<User> getUserByUserPhoneNumber(String userPhoneNumber);

    @Insert("INSERT INTO user (userName, userAccount, userPassword, userHead, userPhoneNumber, userSex, userBalance) " +
            "VALUES (#{userName}, #{userAccount}, #{userPassword}, #{userHead}, #{userPhoneNumber}, #{userSex}, #{userBalance})")
    boolean registerUser(User user);

    @Update("UPDATE user SET userName = #{userName} WHERE userId = #{userId}")
    boolean updateUserName(Integer userId, String userName);

    @Update("UPDATE user SET userPassword = #{userPassword} WHERE userId = #{userId}")
    boolean updateUserPassword(Integer userId, String userPassword);

    @Update("UPDATE user SET userHead = #{userHead} WHERE userId = #{userId}")
    boolean updateUserHead(Integer userId, String userHead);

    @Update("UPDATE user SET userSex = #{userSex} WHERE userId = #{userId}")
    boolean updateUserSex(Integer userId, String userSex);

    @Update("UPDATE user SET userBalance = #{userBalance} WHERE userId = #{userId}")
    boolean updateUserBalance(Integer userId, Float userBalance);
}


