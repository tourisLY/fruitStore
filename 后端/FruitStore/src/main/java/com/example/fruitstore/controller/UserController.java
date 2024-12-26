package com.example.fruitstore.controller;

import com.example.fruitstore.entity.User;
import com.example.fruitstore.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/loginByAccount")
    public boolean loginUserByAccount(@RequestParam("userAccount")String userAccount,
                                       @RequestParam("userPassword")String userPassword)
    {
        return !userMapper.loginUserByUserAccount(userAccount, userPassword).isEmpty();
    }

    @GetMapping("/loginByPhoneNumber")
    public boolean loginUserByPhoneNumber(@RequestParam("userPhoneNumber")String userPhoneNumber,
                                        @RequestParam("userPassword")String userPassword)
    {
        return !userMapper.loginUserByUserPhoneNumber(userPhoneNumber, userPassword).isEmpty();
    }

    @GetMapping("/getByAccount")
    public List<User> getUserByAccount(@RequestParam("userAccount")String userAccount)
    {
        return userMapper.getUserByUserAccount(userAccount);
    }

    @GetMapping("/getByUserId")
    public List<User> getUserByUserId(@RequestParam("userId")String userId)
    {
        return userMapper.getUserByUserId(userId);
    }

    @GetMapping("/getByPhoneNumber")
    public boolean getUserByPhoneNumber(@RequestParam("userPhoneNumber")String userPhoneNumber)
    {
        return !userMapper.getUserByUserPhoneNumber(userPhoneNumber).isEmpty();
    }


    @PostMapping("/registerUserByAccount")
    public boolean registerUserByAccount(@RequestParam("userAccount")String userAccount,
                                         @RequestParam("userPassword")String userPassword)
    {
        User user = new User();
        if(!userMapper.getUserByUserAccount(userAccount).isEmpty()){
            return false;
        }

        user.setUserAccount(userAccount);
        user.setUserPassword(userPassword);
        user.setUserName("未命名");
        user.setUserHead("none");
        user.setUserPhoneNumber("00000000000");
        user.setUserSex("男");
        user.setUserBalance(0);

        return userMapper.registerUser(user);
    }

    @PostMapping("/updateUserName")
    public boolean updateUserName(@RequestParam("userId")int userId,
                                  @RequestParam("userName")String userName)
    {
        return userMapper.updateUserName(userId, userName);
    }

    @PostMapping("/updateUserBalance")
    public boolean updateUserBalance(@RequestParam("userId") int userId,
                                     @RequestParam("userBalance") float userBalance)
    {
        return userMapper.updateUserBalance(userId, userBalance);
    }
}
