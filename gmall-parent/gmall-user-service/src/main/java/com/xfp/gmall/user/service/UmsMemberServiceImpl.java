package com.xfp.gmall.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.user.bean.UmsMember;
import com.xfp.gmall.user.bean.UmsMemberReceiveAddress;
import com.xfp.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.xfp.gmall.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class UmsMemberServiceImpl implements UserService {

    @Autowired
    private UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UmsMember> selectAllUser() {
        List<UmsMember> umsMembers = userMapper.selectAllUser();
        return umsMembers;
    }

    @Override
    public List<UmsMemberReceiveAddress> selectAllUserReceiverAddress(long member_id) {
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.selectAllUserReceiverAddress(member_id);
        return umsMemberReceiveAddresses;
    }
}
