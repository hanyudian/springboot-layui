package com.gwdtz.springboot.controller.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gwdtz.springboot.entity.*;
import com.gwdtz.springboot.service.IUserService;
import com.gwdtz.springboot.service.ILogService;
import com.gwdtz.springboot.service.IUserModuleService;
import com.gwdtz.springboot.service.IRoleService;
import com.gwdtz.springboot.service.IDeptService;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program: springboot
 * @Date: 2021-2-25 10:00
 * @Author: Miss.Yanjc
 * @Description:
 */
@Api(description = "用户管理")
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IUserModuleService userModuleService;
    @Autowired
    ILogService logService;
    @Autowired
    IDeptService deptService;


    @ApiOperation(value = "用户管理", notes = "notes")
    @GetMapping(value = "/getUserList")
    @ResponseBody
    public Map<String, Object> getuserlist( HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserLoginDo userDO = (UserLoginDo) RedisUtil.hmget(request.getParameter("username"), UserLoginDo.class);
        String username = userDO.getUsername();
        String deptlevelcode = deptService.selectDeptById(userDO.getDeptid()).getDeptlevelcode();
        Map<String, Object> mapobj = new HashMap<String, Object>();
        PageInfo pageInfo = null;
        PageHelper.startPage(request);
        List<UserDO> list = userService.getUserList(username, deptlevelcode,request.getParameter("name"), request.getParameter("deptname"), request.getParameter("rolename"));
        if(!userDO.getUsername().equals("admin")){
            Iterator<UserDO> iterator = list.iterator();
            while (iterator.hasNext()){
                UserDO next = iterator.next();
                if(next.getUsername().equals("admin") || next.getUsername().equals(username) ){
                    iterator.remove();
                }
            }
        }
        pageInfo = new PageInfo(list);
        mapobj.put("code", "0");
        mapobj.put("msg", "");
        mapobj.put("data", pageInfo.getList());
        mapobj.put("count", pageInfo.getTotal());
        return mapobj;
    }

    @ApiOperation(value = "查询用户", notes = "notes")
    @GetMapping(value = "/getuserlist1")
    @ResponseBody
    public Map<String, Object> getuserlist1(String deptid, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> mapobj = new HashMap<String, Object>();
        PageInfo pageInfo = null;
        PageHelper.startPage(request);
        List<UserDO> list = userService.getUserList1(deptid);
        pageInfo = new PageInfo(list);
        mapobj.put("pageList", pageInfo.getList());
        mapobj.put("pageCount", pageInfo.getTotal());
        //mapobj.put("cookies",cookies);
        return mapobj;

    }

    //用户查询
    @ApiOperation(value = "用户查询(单个，用于编辑页面的查询)")
    @GetMapping(value = "/selectUserById")
    @ResponseBody
    private Result selectUserById(long userid) {
        try {
            UserDO userDO = userService.selectUserByUserid(userid);
            DeptDO deptDO = deptService.selectDeptById(userDO.getDeptid());
            userDO.setDeptname(deptDO.getDeptname());
            return new Result(CODE.SUCCESS, userDO, "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result(CODE.ERROR, null, "查询失败！");
        }
    }

    @ApiOperation(value = "用户新增")
    @PostMapping(value = "/userAdd/{username}")
    @ResponseBody
    public Result userAdd(UserDO userDO,@PathVariable("username") String username, HttpServletRequest request) {
        String rawPass = userDO.getUsername() + "Aa12345";
        userDO.setPassword(EncodeUtils.md5Encode(rawPass));
        try {
            String userName = userDO.getUsername();
            userService.getUserName(userName);
            Integer num = userService.insert(userDO);
            LogUtils.insert("用户新增:" + userName, "userAdd", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), "userDO");
            return new Result(CODE.SUCCESS, num, "插入成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "插入失败！");
        }
    }

    @ApiOperation(value = "用户编辑")
    @PutMapping(value = "/userEdit/{username}")
    @ResponseBody
    public Result userEdit(UserDO userDO,@PathVariable("username") String username, HttpServletRequest request) {
//        String rawPass = userDO.getUsername() + "Aa12345";
//        userDO.setPassword(EncodeUtils.md5Encode(rawPass));
        try {
            Integer num = userService.update(userDO);
            LogUtils.insert("用户编辑:" + userDO.getUsername(), "userEdit", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), "userDO");
            return new Result(CODE.SUCCESS, num, "更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "更新失败！");
        }
    }

    //用户删除
    @ApiOperation(value = "用户删除")
    @DeleteMapping(value = "/deleteUserById")
    @ResponseBody
    public Result deleteUserById(HttpServletRequest request) {
        try {
            long userid = Long.parseLong(request.getParameter("userid"));
            UserDO userDO = userService.selectUserByUserid(userid);
            Integer num = userService.deleteByUserid(userid);
            userModuleService.deleteByUserid(userid);
            LogUtils.insert("用户删除:" + userDO.getUsername(), "deleteUserById", UserUtils.userGet(request).getUserid(), UserUtils.userGet(request).getUsername(), "userid");
            return new Result(CODE.SUCCESS, num, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "删除失败！");
        }
    }

    /**
     * 查询权限列表
     *
     * @return
     */
    @ApiOperation(value = "权限列表)")
    @GetMapping(value = "/selectRoleList")
    @ResponseBody
    private Map<String, Object> selectRoleList() {
        Map<String, Object> mapobj = new HashMap<String, Object>();
        List<RoleDO> list = roleService.selectList();
        mapobj.put("RoleList", list);
        return mapobj;
    }


    //用户模块编辑
    @ApiOperation(value = "设置权限")
    @PostMapping(value = "/userModuleEdit")
    @ResponseBody
    public Result usermoduleedit(Long userid, String moduleids, HttpServletRequest request) {
        try {
            Integer num = userModuleService.deleteById(userid, moduleids);
            LogUtils.insert("设置权限:"+userid,"usermoduleedit", UserUtils.userCookieGet(request).getUserid(),UserUtils.userCookieGet(request).getUsername(),"userid,moduleids");
            return new Result(CODE.SUCCESS, num, "更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "更新失败！");
        }
    }

    //用户模块编辑
    @ApiOperation(value = "重置密码")
    @PostMapping(value = "/passwordReset")
    @ResponseBody
    public Result passwordReset(HttpServletRequest request) {
        try {
            String userid = request.getParameter("userid");
            String username = request.getParameter("username");
            if (userid != null && !userid.equals("") && username != null && !username.equals("") && userid.length() > 0 && username.length() > 0) {
                String rawPass = username + "Aa12345";
                String password = EncodeUtils.md5Encode(rawPass);
                UserDO userDO = new UserDO();
                userDO.setUserid(Integer.parseInt(userid));
                userDO.setUsername(username);
                userDO.setPassword(password);
                Integer num = userService.passwordReset(userDO);
                LogUtils.insert("重置密码:"+userid,"passwordReset", UserUtils.userGet(request).getUserid(),UserUtils.userGet(request).getUsername(),"userid,username");
                return new Result(CODE.SUCCESS, num, "重置成功！");
            } else {
                return new Result(CODE.ERROR, null, "操作失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "重置失败！");
        }
    }

    @ApiOperation(value = "获取选择栏目列表")
    @GetMapping(value = "/selectModuleById")
    @ResponseBody
    public Result selectModuleById(Long userid) {
        try {
            Integer[] num = userModuleService.selectModuleById(userid);
            return new Result(CODE.SUCCESS, num, "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result(CODE.ERROR, null, "查询失败！");
        }
    }

    @ApiOperation(value = "校验用户名是否存在")
    @GetMapping("/existUsername")
    @ResponseBody
    public boolean existUsername(HttpServletRequest request) {
        Boolean b = true;
        String username = request.getParameter("username");
        UserDO userDo = userService.getUserName(username);
        if (userDo != null) {
            b = false;
            return b;
        }
        return b;
    }

}



