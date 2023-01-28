package com.xfp.gmall.cart.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.xfp.gmall.cart.bean.OmsCartItem;
import com.xfp.gmall.cart.mapper.CartItemMapper;
import com.xfp.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public OmsCartItem getOmsCartItemByMemberIdAndSkuId(String memberId, String skuId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        omsCartItem.setProductSkuId(skuId);
        OmsCartItem omsCartItemResult = cartItemMapper.selectOne(omsCartItem);
        return omsCartItemResult;
    }

    @Override
    public void saveCartItemToDB(OmsCartItem omsCartItem) {
        if(StringUtils.isNotBlank(omsCartItem.getProductSkuId())){
            cartItemMapper.insertSelective(omsCartItem);
        }
    }

    @Override
    public void updateCartItemBySkuId(OmsCartItem omsCartFromDb) {
        Example example=new Example(OmsCartItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productSkuId",omsCartFromDb.getProductSkuId());
        cartItemMapper.updateByExampleSelective(omsCartFromDb,example);
    }

    @Override
    public List<OmsCartItem> getCartItemByMemberId(String memberId) {
        List<OmsCartItem> omsCartItems=new ArrayList<>();
        Jedis jedis=null;
        String cartDisLock="cart:"+memberId+":lock";
        try {
            String token = UUID.randomUUID().toString();
            jedis=redisUtil.getJedis();
            List<String> cartStr = jedis.hvals("user:" + memberId + ":cart");
            if(cartStr!=null&&cartStr.size()>0){
                for (String s : cartStr) {
                    OmsCartItem omsCartItem = JSON.parseObject(s, OmsCartItem.class);
                    omsCartItems.add(omsCartItem);
                }
                return omsCartItems;
            }else {
                Map<String,String> map=new HashMap<>();
                String set = jedis.set(cartDisLock, token, "nx", "ex", 8 * 100);
                if(StringUtils.isNotBlank(set)&&"OK".equalsIgnoreCase(set)){
                    Example example=new Example(OmsCartItem.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("memberId",memberId);
                    omsCartItems=cartItemMapper.selectByExample(example);
                    if(omsCartItems!=null&&omsCartItems.size()>0){
                        for (OmsCartItem omsCartItem : omsCartItems) {
                            map.put(omsCartItem.getProductSkuId(),JSON.toJSONString(omsCartItem));
                        }
                        jedis.del("user:"+memberId+":cart");
                        jedis.hmset("user:"+memberId+":cart",map);
                    }else {
                        jedis.hmset("user:"+memberId+":cart",map);
                    }
                    String disToken = jedis.get(cartDisLock);
                    String script="if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                    jedis.eval(script, Collections.singletonList(cartDisLock),Collections.singletonList(disToken));
                }else {
                    return getCartItemByMemberId(memberId);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            jedis.close();
            return null;
        }finally {
            jedis.close();
        }
        return omsCartItems;
    }

    @Override
    public void checkCart(OmsCartItem omsCartItem) {
        Example example=new Example(OmsCartItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productSkuId",omsCartItem.getProductSkuId())
                .andEqualTo("memberId",omsCartItem.getMemberId());
        cartItemMapper.updateByExampleSelective(omsCartItem,example);
        OmsCartItem cartItem = cartItemMapper.selectOneByExample(example);
        if(cartItem!=null){
            Jedis jedis =null;
            try {
                 jedis=redisUtil.getJedis();
                 jedis.hdel("user:"+omsCartItem.getMemberId()+":cart",omsCartItem.getProductSkuId());
                 jedis.hset("user:"+omsCartItem.getMemberId()+":cart",omsCartItem.getProductSkuId(),JSON.toJSONString(cartItem));
            }catch (Exception e){
                e.printStackTrace();
                jedis.close();
            }finally {
                jedis.close();
            }
        }
    }

    @Override
    public void flushCartItemCache(List<OmsCartItem> cartItemByMemberId) {
        Jedis jedis =null;
       try {
           String memberId = cartItemByMemberId.get(0).getMemberId();
           Map<String,String> map=new HashMap<>();
           jedis=redisUtil.getJedis();
           for (OmsCartItem omsCartItem : cartItemByMemberId) {
            map.put(omsCartItem.getProductSkuId(),JSON.toJSONString(omsCartItem));
           }
           jedis.del("user:"+memberId+":cart");
           jedis.hmset("user:"+memberId+":cart",map);
       }catch (Exception e){
           e.printStackTrace();
           jedis.close();
       }finally {
           jedis.close();
       }
    }
}
