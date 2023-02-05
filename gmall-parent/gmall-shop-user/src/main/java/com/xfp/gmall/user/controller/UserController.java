package com.xfp.gmall.user.controller;


import com.xfp.gmall.user.bean.UmsMember;
import com.xfp.gmall.user.bean.UmsMemberReceiveAddress;
import com.xfp.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/all")
    @ResponseBody
    public List<UmsMember> findAllUsers() {
        List<UmsMember> users = userService.selectAllUser();
        return users;
    }

    @RequestMapping("/getUserReceiveAddress/{member_id}")
    @ResponseBody
    public List<UmsMemberReceiveAddress> findAllUsersReceiveAddress(@PathVariable("member_id") long member_id) {
        List<UmsMemberReceiveAddress> address = userService.
                selectAllUserReceiverAddress(member_id);
        return address;
    }

}
