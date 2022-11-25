package com.xfp.gmall.manager.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.manager.bean.PmsSkuAttrValue;
import com.xfp.gmall.manager.bean.PmsSkuImage;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import com.xfp.gmall.manager.bean.PmsSkuSaleAttrValue;
import com.xfp.gmall.manager.mapper.PmsSkuAttrValueMapper;
import com.xfp.gmall.manager.mapper.PmsSkuImageMapper;
import com.xfp.gmall.manager.mapper.PmsSkuInfoMapper;
import com.xfp.gmall.manager.mapper.PmsSkuSaleAttrValueMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Override
    public PmsSkuInfo saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfoMapper.saveSkuInfo(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();
        List<PmsSkuAttrValue> pmsSkuAttrValues=new ArrayList<>();
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues=new ArrayList<>();
        List<PmsSkuImage> pmsSkuImages=new ArrayList<>();
        //添加平台属性
        List<PmsSkuAttrValue> pmsSkuAttrValueList = pmsSkuInfo.getPmsSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValues.add(pmsSkuAttrValue);
        }
        //添加销售属性
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = pmsSkuInfo.getPmsSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValues.add(pmsSkuSaleAttrValue);
        }
        //添加sku所有的image
        List<PmsSkuImage> pmsSkuImageList = pmsSkuInfo.getPmsSkuImageList();
        for (PmsSkuImage pmsSkuImage : pmsSkuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImages.add(pmsSkuImage);
        }
        //进行三张表操作添加数据就可以
        pmsSkuAttrValueMapper.saveSkuAttrValues(pmsSkuAttrValues);
        pmsSkuSaleAttrValueMapper.saveSkuSaleAttrValues(pmsSkuSaleAttrValues);
        pmsSkuImageMapper.saveSkuImages(pmsSkuImages);
        return pmsSkuInfo;
    }
}
