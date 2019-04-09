package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.ApplytransferMapper;
import com.hchenpan.pojo.Applytransfer;
import com.hchenpan.service.ApplytransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.ApplytransferServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/4/8 01:38 下午
 **/
@Service("applytransferService")
public class ApplytransferServiceImpl extends BaseServiceImpl<ApplytransferMapper, Applytransfer> implements ApplytransferService {
    private final ApplytransferMapper mapper;

    @Autowired
    public ApplytransferServiceImpl(ApplytransferMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String getCode() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String strDate = format.format(date);
        Page<Applytransfer> page = selectPage(new Page<>(1, 1, "applytransfercode", true), new EntityWrapper<Applytransfer>().eq("flag", "E").like("applytransfercode", strDate, SqlLike.DEFAULT).setSqlSelect("applytransfercode"));
        StringBuilder temp = new StringBuilder();
        if (page.getTotal() == 0) {
            temp.append(strDate).append("001");
        } else {
            String nid = page.getRecords().get(0).getApplytransfercode();
            StringBuilder sb = new StringBuilder();

            String temp1 = nid.substring(8, 9);
            String temp2 = nid.substring(9, 10);
            int k;
            if (!"0".equals(temp1)) {
                k = Integer.parseInt(nid.substring(8));
                k++;
                sb.append(k);
                temp.append(strDate).append(sb);
            } else if ("0".equals(temp2)) {
                k = Integer.parseInt(nid.substring(10));
                k++;
                sb.append(k);
                if (k == 10) {
                    temp.append(strDate).append("0").append(sb);
                } else {
                    temp.append(strDate).append("00").append(sb);
                }
            } else {
                k = Integer.parseInt(nid.substring(9));
                k++;
                sb.append(k);
                if (k == 100) {
                    temp.append(strDate).append(sb);
                } else {
                    temp.append(strDate).append("0").append(sb);
                }
            }
        }
        return temp.toString();
    }
}