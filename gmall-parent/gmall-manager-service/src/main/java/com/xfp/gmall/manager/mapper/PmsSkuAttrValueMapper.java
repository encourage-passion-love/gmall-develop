package com.xfp.gmall.manager.mapper;

import com.xfp.gmall.manager.bean.PmsSkuAttrValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsSkuAttrValueMapper {
    public void saveSkuAttrValues(List<PmsSkuAttrValue> pmsSkuAttrValues);
    public List<PmsSkuAttrValue> findAttrvaluesBySkuId(@Param("skuId") String skuId);
}
