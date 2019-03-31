package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.SparepartCodeMapper;
import com.hchenpan.model.PageContainer;
import com.hchenpan.model.Spart;
import com.hchenpan.pojo.SparepartCode;
import com.hchenpan.service.SparepartCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.UserServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:41 上午
 **/
@Service("sparepartCodeService")
public class SparepartCodeServiceImpl extends BaseServiceImpl<SparepartCodeMapper, SparepartCode> implements SparepartCodeService {
    private final SparepartCodeMapper sparepartCodeMapper;

    @Autowired
    public SparepartCodeServiceImpl(SparepartCodeMapper sparepartCodeMapper) {
        this.sparepartCodeMapper = sparepartCodeMapper;
    }

    @Override
    public PageContainer<Spart> getroot() {
        PageContainer<Spart> list = new PageContainer<>();
        List<Spart> dlist = new ArrayList<>();
        EntityWrapper<SparepartCode> ew = new EntityWrapper<>();

        ew.like("description", "备件类别");
        List<SparepartCode> sparepartCodes = sparepartCodeMapper.selectList(ew);
        for (SparepartCode sparepartCode : sparepartCodes) {
            Spart d = new Spart();
            d.setId(sparepartCode.getId());
            d.setName(sparepartCode.getName());
            d.setCode(sparepartCode.getCode());
            d.setDescription(sparepartCode.getDescription());
            d.setParentcode(sparepartCode.getParentcode());
            d.setParentid(sparepartCode.get_parentid());
            d.setState("closed");
            dlist.add(d);
        }
        list.setTotal(dlist.size());
        list.setRows(dlist);
        return list;
    }

    @Override
    public List<Spart> getsons(Spart spart) {
        EntityWrapper<SparepartCode> ew = new EntityWrapper<>();
        ew.like("parentid", spart.getId());
        List<SparepartCode> sparepartCodes = sparepartCodeMapper.selectList(ew);
        List<Spart> dlist = new ArrayList<>();
        for (SparepartCode sparepartCode : sparepartCodes) {
            Spart d = new Spart();
            d.setId(sparepartCode.getId());
            d.setName(sparepartCode.getName());
            d.setCode(sparepartCode.getCode());
            d.setDescription(sparepartCode.getDescription());
            d.setParentcode(sparepartCode.getParentcode());
            d.setParentid(sparepartCode.get_parentid());
            if (sparepartCodeMapper.selectCount(new EntityWrapper<SparepartCode>().eq("parentid", d.getId())) > 0) {
                d.setState("closed");
            }
            dlist.add(d);
        }
        return dlist;
    }

    @Override
    public String getexpandid(String parentid) {
        SparepartCode sparepartCode1 = new SparepartCode();
        sparepartCode1.setParentid(parentid);
        return sparepartCodeMapper.selectOne(sparepartCode1).get_parentid();
    }
}