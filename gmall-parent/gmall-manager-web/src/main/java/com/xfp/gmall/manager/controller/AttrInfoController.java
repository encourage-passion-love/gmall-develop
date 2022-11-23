package com.xfp.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xfp.gmall.manager.bean.PmsBaseAttrValue;
import com.xfp.gmall.manager.service.PmsAttrService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.xfp.gmall.manager.bean.PmsBaseAttrInfo;
@RestController
public class AttrInfoController {

    @Reference
    private PmsAttrService pmsAttrService;

    @RequestMapping("/attrInfoList")
    public List<PmsBaseAttrInfo> attrInfoList(@RequestParam("catalog3Id") String catalog3Id){
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsAttrService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }

    @RequestMapping(value = "/saveAttrInfo",method = RequestMethod.POST)
    public PmsBaseAttrInfo saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        pmsAttrService.saveAttrInfo(pmsBaseAttrInfo);
        return pmsBaseAttrInfo;
    }

    @RequestMapping("/getAttrValueList")
    public List<PmsBaseAttrValue> getAttrValueList(@RequestParam("attrId")
                                                               String attrId){
        List<PmsBaseAttrValue> attrValueList = pmsAttrService.getAttrValueList(attrId);
        return attrValueList;
    }


}
