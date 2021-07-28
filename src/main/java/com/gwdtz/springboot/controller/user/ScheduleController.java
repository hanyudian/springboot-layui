package com.gwdtz.springboot.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gwdtz.springboot.entity.*;
import com.gwdtz.springboot.entity.customer.ScheduleNameIdCount;
import com.gwdtz.springboot.service.IModuleService;
import com.gwdtz.springboot.service.IScheduleAttachService;
import com.gwdtz.springboot.service.IScheduleDetailService;
import com.gwdtz.springboot.service.IScheduleService;
import com.gwdtz.springboot.task.ScheduleTask;
import com.gwdtz.springboot.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Sides;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @ClassName ScheduleController
 * @Description
 * @Author hanshuai(the Developing of Four)
 * @Date 2021/7/1 11:07
 * @Version 1.0
 **/
@Api(description = "日程提醒")
@RequestMapping("/schedule")
@RestController
public class ScheduleController {

    @Autowired
    IScheduleService scheduleService;

    @Autowired
    IScheduleAttachService scheduleAttachService;

    @Autowired
    IScheduleDetailService scheduleDetailService;

    @Autowired
    IModuleService moduleService;

    @ApiOperation(value = "日程管理(单个，用于编辑页面的查询)")
    @GetMapping(value = "/selectScheduleById")
    @ResponseBody
    private Map<String, Object> selectScheduleById(HttpServletRequest request) {
        Map<String, Object> mapobj = new HashMap<String, Object>();
        try {
            ScheduleDo scheduleDo = scheduleService.selectScheduleById(Long.valueOf(request.getParameter("id")));
            if (scheduleDo.getDate() != null && scheduleDo.getTime() != null) {
                scheduleDo.setCalltime(scheduleDo.getDate() + " " + scheduleDo.getTime());
            }
            List<ScheduleAttachDo> attachlist = scheduleAttachService.getScheduleAttachList(scheduleDo.getAttachids().split(","));

            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            String strattachlist = "";
            for (ScheduleAttachDo l : attachlist) {
                l.setAttachabsoluteurl(filePath + l.getAttachrelativeurl());
                strattachlist += l.getAttachrealname() + "," + l.getAttachrelativeurl() + "," + l.getAttachtype() + "," + l.getAttachsize() + "|";
            }
            mapobj.put("scheduleDo", scheduleDo);
            mapobj.put("attachlist", attachlist);
            mapobj.put("strattachlist", strattachlist);
            mapobj.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            mapobj.put("success", false);
        }
        return mapobj;
    }

    @ApiOperation(value = "日程管理", notes = "日程管理")
    @GetMapping(value = "/getScheduleList")
    @ResponseBody
    public Map<String, Object> getScheduleList(HttpServletRequest request, HttpServletResponse response) {
        UserLoginDo userLoginDo = UserUtils.userGet(request);
        String strkey = request.getParameter("strkey");
        String persontype = request.getParameter("persontype");
        String date = ScheduleUtils.getDateByString();
        Map<String, Object> mapobj = new HashMap<String, Object>();
        PageInfo pageInfo = null;
        PageHelper.startPage(request);
        List<ScheduleDo> list = scheduleService.getScheduleList(userLoginDo.getUserid(), persontype, strkey);
        List<Integer> finishList = new ArrayList<>();
        for (ScheduleDo s :
                list) {
            int count = scheduleDetailService.getUnFinishScheduleCount(s.getId(), date);
            finishList.add(count);
        }
        pageInfo = new PageInfo(list);
        mapobj.put("pageList", pageInfo.getList());
        mapobj.put("pageCount", pageInfo.getTotal());
        mapobj.put("finishList", finishList);
        return mapobj;
    }

