package com.xfp.gmall.passport.service;

import com.xfp.gmall.user.bean.UmsMember;
import com.xfp.gmall.user.bean.UmsMemberReceiveAddress;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface UserService {
    List<UmsMember> selectAllUser();
    List<UmsMemberReceiveAddress> selectAllUserReceiverAddress(long member_id);
    UmsMember login(UmsMember umsMember);
    void addOAuthUser(UmsMember umsMember);
}
