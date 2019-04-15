package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.WarehousinglistMapper;
import com.hchenpan.pojo.Warehousinglist;
import com.hchenpan.service.WarehousinglistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.WarehousinglistServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("warehousinglistService")
public class WarehousinglistServiceImpl extends BaseServiceImpl<WarehousinglistMapper, Warehousinglist> implements WarehousinglistService {
    private final WarehousinglistMapper mapper;

    @Autowired
    public WarehousinglistServiceImpl(WarehousinglistMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public int getkongnum(String rkcode) {

        return selectCount(new EntityWrapper<Warehousinglist>()
                .eq("flag", "E")
                .eq("rkcode", rkcode)
                .eq("sjnum", "0.00")
        );
    }

    @Override
    public int getkongnum1(String rkcode) {

        return selectCount(new EntityWrapper<Warehousinglist>()

                .eq("rkcode", rkcode)
        );

    }

    @Override
    public List<Warehousinglist> getallrkmx(String rkcode) {
        return selectList(new EntityWrapper<Warehousinglist>()

                .eq("rkcode", rkcode)
        );
    }

    @Override
    public int getwznum(String wzcode, String contractbasicid, String buycode, String plancode, String flag, String rkcode) {
        return selectCount(new EntityWrapper<Warehousinglist>()
                .eq("wzcode", wzcode)
                .eq("contractbasicid", contractbasicid)
                .eq("buycode", buycode)
                .eq("plancode", plancode)
                .eq("flag", flag)
                .eq("rkcode", rkcode)
        );
    }

    @Override
    public List<Map<String, Object>> selectWZList(Warehousinglist warehousinglist) {
        Map<String, Object> params = new HashMap<>(10);

        String[] aa = warehousinglist.getPlancode().split(",");
        String plancode = "";
        for (int i = 0; i < aa.length; i++) {
            plancode = plancode + "'" + aa[i] + "'" + ",";
        }
        plancode = plancode.substring(0, plancode.length() - 1);
        params.put("plancode", plancode);
        params.put("storehousecode", warehousinglist.getStorehousecode());

        return transformUpperCase(mapper.selectWZList(params));
    }

    @Override
    public Warehousinglist getgoods(String plancode, String buycode, String contractbasicid, String rkcode, String wzcode) {

        return selectOne(new EntityWrapper<Warehousinglist>()

                .eq("plancode", plancode)
                .eq("buycode", buycode)
                .eq("contractbasicid", contractbasicid)
                .eq("rkcode", rkcode)
                .eq("wzcode", wzcode)
                .eq("flag", "E")
        );
    }
}