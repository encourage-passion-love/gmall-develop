package com.xfp.gmall.manager.mapper;

import com.xfp.gmall.manager.bean.PmsProductSaleAttrValue;

import java.util.List;
import java.util.Map;

public interface PmsProductSaleAttrValueMapper {

   public List<PmsProductSaleAttrValue> findSaleAttrValuesBySpuIdAndAttrId(Map map);
   public void  savePmsSaleAttrValues(List<PmsProductSaleAttrValue> pmsProductSaleAttrValues);
}
