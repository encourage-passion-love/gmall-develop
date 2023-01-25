package com.xfp.gmall.order.manager.mapper;

import com.xfp.gmall.manager.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PmsProductSaleAttrMapper {

   public void  savePmsSaleAttrs(List<PmsProductSaleAttr> pmsProductSaleAttrs);

    public List<PmsProductSaleAttr> spuSaleAttrs(@Param("spuId") String spuId);

    public List<PmsProductSaleAttr> findPmsProductSaleAttrBySpuIdAndSkuId(Map map);
}
