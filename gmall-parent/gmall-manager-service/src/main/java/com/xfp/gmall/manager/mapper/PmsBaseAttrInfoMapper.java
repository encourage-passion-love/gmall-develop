package com.xfp.gmall.manager.mapper;

import com.xfp.gmall.manager.bean.PmsBaseAttrInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsBaseAttrInfoMapper {
    public List<PmsBaseAttrInfo> attrInfoList(@Param("catalog3Id") String catalog3Id);
}