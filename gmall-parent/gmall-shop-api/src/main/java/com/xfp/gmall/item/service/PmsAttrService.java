package com.xfp.gmall.item.service;

import com.xfp.gmall.item.bean.PmsBaseAttrInfo;
import com.xfp.gmall.item.bean.PmsBaseAttrValue;
import com.xfp.gmall.item.bean.PmsBaseSaleAttr;

import java.util.List;

public interface PmsAttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);
    void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);
    public List<PmsBaseAttrValue> getAttrValueList(String attrId);
    public void deleteAttrValueByAttrId(String attrId);
    public List<PmsBaseSaleAttr> baseSaleAttrList();
}
