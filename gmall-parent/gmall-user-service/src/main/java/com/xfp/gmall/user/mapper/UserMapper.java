package com.xfp.gmall.user.mapper;

import com.xfp.gmall.user.bean.UmsMember;

import java.util.List;


public interface UserMapper  {

    List<UmsMember> selectAllUser();
    UmsMember selectByUsernameAndPassword(UmsMember umsMember);
    void insertOAuthUser(UmsMember umsMember);
}
