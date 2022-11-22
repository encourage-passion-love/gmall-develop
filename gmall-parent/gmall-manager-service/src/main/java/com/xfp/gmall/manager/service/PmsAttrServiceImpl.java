package com.xfp.gmall.manager.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.manager.bean.PmsBaseAttrInfo;
import com.xfp.gmall.manager.mapper.PmsBaseAttrInfoMapper;
import com.xfp.gmall.manager.mapper.PmsBaseAttrValueMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
}
