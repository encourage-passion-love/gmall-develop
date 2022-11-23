package com.xfp.gmall.manager.mapper;

import java.util.List;
import com.xfp.gmall.manager.bean.PmsBaseAttrValue;
import org.apache.ibatis.annotations.Param;

public interface PmsBaseAttrValueMapper {

    public void saveAttrValues(List<PmsBaseAttrValue> attrValueList);

    public List<PmsBaseAttrValue> getAttrValueList(@Param("attrId") String attrId);

}
