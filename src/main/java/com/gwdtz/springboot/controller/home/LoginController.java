package com.gwdtz.springboot.controller.home;

import com.gwdtz.springboot.entity.DeptDO;
import com.gwdtz.springboot.entity.DeptnameUserNameDo;
import com.gwdtz.springboot.entity.UserDO;
import com.gwdtz.springboot.entity.UserLoginDo;
import com.gwdtz.springboot.service.IDeptService;
import com.gwdtz.springboot.service.IDeptnameUsernameService;
import com.gwdtz.springboot.service.ILoginService;
import com.gwdtz.springboot.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Track;
import javax.sql.rowset.BaseRowSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: springboot
 * @Date: 2021-1-11 15:09
 * @Author: Mr.Wu
 * @Description:
 */
@Api(description = "登录管理")
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    ILoginService loginService;

    @Autowired
    IDeptService deptService;

    @Autowired
    IDeptnameUsernameService deptnameUsernameService;

    @ApiOperation(value = "登录", notes = "POST用户登录签发JWT")
    @PostMapping("/sign")
    public Result sign(String username, String password, HttpServletResponse response) {
        UserLoginDo userDO;
        String rawPass = username + password;
        password = EncodeUtils.md5Encode(rawPass);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            userDO = loginService.findByName(username);
            if (userDO == null) {
                return new Result(CODE.ERROR, null, "用户不存在！");
            } else {
                if (!userDO.getPassword().equals(password)) {
                    return new Result(CODE.ERROR, null, "密码不正确！");
                } else {
                    Map<String, Object> chaim = new HashMap<String, Object>();
                    DeptDO deptDO = deptService.selectDeptById(userDO.getDeptid());
                    chaim.put("username", username);
                    JwtUtil jwtUtil = new JwtUtil();
                    String jwtToken = jwtUtil.encode(username, chaim);
                    int flag = 0;
                    if (username.equals("hhgwd")) {
                        flag = 1;
                    } else {
                        List<DeptnameUserNameDo> deptnameUserNameDos = deptnameUsernameService.selectDeptnameUsernameList();
                        for (DeptnameUserNameDo deptnameUserNameDo :
                                deptnameUserNameDos) {
                            if (deptnameUserNameDo.getUsername().equals(username)) {
                                flag = 1;
                                break;
                            }
                        }
                    }
                    map.put("role", userDO.getRoleid());
                    map.put("msg", "登录成功");
                    map.put("token", jwtToken);
                    map.put("username", username);
                    map.put("deptcode", deptDO.getDeptcode());
                    //判断是否是
                    map.put("flag", flag);
                    RedisUtil.hmset(username, userDO);
                    return new Result(CODE.SUCCESS, map, "ok");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result(CODE.ERROR, null, "登录失败");
        }
    }

    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @PutMapping("/password")
    public Result password(String old_password, String new_password, String again_password, String username) throws Exception {
        try {
            UserDO userDO = (UserDO) RedisUtil.hmget(username, UserDO.class);
            String old_rawPass = username + old_password;
            String old_cipher = EncodeUtils.md5Encode(old_rawPass);
            if (old_cipher.equals(userDO.getPassword())) {
                if (new_password.equals(again_password)) {
                    String new_rawPass = username + new_password;
                    String new_cipher = EncodeUtils.md5Encode(new_rawPass);
                    userDO.setPassword(new_cipher);
                    Integer num = loginService.updatePsw(userDO);
                    LogUtils.insert("修改用户密码:" + username, "password", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), "旧密码：" + old_password + "   新密码：" + new_password);
                    return new Result(CODE.SUCCESS, num, "密码修改成功，请重新登录！");
                } else {
                    return new Result(CODE.ERROR, null, "输入密码不一致!");
                }
            } else {
                return new Result(CODE.ERROR, null, "旧密码输入错误!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result(CODE.ERROR, null, "修改密码失败");
        }
    }
}
