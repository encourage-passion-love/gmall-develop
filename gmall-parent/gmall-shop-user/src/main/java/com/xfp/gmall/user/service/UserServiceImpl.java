package com.xfp.gmall.user.service;

import com.xfp.gmall.user.bean.UmsMember;
import com.xfp.gmall.user.bean.UmsMemberReceiveAddress;
import com.xfp.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.xfp.gmall.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMember> selectAllUser() {
        List<UmsMember> users = userMapper.selectAllUser();
        return users;
    }

    @Override
    public List<UmsMemberReceiveAddress> selectAllUserReceiverAddress(long member_id) {
        List<UmsMemberReceiveAddress> umsMemeberReceiveAddresses = umsMemberReceiveAddressMapper.selectAllUserReceiverAddress(member_id);
        return umsMemeberReceiveAddresses;
    }

    @Override
    public UmsMember login(UmsMember umsMember) {
        return null;
    }

    @Override
    public void addOAuthUser(UmsMember umsMember) {

    }
}
