package com.xfp.gmall.order.search.service;

import com.xfp.gmall.manager.bean.PmsBaseAttrInfo;
import com.xfp.gmall.manager.bean.PmsSkuInfo;

import java.util.List;
import java.util.Set;

public interface SkuService {

    public PmsSkuInfo saveSkuInfo(PmsSkuInfo pmsSkuInfo);
    public PmsSkuInfo findSkuInfoById(String skuId)throws Exception;
    public List<PmsSkuInfo> getSkusBySpuId(String spuId);
    public List<PmsSkuInfo> getAllSkuInfo();
    public List<PmsBaseAttrInfo> getPmsAttrListBySkuValueId(Set<String> valueIds);

}
