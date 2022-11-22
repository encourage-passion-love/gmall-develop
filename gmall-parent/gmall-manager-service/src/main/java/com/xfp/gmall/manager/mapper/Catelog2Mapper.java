package com.xfp.gmall.manager.mapper;

import com.xfp.gmall.manager.bean.PmsBaseCatalog1;
import com.xfp.gmall.manager.bean.PmsBaseCatalog2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Catelog2Mapper {
    List<PmsBaseCatalog2> getCateLog2(@Param("catalog1_id") String  catelog1_id);
}
