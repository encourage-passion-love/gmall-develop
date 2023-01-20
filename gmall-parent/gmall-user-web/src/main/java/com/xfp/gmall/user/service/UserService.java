package com.xfp.gmall.user.service;

import com.xfp.gmall.user.bean.UmsMember;
import com.xfp.gmall.user.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {
    List<UmsMember> selectAllUser();
    List<UmsMemberReceiveAddress> selectAllUserReceiverAddress(long member_id);
    UmsMember login(UmsMember umsMember);
}
