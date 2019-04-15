package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.CancellingstocksSQMapper;
import com.hchenpan.pojo.CancellingstocksSQ;
import com.hchenpan.service.CancellingstocksSQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.CancellingstocksSQServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("cancellingstocksSQService")
public class CancellingstocksSQServiceImpl extends BaseServiceImpl<CancellingstocksSQMapper, CancellingstocksSQ> implements CancellingstocksSQService {
    private final CancellingstocksSQMapper mapper;

    @Autowired
    public CancellingstocksSQServiceImpl(CancellingstocksSQMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public String createTKcode() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String _str_date = format.format(date);
        Page<CancellingstocksSQ> page = selectPage(new Page<>(1, 1, "TKcode", false), new EntityWrapper<CancellingstocksSQ>().eq("flag", "E").like("TKcode", _str_date, SqlLike.DEFAULT).setSqlSelect("TKcode"));
        StringBuilder temp = new StringBuilder();
        if (page.getTotal() == 0) {
            temp.append("TK").append(_str_date).append("001");
        } else {
            String nid = page.getRecords().get(0).getTKcode();
            StringBuilder sb = new StringBuilder();

            String temp1 = nid.substring(10, 11);
            String temp2 = nid.substring(11, 12);
            int k;
            if (!"0".equals(temp1)) {
                k = Integer.parseInt(nid.substring(8));
                k++;
                sb.append(k);
                temp.append("TK").append(_str_date).append(sb);
            } else if ("0".equals(temp2)) {
                k = Integer.parseInt(nid.substring(10));
                k++;
                sb.append(k);
                if (k == 10) {
                    temp.append("TK").append(_str_date).append("0").append(sb);
                } else {
                    temp.append("TK").append(_str_date).append("00").append(sb);
                }
            } else {
                k = Integer.parseInt(nid.substring(9));
                k++;
                sb.append(k);
                if (k == 100) {
                    temp.append("TK").append(_str_date).append(sb);
                } else {
                    temp.append("TK").append(_str_date).append("0").append(sb);
                }
            }

        }
        return temp.toString();
    }

    @Override
    public CancellingstocksSQ getbycode(String tKcode) {

        return selectOne(new EntityWrapper<CancellingstocksSQ>()
                .eq("TKcode", tKcode)
                .eq("flag", "E")

        );
    }
}