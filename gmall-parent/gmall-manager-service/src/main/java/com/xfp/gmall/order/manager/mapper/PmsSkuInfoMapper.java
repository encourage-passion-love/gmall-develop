package com.xfp.gmall.order.manager.mapper;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsSkuInfoMapper {

    public  void saveSkuInfo(PmsSkuInfo pmsSkuInfo);
    public PmsSkuInfo findSkuById(@Param("skuId") String skuId);
    public List<PmsSkuInfo> getSkusBySpuId(@Param("spuId") String spuId);
    public List<PmsSkuInfo> getAllSkuInfo();
}
