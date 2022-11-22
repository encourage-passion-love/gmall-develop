package com.xfp.gmall.manager.service;

import com.xfp.gmall.manager.bean.PmsBaseAttrInfo;

import java.util.List;

public interface PmsAttrService {
    List<PmsBaseAttrInfo> attrInfoList( String catalog3Id);
    void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

}
