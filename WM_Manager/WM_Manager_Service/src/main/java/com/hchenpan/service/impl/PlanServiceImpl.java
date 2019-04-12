package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.PlanMapper;
import com.hchenpan.pojo.Plan;
import com.hchenpan.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.PlanServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/4/11 12:32 下午
 **/
@Service("planService")
public class PlanServiceImpl extends BaseServiceImpl<PlanMapper, Plan> implements PlanService {
    private final PlanMapper mapper;

    @Autowired
    public PlanServiceImpl(PlanMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String createplancode(String plantype) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String strDate = format.format(date);
        Page<Plan> page = selectPage(new Page<>(1, 1, "plancode", false), new EntityWrapper<Plan>().eq("flag", "E").like("plancode", strDate, SqlLike.DEFAULT).setSqlSelect("plancode"));
        StringBuilder temp = new StringBuilder();
        if (page.getTotal() == 0) {
            temp.append(strDate).append("001");
        } else {
            String nid = page.getRecords().get(0).getPlancode();
            StringBuilder sb = new StringBuilder();

            String  temp1=nid.substring(12, 13);
            String  temp2=nid.substring(13, 14);
            int k;
            if (!"0".equals(temp1)) {
                k=Integer.parseInt(nid.substring(12));
                k++;
                sb.append(k);
                temp.append(strDate).append(sb);
            } else if ("0".equals(temp2)) {
                k=Integer.parseInt(nid.substring(14));
                k++;
                sb.append(k);
                if (k == 10) {
                    temp.append(strDate).append("0").append(sb);
                } else {
                    temp.append(strDate).append("00").append(sb);
                }
            } else {
                k=Integer.parseInt(nid.substring(9));
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