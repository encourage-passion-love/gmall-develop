package com.xfp.gmall.manager.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.manager.bean.PmsBaseAttrInfo;
import com.xfp.gmall.manager.bean.PmsBaseAttrValue;
import com.xfp.gmall.manager.mapper.PmsBaseAttrInfoMapper;
import com.xfp.gmall.manager.mapper.PmsBaseAttrValueMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PmsAttrServiceImpl implements PmsAttrService{

    @Autowired
    private PmsBaseAttrInfoMapper attrInfoMapper;
    @Autowired
    private PmsBaseAttrValueMapper attrValueMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrInfoMapper.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }

    @Override
    public void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        List<PmsBaseAttrValue> saveAttrValues=new ArrayList<>();
        pmsBaseAttrInfo.setIsEnabled("1");
        attrInfoMapper.saveAttrInfo(pmsBaseAttrInfo);
        String attrInfoId=pmsBaseAttrInfo.getId();
        List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
        for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
            pmsBaseAttrValue.setAttrId(String.valueOf(attrInfoId));
            pmsBaseAttrValue.setIsEnabled("1");
            saveAttrValues.add(pmsBaseAttrValue);
        }
        attrValueMapper.saveAttrValues(saveAttrValues);
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        List<PmsBaseAttrValue> attrValueList = attrValueMapper.getAttrValueList(attrId);
        return attrValueList;
    }
}
