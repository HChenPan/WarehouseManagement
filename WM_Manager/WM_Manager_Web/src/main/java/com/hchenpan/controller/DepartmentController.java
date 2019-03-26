package com.hchenpan.controller;

import com.hchenpan.common.BaseController;
import com.hchenpan.model.DepartJSON;
import com.hchenpan.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.UserController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class DepartmentController extends BaseController {
    private final DepartmentService departmentService;


    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * description(描述) 获取部门树形结构
     *
     * @return string
     * @author Huangcp
     * @date 2019/3/24 04:42 下午
     **/
    @ResponseBody
    @RequestMapping(value = "/department/gettreedepart")
    public String gettreedepart() {
        List<DepartJSON> departlist = departmentService.getrootlist();
        List<DepartJSON> dlist = gettree(departlist);
        return toJson(dlist);
    }

    /**
     * description(描述) 递归调用函数
     *
     * @param dlist 参数
     * @return java.util.List<com.hchenpan.pojo.Department>
     * @author Huangcp
     * @date 2019/3/24 04:46 下午
     **/
    private List<DepartJSON> gettree(List<DepartJSON> dlist) {
        for (DepartJSON d : dlist) {
            List<DepartJSON> departlist = departmentService.getsonlist(d);
            if (departlist.size() > 0) {
                d.setChildren(gettree(departlist));
            }
        }
        return dlist;
    }

}