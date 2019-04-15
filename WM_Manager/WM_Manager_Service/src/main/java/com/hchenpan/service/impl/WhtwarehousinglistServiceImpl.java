package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.WhtwarehousinglistMapper;
import com.hchenpan.pojo.Whtwarehousinglist;
import com.hchenpan.service.WhtwarehousinglistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.WhtwarehousinglistServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("whtwarehousinglistService")
public class WhtwarehousinglistServiceImpl extends BaseServiceImpl<WhtwarehousinglistMapper, Whtwarehousinglist> implements WhtwarehousinglistService {
    private final WhtwarehousinglistMapper mapper;

    @Autowired
    public WhtwarehousinglistServiceImpl(WhtwarehousinglistMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public int getkongnum(String rkcode) {
        return selectCount(new EntityWrapper<Whtwarehousinglist>()
                .eq("flag", "E")
                .eq("rkcode", rkcode)
                .eq("sjnum", "0.00")
        );
    }

    @Override
    public int getkongnum1(String rkcode) {
        return selectCount(new EntityWrapper<Whtwarehousinglist>()
                .eq("rkcode", rkcode)
        );
    }

    @Override
    public List<Whtwarehousinglist> getallrkmx(String rkcode) {

        return selectList(new EntityWrapper<Whtwarehousinglist>()
                .eq("flag", "E")
                .eq("rkcode", rkcode)
        );
    }

    @Override
    public Whtwarehousinglist getallbyidzjx(String id) {


        return selectOne(new EntityWrapper<Whtwarehousinglist>()
                .eq("id", id)

        );
    }

    @Override
    public int getwznum(String wzcode, String flag, String rkcode) {

        return selectCount(new EntityWrapper<Whtwarehousinglist>()
                .eq("flag", "E")
                .eq("rkcode", rkcode)
                .eq("wzcode", wzcode)


        );
    }
}