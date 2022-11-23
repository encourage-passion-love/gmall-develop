package com.xfp.gmall.manager.mapper;

import com.xfp.gmall.manager.bean.PmsBaseAttrInfo;
import com.xfp.gmall.manager.bean.PmsBaseSaleAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsBaseAttrInfoMapper {
    public Long saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);
    public List<PmsBaseAttrInfo> attrInfoList(@Param("catalog3Id") String catalog3Id);
    public void updateAttrInfoById(PmsBaseAttrInfo pmsBaseAttrInfo);
    public List<PmsBaseSaleAttr> baseSaleAttrList();
}
