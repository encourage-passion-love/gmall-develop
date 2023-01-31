package com.xfp.gmall.manager.mapper;

import com.xfp.gmall.gmall.manager.bean.PmsProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpuMapper {

    List<PmsProductInfo> spuList(@Param("catalog3Id") String catalog3Id);
}
