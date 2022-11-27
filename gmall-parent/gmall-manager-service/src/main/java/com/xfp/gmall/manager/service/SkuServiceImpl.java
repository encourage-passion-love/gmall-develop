package com.xfp.gmall.manager.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.xfp.gmall.manager.bean.PmsSkuAttrValue;
import com.xfp.gmall.manager.bean.PmsSkuImage;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import com.xfp.gmall.manager.bean.PmsSkuSaleAttrValue;
import com.xfp.gmall.manager.mapper.PmsSkuAttrValueMapper;
import com.xfp.gmall.manager.mapper.PmsSkuImageMapper;
import com.xfp.gmall.manager.mapper.PmsSkuInfoMapper;
import com.xfp.gmall.manager.mapper.PmsSkuSaleAttrValueMapper;
import com.xfp.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

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
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public PmsSkuInfo saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfoMapper.saveSkuInfo(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();
        List<PmsSkuAttrValue> pmsSkuAttrValues=new ArrayList<>();
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues=new ArrayList<>();
        List<PmsSkuImage> pmsSkuImages=new ArrayList<>();
        //添加平台属性
        List<PmsSkuAttrValue> pmsSkuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValues.add(pmsSkuAttrValue);
        }
        //添加销售属性
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValues.add(pmsSkuSaleAttrValue);
        }
        //添加sku所有的image
        List<PmsSkuImage> pmsSkuImageList = pmsSkuInfo.getSkuImageList();
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

    public PmsSkuInfo getSkuInfoFromDB(String skuId){
        PmsSkuInfo skuById = pmsSkuInfoMapper.findSkuById(skuId);
        //获取sku的图片列表
        List<PmsSkuImage> skuImgsBySkuId = pmsSkuImageMapper.findSkuImgsBySkuId(skuId);
        //获取sku的平台属性值
        List<PmsSkuAttrValue> attrvaluesBySkuId = pmsSkuAttrValueMapper.findAttrvaluesBySkuId(skuId);
        //获取sku的销售属性值
        List<PmsSkuSaleAttrValue> saleAttrvalueBySkuId = pmsSkuSaleAttrValueMapper.findSaleAttrvalueBySkuId(skuId);
        skuById.setSkuImageList(skuImgsBySkuId);
        skuById.setSkuAttrValueList(attrvaluesBySkuId);
        skuById.setSkuSaleAttrValueList(saleAttrvalueBySkuId);
        return skuById;
    }

    @Override
    public PmsSkuInfo findSkuInfoById(String skuId)throws Exception {
        //连接redis
        PmsSkuInfo pmsSkuInfo=null;
        Jedis jedis = null;
        String disLock="sku:"+skuId+":lock";
        try {
            pmsSkuInfo=new PmsSkuInfo();
            jedis=redisUtil.getJedis();
            String skuStr = jedis.get("sku:" + skuId + ":info");
            if(StringUtils.isNotBlank(skuStr)){
                //出出来数据了
                pmsSkuInfo = JSON.parseObject(skuStr,PmsSkuInfo.class);
            }else {
                String set = jedis.set(disLock, "1", "nx", "ex", 10);
                if(StringUtils.isNotBlank(set)&&"OK".equals(set)){
                    pmsSkuInfo = getSkuInfoFromDB(skuId);
                    if(pmsSkuInfo!=null){
                        jedis.set("sku:" + skuId + ":info",JSON.toJSONString(pmsSkuInfo));
                    }else {
                        //防止缓存穿透给数据库造成压力 所以在2分钟以内
                        //读取的都是缓存中的空字符串
                        jedis.setex("sku:" + skuId + ":info",60*2,JSON.toJSONString(""));
                    }
                }
                else {
                    Thread.sleep(3000);
                    return findSkuInfoById(skuId);
                }
            }
        }catch (Exception e){
            jedis.del(disLock);
            e.printStackTrace();
        }finally {
            jedis.del(disLock);
            jedis.close();
        }
       return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkusBySpuId(String spuId) {
        List<PmsSkuInfo> skus=pmsSkuInfoMapper.getSkusBySpuId(spuId);
        return skus;
    }
}
