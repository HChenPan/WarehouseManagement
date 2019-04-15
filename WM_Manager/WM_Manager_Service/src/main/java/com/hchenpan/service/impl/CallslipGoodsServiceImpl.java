package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.CallslipGoodsMapper;
import com.hchenpan.pojo.CallslipGoods;
import com.hchenpan.service.CallslipGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.CallslipGoodsServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("callslipGoodsService")
public class CallslipGoodsServiceImpl extends BaseServiceImpl<CallslipGoodsMapper, CallslipGoods> implements CallslipGoodsService {
    private final CallslipGoodsMapper mapper;

    @Autowired
    public CallslipGoodsServiceImpl(CallslipGoodsMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public List<CallslipGoods> getwzlist(String callslipcode) {


        return selectList(new EntityWrapper<CallslipGoods>()
                .eq("flag", "E")
                .eq("callslipcode", callslipcode)
        );
    }

    @Override
    public int getwznum(String wzcode, String flag, String callslipcode, String comefrom, String plancode, String buycode, String contractbasicid, String rkcode) {

        return selectCount(new EntityWrapper<CallslipGoods>()
                .eq("wzcode", wzcode)
                .eq("flag", flag)
                .eq("callslipcode", callslipcode)

        );
    }

    @Override
    public CallslipGoods getbycode(CallslipGoods callslipGoods) {

        return selectOne(new EntityWrapper<CallslipGoods>()
                .eq("callslipcode", callslipGoods.getCallslipcode())
                .eq("wzcode", callslipGoods.getWzcode())
                .eq("flag", "E")

        );
    }
}