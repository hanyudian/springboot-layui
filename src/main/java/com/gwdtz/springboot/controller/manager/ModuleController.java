package com.gwdtz.springboot.controller.manager;

import com.gwdtz.springboot.entity.*;
import com.gwdtz.springboot.entity.layui.UserModule;
import com.gwdtz.springboot.service.IDeptService;
import com.gwdtz.springboot.service.ILogService;
import com.gwdtz.springboot.service.IModuleService;
import com.gwdtz.springboot.service.IUserModuleService;
import com.gwdtz.springboot.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.text.StrBuilder;

import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: springboot
 * @Date: 2021-2-24 10:38
 * @Author: Mr.Yanjc
 * @Description:
 */
@Api(description = "栏目维护管理")
@CrossOrigin
@RestController
@RequestMapping("/module")
public class ModuleController {
    @Autowired
    IModuleService moduleService;
    @Autowired
    ILogService logService;
    @Autowired
    IDeptService deptService;
    @Autowired
    IUserModuleService userModuleService;


    @ApiOperation(value = "网站模块", notes = "网站模块列表")
    @GetMapping("/moduleAllList")
    @ResponseBody
    public Result moduleAllList(HttpServletRequest request, ModuleTree moduleTree) throws Exception {
        UserLoginDo userLoginDo = (UserLoginDo) RedisUtil.hmget(request.getParameter("usernamecookie"), UserLoginDo.class);
        String userid = userLoginDo.getUserid().toString();
        List<UserModuleDO> listqxs = userModuleService.getUserModuleList(userid);
        if (listqxs.size() > 0) {
            String strQxs = "";
            for (int i = 0; i < listqxs.size(); i++) {
                strQxs += listqxs.get(i).getModuleid() + ",";
            }
            strQxs = strQxs.substring(0, strQxs.length() - 1);
            moduleTree.setId(-1);
            List<ModuleTree> list = moduleService.getAllListCheckJsonQxs(moduleTree, strQxs);
            return new Result(CODE.SUCCESS, list, "ok");
        } else {
            return new Result(CODE.ERROR, listqxs, "无栏目列表！");
        }
    }

    @ApiOperation(value = "栏目权限", notes = "设置栏目权限")
    @GetMapping("/getUserModuleList")
    @ResponseBody
    public Result getUserModuleList(HttpServletRequest request, UserModule userModule) throws Exception {
        UserLoginDo userLoginDo = (UserLoginDo) RedisUtil.hmget(request.getParameter("usernamecookie"), UserLoginDo.class);
        String userid = userLoginDo.getUserid().toString();
        List<UserModuleDO> listqxs = userModuleService.getUserModuleList(userid);
        if (listqxs.size() > 0) {
            String strQxs = "";
            for (int i = 0; i < listqxs.size(); i++) {
                strQxs += listqxs.get(i).getModuleid() + ",";
            }
            strQxs = strQxs.substring(0, strQxs.length() - 1);
            userModule.setId(-1);
            List<UserModule> list = moduleService.getUserModuleQxs(userModule, strQxs);
            return new Result(CODE.SUCCESS, list, "ok");
        } else {
            return new Result(CODE.ERROR, null, "无栏目列表！");
        }
    }

    /**
     * 查询dept字典列表
     *
     * @return
     */
    @ApiOperation(value = "栏目字典列表)")
    @GetMapping(value = "/selectDeptNameList")
    @ResponseBody
    private Map<String, Object> selectDeptNameList() {
        Map<String, Object> mapobj = new HashMap<String, Object>();
        List<DeptDO> deptList = deptService.getAll();
        mapobj.put("deptList", deptList);
        return mapobj;
    }

    @ApiOperation(value = "栏目新增")
    @PostMapping(value = "/moduleAdd/{username}")
    @ResponseBody
    public Result moduleAdd(ModuleDO moduleDO,@PathVariable("username") String username, HttpServletRequest request) throws Exception {
        try {
            //字段Sortno存储
            String MaxSortno = moduleService.getMaxSortno(moduleDO.getPid());
            if (MaxSortno == null || MaxSortno == "") {
                moduleDO.setSortno(1);
            } else {
                moduleDO.setSortno(Integer.parseInt(MaxSortno) + 1);
            }
            //字段ischild存储
            Integer parentIschild = moduleService.getIschild(moduleDO.getPid());//取得父节点的ischild
            moduleDO.setIschild(0);
            //  字段code存储
            if (moduleDO.getPid() == -1) {
                if (moduleDO.getSortno() >= 10) {
                    moduleDO.setCode(String.valueOf(moduleDO.getSortno()));
                } else {
                    moduleDO.setCode("0" + moduleDO.getSortno());
                }
            } else {
//                String aa=String.valueOf(moduleDO.getPid());
                ModuleDO parentDO = moduleService.selectModuleByPrimaryKey(String.valueOf(moduleDO.getPid()));//取得父节点的model
                String code = parentDO.getCode();
                if (moduleDO.getSortno() >= 10) {
                    moduleDO.setCode(code + moduleDO.getSortno());
                } else {
                    moduleDO.setCode(code + "0" + moduleDO.getSortno());
                }
            }
            moduleDO.setIcon("fa " + moduleDO.getIcon());
            //执行添加操作，存储日志信息，返回操作成功提示；
            Integer num = moduleService.insertSelective(moduleDO);
            //修改父节点的ischild字段
            if (parentIschild == null) {
                moduleDO.setIschild(0);
            } else if (parentIschild == 0) {
                ModuleDO parentModule = new ModuleDO();
                parentModule.setId(moduleDO.getPid());
                parentModule.setIschild(1);
                moduleService.updateIschild(parentModule);
            }
            // 在权限表中插入该条记录
            userModuleService.insert(Long.valueOf(UserUtils.userGet(username).getUserid().toString()), Long.toString(moduleDO.getId()));
            LogUtils.insert("新增栏目:" + moduleDO.getTitle(), "moduleAdd", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), "moduleDO");
            return new Result(CODE.SUCCESS, num, "插入成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "插入失败！");
        }
    }

