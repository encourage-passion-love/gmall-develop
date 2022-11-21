package com.xfp.gmall.user.mapper;



import com.xfp.gmall.user.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UmsMemberReceiveAddressMapper {
    List<UmsMemberReceiveAddress> selectAllUserReceiverAddress(long member_id);
}
