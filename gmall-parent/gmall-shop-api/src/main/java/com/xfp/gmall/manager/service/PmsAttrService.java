package com.xfp.gmall.manager.service;

import com.xfp.gmall.manager.bean.PmsBaseAttrInfo;
import com.xfp.gmall.manager.bean.PmsBaseAttrValue;

import java.util.List;

public interface PmsAttrService {
    List<PmsBaseAttrInfo> attrInfoList( String catalog3Id);
    void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);
    public List<PmsBaseAttrValue> getAttrValueList(String attrId);
    public void deleteAttrValueByAttrId(String attrId);
}