    @ApiOperation(value = "栏目编辑")
    @PutMapping(value = "/moduleEdit/{username}")
    @ResponseBody
    public Result moduleEdit(ModuleDO moduleDO,@PathVariable("username") String username, HttpServletRequest request) throws Exception {
        try {
            moduleDO.setIcon("fa " + moduleDO.getIcon());
            Integer num = moduleService.updateByPrimaryKeySelective(moduleDO);
            LogUtils.insert("编辑栏目:" + moduleDO.getTitle(), "moduleEdit", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), "moduleDO");
            return new Result(CODE.SUCCESS, num, "更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "更新失败！");
        }
    }

    //删除子节点
    @ApiOperation(value = "删除子节点")
    @ResponseBody
    @DeleteMapping(value = "/removeChildNode/{username}/{id}")
    public String removeChildNode(@PathVariable String id, @PathVariable String username, HttpServletRequest request) {
        try {
            ModuleDO moduleDO = moduleService.selectModuleByPrimaryKey(id.toString());
            moduleService.deleteByPrimaryKey(id);
            userModuleService.deleteByModuleId(id);
            LogUtils.insert("删除栏目子节点:" + moduleDO.getTitle(), "removeChildNode", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), "id");
            return "0";
        } catch (Exception e) {
            return "1";
        }
    }

    //删除父节点
    @ApiOperation(value = "删除父节点")
    @ResponseBody
    @DeleteMapping(value = "/removeParentNodes/{username}/{id}")
    public String removeParentNodes(@PathVariable String id, @PathVariable String username, HttpServletRequest request) {
        //根据父节点id递归得到所有下级的id
        StringBuilder sb = new StringBuilder();
        String allParentAndChildIds = moduleService.getAllParentAndChildIds(id, sb);
//        System.out.println(id + "----------" + allParentAndChildIds);
        try {
            StrBuilder strBuilder = new StrBuilder();
            for (String s : allParentAndChildIds.split(",")) {
                ModuleDO moduleDO = moduleService.selectModuleByPrimaryKey(s);
                strBuilder.append(moduleDO.getTitle());
                strBuilder.append(',');
            }
            moduleService.deleteParentNodesByAllIds(allParentAndChildIds.split(","));
            userModuleService.deleteByModuleIds(allParentAndChildIds.split(","));
            LogUtils.insert("删除栏目父节点:" + strBuilder, "removeParentNodes", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), "id");
            return "0";
        } catch (Exception e) {
            return "1";
        }
    }

    @ApiOperation(value = "栏目查询(单个，用于编辑页面的查询)")
    @GetMapping(value = "/selectModuleByPrimaryKey")
    @ResponseBody
    private Result selectModuleByPrimaryKey(String id) {
        try {
            ModuleDO moduleDO = moduleService.selectModuleByPrimaryKey(id);
            return new Result(CODE.SUCCESS, moduleDO, "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result(CODE.ERROR, null, "查询失败！");
        }
    }


    @ApiOperation(value = "网站模块", notes = "网站模块列表")
    @GetMapping("/moduleAllList1")
    @ResponseBody
    public Result moduleAllList1(HttpServletRequest request, ModuleTree moduleTree) throws Exception {
        UserLoginDo userLoginDo = (UserLoginDo) RedisUtil.hmget(request.getParameter("usernamecookie"), UserLoginDo.class);
        String userid = userLoginDo.getUserid().toString();
        List<UserModuleDO> listqxs = userModuleService.getUserModuleList(userid);
        if (listqxs.size() > 0) {
            String strQxs = "";
            for (int i = 0; i < listqxs.size(); i++) {
                strQxs += listqxs.get(i).getModuleid() + ",";
            }
            strQxs = strQxs.substring(0, strQxs.length() - 1);
            List<ModuleDO> list = moduleService.getModuleLists(strQxs.split(","));
            return new Result(CODE.SUCCESS, list, "ok");
        } else {
            return new Result(CODE.ERROR, listqxs, "无栏目列表！");
        }
    }


}
