package com.xfp.gmall.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.xfp.gmall.util.RedisUtil;
import com.xfp.gmall.user.bean.UmsMember;
import com.xfp.gmall.user.bean.UmsMemberReceiveAddress;
import com.xfp.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.xfp.gmall.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
@Service
public class UmsMemberServiceImpl implements UserService {

    @Autowired
    private UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

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

    @Override
    public UmsMember login(UmsMember umsMember) {
        Jedis jedis=null;
        try {
            jedis= redisUtil.getJedis();
            if(jedis!=null){
                String userinfo = jedis.get("user:" + umsMember.getUsername() + ":info");
                if(StringUtils.isNotBlank(userinfo)){
                    UmsMember umsMember1 = JSON.parseObject(userinfo, UmsMember.class);
                    return umsMember1;
                }
            }
            UmsMember umsMemberFromDB=loginFromDB(umsMember);
            if(umsMemberFromDB!=null){
                //将数据放到redis里面去
                jedis.setex("user:"+umsMember.getUsername()+":info",60*60*24,JSON.toJSONString(umsMemberFromDB));
            }
            return umsMemberFromDB;
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            jedis.close();
        }
        return null;
    }

    @Override
    public void addOAuthUser(UmsMember umsMember) {
        userMapper.insertOAuthUser(umsMember);
    }

    private UmsMember loginFromDB(UmsMember umsMember) {
        UmsMember selectByUsernameAndPassword = userMapper.selectByUsernameAndPassword(umsMember);
        return selectByUsernameAndPassword;
    }
}
