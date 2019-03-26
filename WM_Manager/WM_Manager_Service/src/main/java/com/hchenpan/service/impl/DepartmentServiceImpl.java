package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.DepartmentMapper;
import com.hchenpan.model.DepartJSON;
import com.hchenpan.pojo.Department;
import com.hchenpan.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.TestServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("departmentService")
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    private final DepartmentMapper mapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public List<DepartJSON> getrootlist() {
        EntityWrapper<Department> ew = new EntityWrapper<>();
        ew.isNull("PARENTID");
        List<Department> dList = selectList(ew);
        List<DepartJSON> djList = new ArrayList<>();
        for (int i = 0; i < dList.size(); i++) {
            DepartJSON departJSON = new DepartJSON();
            departJSON.setId(dList.get(0).getId());
            departJSON.setText(dList.get(0).getName());
            djList.add(departJSON);
        }
        return djList;
    }

    @Override
    public List<DepartJSON> getsonlist(DepartJSON d) {
        EntityWrapper<Department> ew = new EntityWrapper<>();
        ew.eq("PARENTID", d.getId());
        List<Department> dList = selectList(ew);
        List<DepartJSON> djList = new ArrayList<>();
        for (Department department : dList) {
            DepartJSON departJSON = new DepartJSON();
            departJSON.setId(department.getId());
            departJSON.setText(department.getName());
            EntityWrapper<Department> ew1 = new EntityWrapper<>();
            ew.eq("PARENTID", department.getId());
            if (selectCount(ew1) > 0) {
                departJSON.setState("closed");
            }
            djList.add(departJSON);
        }
        return djList;
    }
}