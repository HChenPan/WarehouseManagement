package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.ContractBasicMapper;
import com.hchenpan.pojo.ContractBasic;
import com.hchenpan.service.ContractBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.ContractBasicServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("contractBasicService")
public class ContractBasicServiceImpl extends BaseServiceImpl<ContractBasicMapper, ContractBasic> implements ContractBasicService {
    private final ContractBasicMapper mapper;

    @Autowired
    public ContractBasicServiceImpl(ContractBasicMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public String createnumberid(String htInitials) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String _str_date = format.format(date);

        Page<ContractBasic> page = selectPage(new Page<>(1, 1, "serialsnumber", false), new EntityWrapper<ContractBasic>().eq("flag", "E").like("serialsnumber", htInitials + _str_date, SqlLike.DEFAULT).setSqlSelect("serialsnumber"));

        StringBuilder temp = new StringBuilder();
        if (page.getTotal() == 0) {
            temp.append(_str_date).append("001");
        } else {
            String nid = page.getRecords().get(0).getSerialsnumber();
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
    public Page<Map<String, Object>> selectContractPage(Page<Map<String, Object>> page, Map<String, Object> params) {
        page.setRecords(mapper.selectContractPage(page, params));
        return page;
    }

    @Override
    public List<Map<String, Object>> selectallList() {

        return transformUpperCase(mapper.selectallList());
    }
}