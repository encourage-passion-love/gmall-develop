package com.xfp.gmall.manager.mapper;

import com.xfp.gmall.manager.bean.PmsSkuImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsSkuImageMapper {
   public void saveSkuImages(List<PmsSkuImage> pmsSkuImages);
   public List<PmsSkuImage> findSkuImgsBySkuId(@Param("skuId") String skuId);

}
