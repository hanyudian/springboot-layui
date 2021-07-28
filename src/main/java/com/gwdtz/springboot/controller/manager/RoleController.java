package com.gwdtz.springboot.controller.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gwdtz.springboot.entity.RoleDO;
import com.gwdtz.springboot.service.ILogService;
import com.gwdtz.springboot.service.IRoleService;
import com.gwdtz.springboot.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @Author hanshuai(the developing of Four)
 * @Description
 * @return
 * @Date 2021/6/7 16:13
 **/
@Api(description = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    IRoleService roleService;
    @Autowired
    ILogService logService;

    //角色列表查询
    @ApiOperation(value = "角色列表", notes = "notes")
    @GetMapping(value = "/getRoleList")
    @ResponseBody
    public Map<String, Object> getRoleList( HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> mapobj = new HashMap<String, Object>();
        PageInfo pageInfo = null;
        PageHelper.startPage(request);
        List<RoleDO> list = roleService.getRoleList(request.getParameter("name"), request.getParameter("description"));
        pageInfo = new PageInfo(list);
        mapobj.put("code", 0);
        mapobj.put("msg", "成功！");
        mapobj.put("data", pageInfo.getList());
        mapobj.put("count", pageInfo.getTotal());
        return mapobj;

    }

    //角色查询
    @ApiOperation(value = "角色查询(单个，用于编辑页面的查询)")
    @GetMapping(value = "/selectRoleById")
    @ResponseBody
    private Result selectRoleById(long id) {
        try {
            RoleDO roleDO = roleService.selectRoleById(id);
            return new Result(CODE.SUCCESS, roleDO, "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result(CODE.ERROR, null, "查询失败！");
        }
    }

    //角色新增
    @ApiOperation(value = "角色新增")
    @PostMapping(value = "/roleAdd")
    @ResponseBody
    public Result roleAdd(RoleDO roleDO, HttpServletRequest request) {
        try {
            Integer num = roleService.insert(roleDO);
//            LogUtils.insert("角色新增:" + roleDO.getName(), "roleAdd", UserUtils.userGet(request).getUserid(), UserUtils.userGet(request).getUsername(), "roleDO");
            return new Result(CODE.SUCCESS, num, "插入成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "插入失败！");
        }
    }

    //角色编辑
    @ApiOperation(value = "角色编辑")
    @PutMapping(value = "/roleEdit")
    @ResponseBody
    public Result roleEdit(RoleDO roleDO, HttpServletRequest request) {
        try {
            Integer num = roleService.update(roleDO);
//            LogUtils.insert("角色编辑:" + roleDO.getName(), "roleEdit", UserUtils.userGet(request).getUserid(), UserUtils.userGet(request).getUsername(), "roleDO");
            return new Result(CODE.SUCCESS, num, "更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "更新失败！");
        }
    }

    //角色删除
    @ApiOperation(value = "角色删除")
    @DeleteMapping(value = "/deleteRoleById")
    public Result deleteRoleById(long id, HttpServletRequest request) {
        try {
            RoleDO roleDO = roleService.selectRoleById(id);
            Integer num = roleService.deleteById(id);
            LogUtils.insert("角色删除:" + roleDO.getName(), "deleteRoleById", UserUtils.userGet(request).getUserid(), UserUtils.userGet(request).getUsername(), "id");
            return new Result(CODE.SUCCESS, num, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "删除失败！");
        }

    }
}