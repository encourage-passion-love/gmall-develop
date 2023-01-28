package com.xfp.gmall.manager.mapper;

import com.xfp.gmall.manager.bean.PmsProductImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductImageMapper {

    public void savePmsProductImages(List<PmsProductImage> spuProductImages);

    public List<PmsProductImage> spuImageList(@Param("spuId") String spuId);
}
