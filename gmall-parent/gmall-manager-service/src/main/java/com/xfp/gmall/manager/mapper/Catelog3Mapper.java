package com.xfp.gmall.manager.mapper;

import com.xfp.gmall.manager.bean.PmsBaseCatalog3;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Catelog3Mapper {
    List<PmsBaseCatalog3> getCateLog3(@Param("catalog2_id") String catelog2_id);
}
