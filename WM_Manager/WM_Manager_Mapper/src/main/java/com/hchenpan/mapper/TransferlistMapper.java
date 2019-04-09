package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Transferlist;
import org.springframework.stereotype.Component;

@Component
public interface TransferlistMapper extends BaseMapper<Transferlist> {

    void updateMXById(String id, String applytransfercode, String sbunit, String sbunitid);
}