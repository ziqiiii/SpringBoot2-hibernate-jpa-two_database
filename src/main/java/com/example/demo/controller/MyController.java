package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * create by ziqi.zhang on 2018/8/29
 */
@RestController
public class MyController {

    @Autowired
    IUserDao iUserDao;

    @RequestMapping("/hi")
    public User index(){
        User user = iUserDao.findByName("a");
        System.out.println(user);
        return  user;
    }
}
