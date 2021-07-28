package com.gwdtz.springboot.task;

import com.gwdtz.springboot.entity.SafetyDayDo;
import com.gwdtz.springboot.service.ISafetyDayService;
import com.gwdtz.springboot.utils.CODE;
import com.gwdtz.springboot.utils.LogUtils;
import com.gwdtz.springboot.utils.Result;
import com.gwdtz.springboot.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @ClassName SafetyDayTask
 * @Description
 * @Author hanshuai(the Developing of Four)
 * @Date 2021/6/30 9:21
 * @Version 1.0
 **/
@Component
public class SafetyDayTask {

    @Autowired
    ISafetyDayService safetyDayService;

    @Scheduled(cron = "0 55 23 * * ?")
    public void updateSafetyDay() {
        System.err.println("========================");
        List<SafetyDayDo> all = safetyDayService.getAll();
        for (SafetyDayDo s :
                all) {
            Integer deptid =  s.getDeptid().intValue();
            SafetyDayDo safetyDayDo = safetyDayService.existYesterday(deptid);
            if (safetyDayDo == null) {
                safetyDayDo = safetyDayService.getSafetyToday(deptid);
                safetyDayService.updateEvent(safetyDayDo.getAday() + 1, safetyDayDo.getBday()+1, safetyDayDo.getCday()+1, safetyDayDo.getDday()+1, Math.toIntExact(deptid));
//                LogUtils.insert("安全天数到期自动更新", "updateSafetyDay", null, "系统定时任务", "");
            }
        }
    }
}
