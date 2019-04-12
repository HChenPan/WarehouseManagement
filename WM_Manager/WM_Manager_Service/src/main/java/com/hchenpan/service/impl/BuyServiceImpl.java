package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.BuyMapper;
import com.hchenpan.pojo.Buy;
import com.hchenpan.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.BuyServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/4/11 12:32 下午
 **/
@Service("buyService")
public class BuyServiceImpl extends BaseServiceImpl<BuyMapper, Buy> implements BuyService {
    private final BuyMapper mapper;

    @Autowired
    public BuyServiceImpl(BuyMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String createbuycode(String buytype) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String _str_date = format.format(date);
        Page<Buy> page = selectPage(new Page<>(1, 1, "buycode", false), new EntityWrapper<Buy>().eq("flag", "E").like("buycode", buytype + _str_date, SqlLike.DEFAULT).setSqlSelect("buycode"));
        StringBuilder temp = new StringBuilder();
        if (page.getTotal() == 0) {
            temp.append("001");
        } else {
            String nid = page.getRecords().get(0).getBuycode();
            StringBuilder sb = new StringBuilder();
            String temp1 = nid.substring(8, 9);
            String temp2 = nid.substring(9, 10);
            int k;
            if (!"0".equals(temp1)) {
                k = Integer.parseInt(nid.substring(8));
                k++;
                sb.append(k);
                temp.append(_str_date).append(sb);
            } else if ("0".equals(temp2)) {
                k = Integer.parseInt(nid.substring(10));
                k++;
                sb.append(k);
                if (k == 10) {
                    temp.append(_str_date).append("0").append(sb);
                } else {
                    temp.append(_str_date).append("00").append(sb);
                }
            } else {
                k = Integer.parseInt(nid.substring(9));
                k++;
                sb.append(k);
                if (k == 100) {
                    temp.append(_str_date).append(sb);
                } else {
                    temp.append(_str_date).append("0").append(sb);
                }
            }
        }
        return temp.toString();
    }
}