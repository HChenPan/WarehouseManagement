package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.WhtwarehousingMapper;
import com.hchenpan.pojo.Whtwarehousing;
import com.hchenpan.service.WhtwarehousingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.WhtwarehousingServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("whtwarehousingService")
public class WhtwarehousingServiceImpl extends BaseServiceImpl<WhtwarehousingMapper, Whtwarehousing> implements WhtwarehousingService {
    private final WhtwarehousingMapper mapper;

    @Autowired
    public WhtwarehousingServiceImpl(WhtwarehousingMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public String createnotecode() {
        //自动生成入库编号
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String _str_date = format.format(date);
        Page<Whtwarehousing> page = selectPage(new Page<>(1, 1, "notecode", false), new EntityWrapper<Whtwarehousing>().eq("flag", "E").like("notecode", _str_date, SqlLike.DEFAULT).setSqlSelect("notecode"));
        StringBuilder temp = new StringBuilder();
        if (page.getTotal() == 0) {
            temp.append(_str_date).append("001");
        } else {

            String nid = page.getRecords().get(0).getNotecode();
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

    @Override
    public List<Map<String, Object>> selectWHlist(String userid) {

        Map<String, Object> params = new HashMap<>(10);
        params.put("halfYearStartTime", getHalfYearStartTime());
        params.put("userid", userid);

        return transformUpperCase(mapper.selectWHlist(params));
    }

    @Override
    public String getrkstatus(String rkcode) {


        return selectOne(new EntityWrapper<Whtwarehousing>()
                .eq("notecode", rkcode)
                .eq("flag", "E")
        ).getRkstatus();
    }

    @Override
    public Whtwarehousing getbyrkcode(String notecode) {
        return selectOne(new EntityWrapper<Whtwarehousing>()
                .eq("notecode", notecode)
                .eq("flag", "E")
        );
    }
}