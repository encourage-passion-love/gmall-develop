package com.xfp.gmall.order.manager.mapper;

import com.xfp.gmall.manager.bean.PmsProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpuMapper {

    List<PmsProductInfo> spuList(@Param("catalog3Id") String catalog3Id);
}
