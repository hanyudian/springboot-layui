package com.gwdtz.springboot.controller.front;

import com.gwdtz.springboot.entity.*;
import com.gwdtz.springboot.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: springboot
 * @Date: 2021-2-22 11:34
 * @Author: Miss.Chenmf
 * @Description:
 */
@Api(description = "前台展示首页")
@RequestMapping(value = "/front")
@RestController
public class FrontController {

    @Autowired
    IModuleIconService moduleIconService;

    @Autowired
    IModuleManageUrlService moduleManageUrlService;

    @ApiOperation(value = "icon字典列表")
    @GetMapping(value = "/selectModuleIconList")
    @ResponseBody
    private Map<String, Object> selectModuleIconList() {
        Map<String, Object> mapobj = new HashMap<String, Object>();
        List<ModuleIconDo> moduleIconList = moduleIconService.selectModuleIconList();
        mapobj.put("moduleIconList", moduleIconList);
        return mapobj;
    }

//    @ApiOperation(value = "栏目管理页面字典列表")
//    @GetMapping(value = "/selectModuleManageUrlList")
//    @ResponseBody
//    private Map<String, Object> selectModuleManageUrlList() {
//        Map<String, Object> mapobj = new HashMap<String, Object>();
//        List<ModuleManageUrlDO> moduleManageUrlList= moduleManageUrlService.selectModuleManageUrlList();
//        mapobj.put("moduleManageUrlList",moduleManageUrlList);
//        return mapobj;
//    }
}
