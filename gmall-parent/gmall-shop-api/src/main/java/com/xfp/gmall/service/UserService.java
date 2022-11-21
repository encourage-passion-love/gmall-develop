package com.xfp.gmall.service;

import com.xfp.gmall.bean.UmsMember;
import com.xfp.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {
    List<UmsMember> selectAllUser();

    List<UmsMemberReceiveAddress> selectAllUserReceiverAddress(long member_id);
}
