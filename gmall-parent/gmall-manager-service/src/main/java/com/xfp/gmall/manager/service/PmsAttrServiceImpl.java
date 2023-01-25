package com.xfp.gmall.manager.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.order.manager.mapper.PmsBaseAttrInfoMapper;
import com.xfp.gmall.order.manager.mapper.PmsBaseAttrValueMapper;
import com.xfp.gmall.manager.bean.PmsBaseAttrInfo;
import com.xfp.gmall.manager.bean.PmsBaseAttrValue;
import com.xfp.gmall.manager.bean.PmsBaseSaleAttr;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class PmsAttrServiceImpl implements PmsAttrService {

    @Autowired
    private PmsBaseAttrInfoMapper attrInfoMapper;
    @Autowired
    private PmsBaseAttrValueMapper attrValueMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrInfoMapper.attrInfoList(catalog3Id);
        //里面有属性ID通过属性ID进行获取属性值
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos) {
            String pmsBaseAttrInfoId = pmsBaseAttrInfo.getId();
         List<PmsBaseAttrValue> attrValues=   attrValueMapper.findAttrValuesByAttrId(pmsBaseAttrInfoId);
            pmsBaseAttrInfo.setAttrValueList(attrValues);
        }
        return pmsBaseAttrInfos;
    }

    @Override
    public void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
       if(null!=pmsBaseAttrInfo.getId()&&!"".equals(pmsBaseAttrInfo.getId())){
           /*
           下面进行修改操作
           1.需要把属性进行更新
           2.先删除原有的属性值
           3.再把新添加的属性值进行保存
            */
           List<PmsBaseAttrValue> attrValueList=new ArrayList<>();
           attrInfoMapper.updateAttrInfoById(pmsBaseAttrInfo);
           attrValueMapper.deleteAttrValueByAttrId(pmsBaseAttrInfo.getId());
           //接着进行操作属性值的插入
           List<PmsBaseAttrValue> attrValueListEx = pmsBaseAttrInfo.getAttrValueList();
           for (PmsBaseAttrValue baseAttrValue : attrValueListEx) {
               baseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
               baseAttrValue.setIsEnabled("1");
               attrValueList.add(baseAttrValue);
           }
           attrValueMapper.saveAttrValues(attrValueList);
       }else {
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
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        List<PmsBaseAttrValue> attrValueList = attrValueMapper.getAttrValueList(attrId);
        return attrValueList;
    }

    @Override
    public void deleteAttrValueByAttrId(String attrId) {
        attrValueMapper.deleteAttrValueByAttrId(attrId);
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = attrInfoMapper.baseSaleAttrList();
        return pmsBaseSaleAttrs;
    }
}
