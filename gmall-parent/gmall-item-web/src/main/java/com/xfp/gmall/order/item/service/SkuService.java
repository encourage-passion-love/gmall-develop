package com.xfp.gmall.order.item.service;

import com.xfp.gmall.manager.bean.PmsSkuInfo;

import java.util.List;

public interface SkuService {

    public PmsSkuInfo saveSkuInfo(PmsSkuInfo pmsSkuInfo);
    public PmsSkuInfo findSkuInfoById(String skuId)throws Exception;
    public List<PmsSkuInfo> getSkusBySpuId(String spuId);

}
