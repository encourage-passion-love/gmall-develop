package com.xfp.gmall.cart.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.cart.bean.OmsCartItem;
import com.xfp.gmall.cart.mapper.CartItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemMapper cartItemMapper;

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
}
