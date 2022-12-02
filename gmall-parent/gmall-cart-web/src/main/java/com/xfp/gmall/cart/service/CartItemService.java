package com.xfp.gmall.cart.service;

import com.xfp.gmall.cart.bean.OmsCartItem;

public interface CartItemService {
    public  OmsCartItem getOmsCartItemByMemberIdAndSkuId(String memberId, String skuId);
    public void saveCartItemToDB(OmsCartItem omsCartItem);
    public void updateCartItemBySkuId(OmsCartItem omsCartFromDb);
}
