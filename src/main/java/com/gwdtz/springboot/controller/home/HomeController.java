package com.gwdtz.springboot.controller.home;


import com.gwdtz.springboot.entity.*;
import com.gwdtz.springboot.service.IDeptService;
import com.gwdtz.springboot.service.IDeptnameUsernameService;
import com.gwdtz.springboot.service.IModuleService;
import com.gwdtz.springboot.service.IUserModuleService;
import com.gwdtz.springboot.utils.CODE;
import com.gwdtz.springboot.utils.RedisUtil;
import com.gwdtz.springboot.utils.Result;
import com.sun.org.apache.xpath.internal.operations.Mod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @program: springboot
 * @Date: 2021-1-12 10:47
 * @Author: Miss.Chenmf
 * @Description:
 */
@Api(description = "左侧树形菜单")
//@CrossOrigin
@RequestMapping(value = "/home")
@RestController
public class HomeController {

    @Autowired
    IModuleService moduleService;
    @Autowired
    IUserModuleService userModuleService;
    @Autowired
    IDeptService deptService;
    @Autowired
    IDeptnameUsernameService deptnameUsernameService;

    @ApiOperation(value = "网站模块", notes = "网站模块列表")
    @GetMapping(value = "/getModuleList")
    @ResponseBody
    public Result getmoulelist(HttpServletRequest request, ModuleTree moduleTree) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            UserLoginDo userLoginDo = (UserLoginDo) RedisUtil.hmget(request.getParameter("username"), UserLoginDo.class);
            long deptid = userLoginDo.getDeptid();
            String username = userLoginDo.getUsername();
            String userid = userLoginDo.getUserid().toString();
            String deptlevelcode = deptService.selectDeptById(deptid).getDeptlevelcode();
            List<UserModuleDO> listqxs = userModuleService.getUserModuleList(userid);
            if(listqxs.size() == 0){
                map.put("flag", true);
                return new Result(CODE.SUCCESS, map, "ok");
            }
            String strQxs = "";
            for (int i = 0; i < listqxs.size(); i++) {
                strQxs += listqxs.get(i).getModuleid() + ",";
            }
            strQxs = strQxs.substring(0, strQxs.length() - 1);
            moduleTree.setId(1);
            List<ModuleTree> list = new ArrayList<>();
            if (deptlevelcode.length() == 2) {
                list = moduleService.getAllListCheckJsonQxs(moduleTree, strQxs);
            } else {
                if (deptlevelcode.substring(0, 4).equals("0001")) {
                    list = moduleService.getAllListCheckJsonQxs(deptlevelcode, moduleTree, strQxs);
                } else {
                    list = moduleService.getAllListCheckJsonQxs(moduleTree, strQxs);
                }
            }

            if (deptlevelcode.length() == 2) {
                if(list.size() == 3){
                    Collections.swap(list, 2, 0);
                    Collections.swap(list, 2, 1);
                }
            } else {
                if (list.size() == 2) {
                    if (deptlevelcode.substring(0, 4).equals("0001")) {
                        Collections.swap(list, 1, 0);
                    } else if (deptlevelcode.substring(0, 4).equals("0002")) {

                    }
                } else if (list.size() == 3) {
                    Collections.swap(list, 1, 0);
                }
            }

            map.put("deptlevelcode", deptlevelcode);
            map.put("username", username);
            map.put("list", list);
            return new Result(CODE.SUCCESS, map, "ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, "redis缓存宕机！", "ok");
        }

    }

    @ApiOperation(value = "管理员登录显示树结构", notes = "管理员登录显示树结构")
    @GetMapping(value = "/getDeptList")
    @ResponseBody
    public Result getdeptlist(HttpServletRequest request, DeptTreeDO deptTreeDO) throws Exception {
        Map<String, Object> map = new HashMap<>();
        deptTreeDO.setId(1);
        List<DeptTreeDO> list = deptService.getfrontnav(deptTreeDO);
        map.put("list", list);
        return new Result(CODE.SUCCESS, map, "ok");
    }

    @ApiOperation(value = "网站模块", notes = "网站模块列表")
    @GetMapping(value = "/delredis")
    @ResponseBody
    public Result delredis() throws Exception {
        RedisUtil.del("userobj");
        return new Result(CODE.SUCCESS, null, "ok");
    }
}

