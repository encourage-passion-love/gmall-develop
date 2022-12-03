package com.xfp.gmall.cart.service;

import com.xfp.gmall.cart.bean.OmsCartItem;

import java.util.List;

public interface CartItemService {
    public  OmsCartItem getOmsCartItemByMemberIdAndSkuId(String memberId, String skuId);
    public void saveCartItemToDB(OmsCartItem omsCartItem);
    public void updateCartItemBySkuId(OmsCartItem omsCartFromDb);
    public List<OmsCartItem> getCartItemByMemberId(String memberId);
    public void checkCart(OmsCartItem omsCartItem);
    public void flushCartItemCache(List<OmsCartItem> cartItemByMemberId);
}
