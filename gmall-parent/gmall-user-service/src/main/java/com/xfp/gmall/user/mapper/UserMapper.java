package com.xfp.gmall.user.mapper;

import com.xfp.gmall.user.bean.UmsMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


public interface UserMapper  {

    List<UmsMember> selectAllUser();
    UmsMember selectByUsernameAndPassword(UmsMember umsMember);
    void insertOAuthUser(UmsMember umsMember);
}
