package com.gwdtz.springboot.controller.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gwdtz.springboot.entity.LogDO;
import com.gwdtz.springboot.service.ILogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: springboot
 * @Date: 2021-2-25 10:00
 * @Author: Miss.Yanjc
 * @Description:
 */
@Api(description = "日志管理")
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    ILogService logService;

    @ApiOperation(value = "日志管理", notes = "notes")
    @GetMapping(value = "/getLogList")
    @ResponseBody
    public Map<String, Object> getLogList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> mapobj = new HashMap<String, Object>();
        PageInfo pageInfo = null;
        PageHelper.startPage(request);
        List<LogDO> list = logService.getLogList(request.getParameter("name"),request.getParameter("ip"),request.getParameter("operation"),request.getParameter("time"));
        pageInfo = new PageInfo(list);
        mapobj.put("code", 0);
        mapobj.put("msg", "成功！");
        mapobj.put("data", pageInfo.getList());
        mapobj.put("count", pageInfo.getTotal());
        return mapobj;

    }

}
