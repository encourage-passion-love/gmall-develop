package com.xfp.gmall.manager.service;

import com.xfp.gmall.manager.bean.PmsProductInfo;
import com.xfp.gmall.manager.bean.PmsProductImage;
import com.xfp.gmall.manager.bean.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {


    public List<PmsProductInfo> spuList(String catalog3Id);
    public PmsProductInfo saveSpuInfo(PmsProductInfo pmsProductInfo);
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId);
    public List<PmsProductImage> spuImageList(String spuId);
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String spuId,String skuId);
}
