package com.xfp.gmall.order.manager.mapper;
import com.xfp.gmall.manager.bean.PmsSkuSaleAttrValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsSkuSaleAttrValueMapper {

   public void  saveSkuSaleAttrValues(List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues);
   public List<PmsSkuSaleAttrValue> findSaleAttrvalueBySkuId(@Param("skuId") String skuId);
}