    @ApiOperation(value = "日程新增")
    @PostMapping(value = "/scheduleAdd")
    @ResponseBody
    public Result scheduleAdd(ScheduleDo scheduleDo, HttpServletRequest request) throws Exception {
        UserLoginDo userLoginDo = UserUtils.userGet(request);
        scheduleDo.setUserid(Long.valueOf(userLoginDo.getUserid()));
        try {
            String jsonArray = scheduleDo.getJsonArray();
            StringBuilder stringBuilder = new StringBuilder();
            if (!jsonArray.equals("")) {
                jsonArray = jsonArray.substring(0, jsonArray.length() - 1);
                String[] jsonArrays = jsonArray.split("\\|");
                for (int i = 0; i < jsonArrays.length; i++) {
                    ScheduleAttachDo scheduleAttachDo = new ScheduleAttachDo();
                    String[] objs = jsonArrays[i].split(",");
                    scheduleAttachDo.setAttachrealname(objs[0]);
                    scheduleAttachDo.setAttachrelativeurl(objs[1]);
                    scheduleAttachDo.setAttachtype(objs[2]);
                    scheduleAttachDo.setAttachsize(Double.valueOf(objs[3]));
                    scheduleAttachService.insertSelective(scheduleAttachDo);
                    stringBuilder.append(scheduleAttachDo.getId() + ",");
                }
            }
            scheduleDo.setAttachids(String.valueOf(stringBuilder));
            if (scheduleDo.getCalltime().length() == 19) {
                scheduleDo.setDate(scheduleDo.getCalltime().split("\\s+")[0]);
                scheduleDo.setTime(scheduleDo.getCalltime().split("\\s+")[1]);
            } else {
                scheduleDo.setTime(scheduleDo.getCalltime());
            }
            scheduleService.insertSelective(scheduleDo);

            ScheduleDetailDo scheduleDetailDo = new ScheduleDetailDo();
            scheduleDetailDo.setScheduleid(scheduleDo.getId());
            Date now = new Date();
            switch (scheduleDo.getType()) {
                case 0:
                    scheduleDetailDo.setDate(scheduleDo.getDate());
                    scheduleDetailDo.setTime(scheduleDo.getTime());
                    scheduleDetailDo.setFlag(0);
                    scheduleDetailService.insertSelective(scheduleDetailDo);
                    break;
                case 1:
                    if (scheduleDo.getTime().compareTo(new SimpleDateFormat("HH:mm:ss").format(now)) <= 0) {

                    } else {
                        scheduleDetailDo.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        scheduleDetailDo.setTime(scheduleDo.getTime());
                        scheduleDetailDo.setFlag(0);
                        scheduleDetailService.insertSelective(scheduleDetailDo);
                    }
                    break;
                case 2:
                    if (scheduleDo.getWeek() > ScheduleUtils.getWeek()) {
                        int size = scheduleDo.getWeek() - ScheduleUtils.getWeek();
                        scheduleDetailDo.setDate(ScheduleUtils.getAfterNumDay(size));
                        scheduleDetailDo.setTime(scheduleDo.getTime());
                        scheduleDetailDo.setFlag(0);
                        scheduleDetailService.insertSelective(scheduleDetailDo);
                    } else if (scheduleDo.getWeek() == ScheduleUtils.getWeek()) {
                        if (scheduleDo.getTime().compareTo(new SimpleDateFormat("HH:mm:ss").format(now)) >= 0) {
                            scheduleDetailDo.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                            scheduleDetailDo.setTime(scheduleDo.getTime());
                            scheduleDetailDo.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo);
                        } else {

                        }
                    } else {

                    }

                    break;
                case 3:
                    if (scheduleDo.getDay() > ScheduleUtils.getDay()) {
                        int year = ScheduleUtils.getYear();
                        int month = ScheduleUtils.getMonth();
                        String day = "";
                        if (scheduleDo.getDay() >= 1 && scheduleDo.getDay() <= 9) {
                            day += "0" + scheduleDo.getDay();
                        } else {
                            day += scheduleDo.getDay();
                        }
                        String date = year + "-" + month + "-" + day;
                        scheduleDetailDo.setDate(date);
                        scheduleDetailDo.setTime(scheduleDo.getTime());
                        scheduleDetailDo.setFlag(0);
                        scheduleDetailService.insertSelective(scheduleDetailDo);
                    } else if (scheduleDo.getDay() == ScheduleUtils.getDay()) {
                        if (scheduleDo.getTime().compareTo(new SimpleDateFormat("HH:mm:ss").format(now)) >= 0) {
                            int year = ScheduleUtils.getYear();
                            int month = ScheduleUtils.getMonth();
                            String day = "";
                            if (scheduleDo.getDay() >= 1 && scheduleDo.getDay() <= 9) {
                                day += "0" + scheduleDo.getDay();
                            } else {
                                day += scheduleDo.getDay();
                            }
                            String date = year + "-" + month + "-" + day;
                            scheduleDetailDo.setDate(date);
                            scheduleDetailDo.setTime(scheduleDo.getTime());
                            scheduleDetailDo.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo);
                        } else {

                        }
                    } else {

                    }
                    break;
                case 4:
                    int monthInQuarter = ScheduleUtils.getMonthInQuarter();
                    if (monthInQuarter < scheduleDo.getQuarter()) {
                        int year = ScheduleUtils.getYear();
                        int season = DateQuarterUtil.getSeason(new Date());
                        String month = "";
                        if ((season - 1) * 3 + scheduleDo.getQuarter() >= 1 && (season - 1) * 3 + scheduleDo.getQuarter() <= 9) {
                            month += "0" + ((season - 1) * 3 + scheduleDo.getQuarter());
                        } else {
                            month += ((season - 1) * 3 + scheduleDo.getQuarter());
                        }
                        String day = "";
                        if (scheduleDo.getDay() >= 1 && scheduleDo.getDay() <= 9) {
                            day += "0" + scheduleDo.getDay();
                        } else {
                            day += scheduleDo.getDay();
                        }
                        String date = year + "-" + month + "-" + day;
                        scheduleDetailDo.setDate(date);
                        scheduleDetailDo.setTime(scheduleDo.getTime());
                        scheduleDetailDo.setFlag(0);
                        scheduleDetailService.insertSelective(scheduleDetailDo);
                    } else if (monthInQuarter == scheduleDo.getQuarter()) {
                        if (scheduleDo.getDay() > ScheduleUtils.getDay()) {
                            int year = ScheduleUtils.getYear();
                            int season = DateQuarterUtil.getSeason(new Date());
                            String month = "";
                            if ((season - 1) * 3 + scheduleDo.getQuarter() >= 1 && (season - 1) * 3 + scheduleDo.getQuarter() <= 9) {
                                month += "0" + ((season - 1) * 3 + scheduleDo.getQuarter());
                            } else {
                                month += ((season - 1) * 3 + scheduleDo.getQuarter());
                            }
                            String day = "";
                            if (scheduleDo.getDay() >= 1 && scheduleDo.getDay() <= 9) {
                                day += "0" + scheduleDo.getDay();
                            } else {
                                day += scheduleDo.getDay();
                            }
                            String date = year + "-" + month + "-" + day;
                            scheduleDetailDo.setDate(date);
                            scheduleDetailDo.setTime(scheduleDo.getTime());
                            scheduleDetailDo.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo);
                        } else if (scheduleDo.getDay() == ScheduleUtils.getDay()) {
                            if (scheduleDo.getTime().compareTo(new SimpleDateFormat("HH:mm:ss").format(now)) >= 0) {
                                int year = ScheduleUtils.getYear();
                                int season = DateQuarterUtil.getSeason(new Date());
                                String month = "";
                                if ((season - 1) * 3 + scheduleDo.getQuarter() >= 1 && (season - 1) * 3 + scheduleDo.getQuarter() <= 9) {
                                    month += "0" + ((season - 1) * 3 + scheduleDo.getQuarter());
                                } else {
                                    month += ((season - 1) * 3 + scheduleDo.getQuarter());
                                }
                                String day = "";
                                if (scheduleDo.getDay() >= 1 && scheduleDo.getDay() <= 9) {
                                    day += "0" + scheduleDo.getDay();
                                } else {
                                    day += scheduleDo.getDay();
                                }
                                String date = year + "-" + month + "-" + day;
                                scheduleDetailDo.setDate(date);
                                scheduleDetailDo.setTime(scheduleDo.getTime());
                                scheduleDetailDo.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo);
                            } else {

                            }
                        }
                    } else {

                    }
                    break;
                case 5:
                    if (scheduleDo.getYear() > ScheduleUtils.getMonth()) {
                        int year = ScheduleUtils.getYear();
                        String month = "";
                        if (scheduleDo.getYear() >= 1 && scheduleDo.getYear() <= 9) {
                            month += "0" + scheduleDo.getYear();
                        } else {
                            month += scheduleDo.getYear();
                        }
                        String day = "";
                        if (scheduleDo.getDay() >= 1 && scheduleDo.getDay() <= 9) {
                            day += "0" + scheduleDo.getDay();
                        } else {
                            day += scheduleDo.getDay();
                        }
                        String date = year + "-" + month + "-" + day;
                        scheduleDetailDo.setDate(date);
                        scheduleDetailDo.setTime(scheduleDo.getTime());
                        scheduleDetailDo.setFlag(0);
                        scheduleDetailService.insertSelective(scheduleDetailDo);
                    } else if (scheduleDo.getYear() == ScheduleUtils.getMonth()) {
                        if (scheduleDo.getDay() > ScheduleUtils.getDay()) {
                            int year = ScheduleUtils.getYear();
                            String month = "";
                            if (scheduleDo.getYear() >= 1 && scheduleDo.getYear() <= 9) {
                                month += "0" + scheduleDo.getYear();
                            } else {
                                month += scheduleDo.getYear();
                            }
                            String day = "";
                            if (scheduleDo.getDay() >= 1 && scheduleDo.getDay() <= 9) {
                                day += "0" + scheduleDo.getDay();
                            } else {
                                day += scheduleDo.getDay();
                            }
                            String date = year + "-" + month + "-" + day;
                            scheduleDetailDo.setDate(date);
                            scheduleDetailDo.setTime(scheduleDo.getTime());
                            scheduleDetailDo.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo);
                        } else if (scheduleDo.getDay() == ScheduleUtils.getDay()) {
                            if (scheduleDo.getTime().compareTo(new SimpleDateFormat("HH:mm:ss").format(now)) >= 0) {
                                int year = ScheduleUtils.getYear();
                                String month = "";
                                if (scheduleDo.getYear() >= 1 && scheduleDo.getYear() <= 9) {
                                    month += "0" + scheduleDo.getYear();
                                } else {
                                    month += scheduleDo.getYear();
                                }
                                String day = "";
                                if (scheduleDo.getDay() >= 1 && scheduleDo.getDay() <= 9) {
                                    day += "0" + scheduleDo.getDay();
                                } else {
                                    day += scheduleDo.getDay();
                                }
                                String date = year + "-" + month + "-" + day;
                                scheduleDetailDo.setDate(date);
                                scheduleDetailDo.setTime(scheduleDo.getTime());
                                scheduleDetailDo.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo);
                            } else {

                            }
                        } else {

                        }
                    } else {

                    }
                    break;
            }

            return new Result(CODE.SUCCESS, null, "插入成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "插入失败！");
        }
    }

    @ApiOperation(value = "日程删除")
    @DeleteMapping(value = "/deleteScheduleById")
    public Result deleteScheduleById(long id) {
        try {
            ScheduleDo scheduleDo = scheduleService.selectScheduleById(id);
            scheduleAttachService.deleteScheduleAttachByIds(scheduleDo.getAttachids().split(","));
            scheduleDetailService.deleteScheduleDetailById(id);
            scheduleService.deleteScheduleById(id);
            return new Result(CODE.SUCCESS, null, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "删除失败！");
        }
    }

    @ApiOperation(value = "日程附件删除")
    @DeleteMapping(value = "/deleteByAttachId")
    public Result deleteByAttachId(String attachid) {
        try {
            Integer num = scheduleAttachService.deleteByAttachId(attachid);
            return new Result(CODE.SUCCESS, num, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "删除失败！");
        }
    }

    @ApiOperation(value = "日程开启与关闭")
    @PutMapping(value = "/scheduleEdit")
    @ResponseBody
    public Result scheduleEdit(long id, int open, HttpServletRequest request) throws Exception {
        ScheduleDo scheduleDo = scheduleService.selectScheduleById(id);
        scheduleDo.setOpen(open);
        scheduleService.updateByPrimaryKeySelective(scheduleDo);
        if (open == 0) {
            scheduleDetailService.deleteScheduleDetailByScheduleId(id);
            return new Result(CODE.SUCCESS, null, "该日程已关闭！");
        } else {
            ScheduleTask scheduleTask = new ScheduleTask();
            scheduleTask.updateScheduleDetailByScheduleId(id);
            return new Result(CODE.SUCCESS, null, "该日程已开启！");
        }
    }

    @ApiOperation(value = "日程附件内容(单个，用于编辑页面的查询)")
    @GetMapping(value = "/selectScheduleDetailById")
    @ResponseBody
    private Map<String, Object> selectScheduleDetailById(HttpServletRequest request) {
        Map<String, Object> mapobj = new HashMap<String, Object>();
        try {
            ScheduleDo scheduleDo = scheduleService.selectScheduleById(Long.valueOf(request.getParameter("id")));
            List<ScheduleAttachDo> scheduleAttachList = scheduleAttachService.getScheduleAttachList(scheduleDo.getAttachids().split(","));
            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            String strattachlist = "";
            for (ScheduleAttachDo l : scheduleAttachList) {
                l.setAttachabsoluteurl(filePath + l.getAttachrelativeurl());
                strattachlist += l.getAttachrealname() + "," + l.getAttachrelativeurl() + "," + l.getAttachtype() + "," + l.getAttachsize() + "|";
            }
            mapobj.put("scheduleDo", scheduleDo);
            mapobj.put("attachlist", scheduleAttachList);
            mapobj.put("strattachlist", strattachlist);
            mapobj.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            mapobj.put("success", false);
        }
        return mapobj;
    }

    @ApiOperation(value = "获取当日未完成的日程")
    @GetMapping(value = "/getUnFinishSchedule")
    @ResponseBody
    private Map<String, Object> getUnFinishSchedule(HttpServletRequest request) {
        Map<String, Object> mapobj = new HashMap<String, Object>();
        try {
            UserLoginDo userLoginDo = UserUtils.userGet(request);
            List<ModuleDO> moduleDOList = moduleService.getModuleListByCode("000115");
            int total = 0;
            List<ScheduleNameIdCount> list = new ArrayList<>();
            for (ModuleDO m :
                    moduleDOList) {
                ScheduleNameIdCount scheduleNameIdCount =new ScheduleNameIdCount();
                scheduleNameIdCount.setName(m.getTitle());
                Integer persontype = Integer.valueOf(m.getHref().split("=")[1]);
                scheduleNameIdCount.setPersontype(persontype);
                List<ScheduleDo> scheduleDoList = scheduleService.getScheduleListByPersonType(userLoginDo.getUserid(),persontype);
                StrBuilder strBuilder = new StrBuilder();
                for (ScheduleDo s :
                        scheduleDoList) {
                    strBuilder.append(s.getId() + ",");
                }
                Integer count = scheduleDetailService.getUnFinishScheduleCountByScheduleIds(strBuilder.toString().split(","), ScheduleUtils.getDateByString());
                total += count;
                scheduleNameIdCount.setCount(count);
                list.add(scheduleNameIdCount);
            }

            mapobj.put("list", list);
            mapobj.put("total", total);
            mapobj.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            mapobj.put("success", false);
        }
        return mapobj;
    }

    @ApiOperation(value = "单个日程未完成的所有日程相关信息")
    @GetMapping(value = "/getUnFinishScheduleListByScheduleId")
    @ResponseBody
    public Map<String, Object> getUnFinishScheduleListByScheduleId(HttpServletRequest request, HttpServletResponse response) {
        long scheduleid = Long.parseLong(request.getParameter("scheduleid"));
        String date = ScheduleUtils.getDateByString();
        Map<String, Object> mapobj = new HashMap<String, Object>();
        PageInfo pageInfo = null;
        PageHelper.startPage(request);
        List<ScheduleDetailDo> unFinishSchedule = scheduleDetailService.getUnFinishSchedule(scheduleid, date);
        pageInfo = new PageInfo(unFinishSchedule);
        mapobj.put("pageList", pageInfo.getList());
        mapobj.put("pageCount", pageInfo.getTotal());
        return mapobj;
    }

    @ApiOperation(value = "日程未完成状态改变")
    @PutMapping(value = "/scheduleFinishFlag")
    @ResponseBody
    public Result scheduleFinishFlag(long id, int flag, HttpServletRequest request) throws Exception {
        ScheduleDetailDo scheduleDetailDo = scheduleDetailService.getScheduleDetailById(id);
        if (flag == 0) {
            return new Result(CODE.SUCCESS, null, "该日程今日已完成！");
        } else {
            scheduleDetailService.updateScheduleFinishFlag(id);
            return new Result(CODE.SUCCESS, null, "该日程" + scheduleDetailDo.getDate() + "已完成！");
        }
    }
}
