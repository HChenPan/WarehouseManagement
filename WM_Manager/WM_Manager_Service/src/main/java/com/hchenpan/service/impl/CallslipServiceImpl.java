package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.CallslipMapper;
import com.hchenpan.pojo.Callslip;
import com.hchenpan.service.CallslipService;
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
 * ClassName : com.hchenpan.service.impl.CallslipServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("callslipService")
public class CallslipServiceImpl extends BaseServiceImpl<CallslipMapper, Callslip> implements CallslipService {
    private final CallslipMapper mapper;

    @Autowired
    public CallslipServiceImpl(CallslipMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public List<Map<String, Object>> getallOrderByApplication(String tkrid) {
        Map<String, Object> params = new HashMap<>(10);
        params.put("tkrid", tkrid);
        params.put("HalfYearStartTime", getHalfYearStartTime());


        return transformUpperCase(mapper.getallOrderByApplication(params));
    }

    @Override
    public String createnumberid(String htInitials) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String _str_date = format.format(date);

        Page<Callslip> page = selectPage(new Page<>(1, 1, "callslipcode", false), new EntityWrapper<Callslip>().eq("flag", "E").like("callslipcode", htInitials + _str_date, SqlLike.DEFAULT).setSqlSelect("callslipcode"));

        StringBuilder temp = new StringBuilder();
        if (page.getTotal() == 0) {
            temp.append(_str_date).append("001");
        } else {
            String nid = page.getRecords().get(0).getCallslipcode();
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
    public Page<Map<String, Object>> selectPagelist(Page<Map<String, Object>> page, Map<String, Object> params) {
        page.setRecords(transformUpperCase(mapper.selectPagelist(page, params)));
        return page;
    }

    @Override
    public Page<Map<String, Object>> selectPagelistck(Page<Map<String, Object>> page, Map<String, Object> params) {
        page.setRecords(transformUpperCase(mapper.selectPagelistck(page, params)));
        return page;
    }

    @Override
    public Page<Map<String, Object>> selectSPlist(Page<Map<String, Object>> page, String toString) {
        Map<String, Object> params = new HashMap<>(10);

        params.put("sql", toString);

        page.setRecords(transformUpperCase(mapper.selectSPlistck(page, params)));
        return page;
    }

    @Override
    public String getstatus(String callslipcode) {

        return selectOne(new EntityWrapper<Callslip>()
                .eq("callslipcode", callslipcode)
                .eq("flag", "E")
                .orderBy("updatetime")
        ).getStatus();
    }
}