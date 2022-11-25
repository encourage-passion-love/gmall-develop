package com.xfp.gmall.manager.mapper;

import com.xfp.gmall.manager.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductSaleAttrMapper {

   public void  savePmsSaleAttrs(List<PmsProductSaleAttr> pmsProductSaleAttrs);

    public List<PmsProductSaleAttr> spuSaleAttrs(@Param("spuId") String spuId);
}
