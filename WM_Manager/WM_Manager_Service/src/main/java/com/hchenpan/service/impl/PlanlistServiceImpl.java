package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.PlanlistMapper;
import com.hchenpan.mapper.SparepartCodeMapper;
import com.hchenpan.mapper.StockMapper;
import com.hchenpan.pojo.Planlist;
import com.hchenpan.pojo.SparepartCode;
import com.hchenpan.pojo.Stock;
import com.hchenpan.service.PlanlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.PlanlistServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/4/11 12:33 下午
 **/
@Service("planlistService")
public class PlanlistServiceImpl extends BaseServiceImpl<PlanlistMapper, Planlist> implements PlanlistService {
    private final PlanlistMapper mapper;
    private final StockMapper stockMapper;
    private final SparepartCodeMapper sparepartCodeMapper;

    @Autowired
    public PlanlistServiceImpl(PlanlistMapper mapper, StockMapper stockMapper, SparepartCodeMapper sparepartCodeMapper) {
        this.mapper = mapper;
        this.stockMapper = stockMapper;
        this.sparepartCodeMapper = sparepartCodeMapper;
    }

    @Override
    public List<Map<String, Object>> selectWZList() {

        return transformUpperCase(mapper.selectWZList());
    }

    @Override
    public List<Planlist> selectWZ(String zjcode, String zjname, String code, String name) {
        List<SparepartCode> sparepartCodes = sparepartCodeMapper.selectList(new EntityWrapper<SparepartCode>().eq("description", "物资")
                .like("code", code, SqlLike.DEFAULT)
                .like("name", name, SqlLike.DEFAULT)
        );
        List<Planlist> dlist = new ArrayList<>();
        for (SparepartCode sparepartCode : sparepartCodes) {
            Planlist d = new Planlist();
            d.setWzid(sparepartCode.getId());
            d.setWzcode(sparepartCode.getCode());
            d.setWzname(sparepartCode.getName());
            d.setModelspcification(sparepartCode.getModelspecification());
            d.setUnit(sparepartCode.getUnit());
            d.setPlannum("0.00");
            d.setPlanmoney("0.00");
            d.setSpnum("0.00");
            d.setSpmoney("0.00");
            d.setZjcode(zjcode);
            d.setZjname(zjname);
            d.setSynum("0.00");
            List<Map<String, Object>> mapList = mapper.selectWZ(d.getWzcode(), getHalfYearStartTime());
            List<Stock> stocks = stockMapper.selectList(new EntityWrapper<Stock>()
                    .eq("wzcode", d.getWzcode())
                    .isNull("tranflag")
                    .isNull("stockyearmon")
            );
            if (mapList.size() == 0) {
                if (stocks.size() == 0) {
                    d.setPrice("0.00");
                    d.setSpprice("0.00");
                } else {
                    List<Map<String, Object>> sumlist = mapper.selectSum(d.getWzcode());
                    String a = sumlist.get(0).get("BQEND").toString();
                    String b = sumlist.get(0).get("BQENDMONEY").toString();
                    BigDecimal a1 = new BigDecimal(a);
                    BigDecimal b1 = new BigDecimal(b);
                    BigDecimal c1 = b1.divide(a1, 2, BigDecimal.ROUND_HALF_UP);
                    d.setPrice(c1.toString());
                    d.setSpprice(c1.toString());
                }
            } else {
                d.setPrice(mapList.get(0).get("BUYPRICE").toString());
                d.setSpprice(mapList.get(0).get("BUYPRICE").toString());
            }

            dlist.add(d);
        }

        return dlist;
    }
}