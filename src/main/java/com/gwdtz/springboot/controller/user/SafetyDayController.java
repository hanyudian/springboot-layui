package com.gwdtz.springboot.controller.user;

import com.gwdtz.springboot.entity.*;
import com.gwdtz.springboot.service.IDeptService;
import com.gwdtz.springboot.service.ISafetyDayService;
import com.gwdtz.springboot.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName SafetyDayController
 * @Description
 * @Author hanshuai(the Developing of Four)
 * @Date 2021/6/16 10:08
 * @Version 1.0
 **/
@Api(description = "安全天数")
@RequestMapping("/safetyDay")
@RestController
public class SafetyDayController {

    @Autowired
    ISafetyDayService safetyDayService;

    @Autowired
    IDeptService deptService;

    //    @ApiOperation(value = "安全天数更新")
//    @PutMapping(value = "/updateEvent")
//    @ResponseBody
//    public Result updateEvent(String switchA, String switchB, String switchC, String switchD, String username) {
//        try {
//            UserLoginDo userDO = (UserLoginDo) RedisUtil.hmget(username, UserLoginDo.class);
//            DeptDO deptDO = deptService.selectDeptById(userDO.getDeptid());
//            SafetyDayDo safetyDayDo = safetyDayService.existToday(userDO.getDeptid());
//            if (safetyDayDo == null) {
//                safetyDayService.updateEvent(switchA, switchB, switchC, switchD, userDO.getDeptid());
//                LogUtils.insert("安全天数更新:" + deptDO.getDeptname(), "updateEvent", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), switchA + "-" + switchB + "-" + switchC + "-" + switchD);
//            } else {
//                return new Result(CODE.ERROR, null, "昨日数据已经更新！");
//            }
//            return new Result(CODE.SUCCESS, null, "更新成功！");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Result(CODE.ERROR, null, "服务器内部错误！");
//        }
//    }
    @ApiOperation(value = "安全天数更新")
    @PutMapping(value = "/updateEvent")
    @ResponseBody
    public Result updateEvent(long aDay, long bDay, long cDay, long dDay, String username) {
        try {
            UserLoginDo userDO = (UserLoginDo) RedisUtil.hmget(username, UserLoginDo.class);
            DeptDO deptDO = deptService.selectDeptById(userDO.getDeptid());
            SafetyDayDo safetyDayDo = safetyDayService.existToday(userDO.getDeptid());
            if (safetyDayDo == null) {
                safetyDayService.updateEvent(aDay, bDay, cDay, dDay, userDO.getDeptid());
                LogUtils.insert("安全天数更新:" + deptDO.getDeptname(), "updateEvent", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), aDay + "-" + bDay + "-" + cDay + "-" + dDay);
            } else {
                return new Result(CODE.ERROR, null, "昨日数据已经更新！");
            }
            return new Result(CODE.SUCCESS, null, "更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "服务器内部错误！");
        }
    }

    @ApiOperation(value = "获取安全天数")
    @GetMapping(value = "/getSafetyToday")
    @ResponseBody
    public Result getSafetyToday(String username) {
        try {
            UserLoginDo userDO = (UserLoginDo) RedisUtil.hmget(username, UserLoginDo.class);
            String deptcode = deptService.selectDeptById(userDO.getDeptid()).getDeptcode();
            SafetyDayDo safetyDayDo = new SafetyDayDo();
            if (deptcode.length() == 2) {
                safetyDayDo = safetyDayService.getSafetyToday(userDO.getDeptid());
            } else {
                if (deptcode.substring(0, 4).equals("0001")) {
                    safetyDayDo = safetyDayService.getSafetyToday(1);
                } else {
                    safetyDayDo = safetyDayService.getSafetyToday(userDO.getDeptid());
                }
            }
            return new Result(CODE.SUCCESS, safetyDayDo, "更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "服务器内部错误！");
        }
    }


}
