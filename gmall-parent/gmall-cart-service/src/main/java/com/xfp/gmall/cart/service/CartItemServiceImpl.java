package com.xfp.gmall.cart.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.cart.bean.OmsCartItem;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Override
    public OmsCartItem getOmsCartItemByMemberIdAndSkuId(String memberId, String skuId) {
        return null;
    }

    @Override
    public void saveCartItemToDB(OmsCartItem omsCartItem) {

    }

    @Override
    public void updateCartItemBySkuId(OmsCartItem omsCartFromDb) {

    }
}
