package com.xfp.gmall.item.service;

import com.xfp.gmall.item.bean.PmsProductImage;
import com.xfp.gmall.item.bean.PmsProductInfo;
import com.xfp.gmall.item.bean.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {


    public List<PmsProductInfo> spuList(String catalog3Id);
    public PmsProductInfo saveSpuInfo(PmsProductInfo pmsProductInfo);
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId);
    public List<PmsProductImage> spuImageList(String spuId);
}
