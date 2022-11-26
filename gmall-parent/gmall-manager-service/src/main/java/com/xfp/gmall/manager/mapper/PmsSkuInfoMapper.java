package com.xfp.gmall.manager.mapper;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import org.apache.ibatis.annotations.Param;

public interface PmsSkuInfoMapper {

    public  void saveSkuInfo(PmsSkuInfo pmsSkuInfo);
    public PmsSkuInfo findSkuById(@Param("skuId") String skuId);

}
