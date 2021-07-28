package com.gwdtz.springboot.task;

import com.gwdtz.springboot.entity.SafetyDayDo;
import com.gwdtz.springboot.entity.ScheduleDetailDo;
import com.gwdtz.springboot.entity.ScheduleDo;
import com.gwdtz.springboot.service.ILogService;
import com.gwdtz.springboot.service.IScheduleAttachService;
import com.gwdtz.springboot.service.IScheduleDetailService;
import com.gwdtz.springboot.service.IScheduleService;
import com.gwdtz.springboot.utils.DateQuarterUtil;
import com.gwdtz.springboot.utils.ScheduleUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;
import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ScheduleTask
 * @Description
 * @Author hanshuai(the Developing of Four)
 * @Date 2021/7/5 16:42
 * @Version 1.0
 **/
@Component
public class ScheduleTask {

    @Autowired
    IScheduleService scheduleService1;

    @Autowired
    IScheduleAttachService scheduleAttachService1;

    @Autowired
    IScheduleDetailService scheduleDetailService1;

    private static IScheduleService scheduleService;

    private static IScheduleAttachService scheduleAttachService;

    private static IScheduleDetailService scheduleDetailService;

    @PostConstruct
    public void init() {
        scheduleService = scheduleService1;
        scheduleAttachService = scheduleAttachService1;
        scheduleDetailService = scheduleDetailService1;
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void updateScheduleDetail() throws Exception {
        System.err.println("日程提醒开始自动更新。。。。。。");
        List<ScheduleDo> all = scheduleService.getAll();
        for (ScheduleDo scheduleDo :
                all) {
            if (scheduleDo.getOpen() == 0) {
                continue;
            } else {
                ScheduleDetailDo scheduleDetailDo = scheduleDetailService.getScheduleDetailByScheduleId(scheduleDo.getId());
                Date now = new Date();
                switch (scheduleDo.getType()) {
                    case 0:
                        break;
                    case 1:
                        String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
                        String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
                        String second = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
                        String nowTime = hour + ":" + minute + ":" + second;
                        if (scheduleDetailDo == null) {
                            if (ScheduleUtils.compTime(scheduleDo.getTime(), nowTime)) {
                                ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo2.setDate(ScheduleUtils.getDateByString());
                                scheduleDetailDo2.setTime(scheduleDo.getTime());
                                scheduleDetailDo2.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo2);
                            } else {

                            }
                        } else {
                            if (ScheduleUtils.isThisDay(scheduleDetailDo.getDate())) {

                            } else {
                                if (ScheduleUtils.compTime(scheduleDo.getTime(), nowTime)) {
                                    ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                    scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                    scheduleDetailDo2.setDate(ScheduleUtils.getDateByString());
                                    scheduleDetailDo2.setTime(scheduleDo.getTime());
                                    scheduleDetailDo2.setFlag(0);
                                    scheduleDetailService.insertSelective(scheduleDetailDo2);
                                } else {

                                }
                            }
                        }
                        break;
                    case 2:
                        if (scheduleDetailDo == null) {
                            if (scheduleDo.getWeek() > ScheduleUtils.getWeek()) {
                                int size = scheduleDo.getWeek() - ScheduleUtils.getWeek();
                                ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo2.setDate(ScheduleUtils.getAfterNumDay(size));
                                scheduleDetailDo2.setTime(scheduleDo.getTime());
                                scheduleDetailDo2.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo2);
                            } else if (scheduleDo.getWeek() == ScheduleUtils.getWeek()) {
                                if (scheduleDo.getTime().compareTo(new SimpleDateFormat("HH:mm:ss").format(now)) >= 0) {
                                    ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                    scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                    scheduleDetailDo2.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                                    scheduleDetailDo2.setTime(scheduleDo.getTime());
                                    scheduleDetailDo2.setFlag(0);
                                    scheduleDetailService.insertSelective(scheduleDetailDo2);
                                } else {

                                }
                            } else {

                            }
                        } else {
                            if (ScheduleUtils.isThisWeek(scheduleDetailDo.getDate())) {

                            } else {
                                ScheduleDetailDo scheduleDetailDo1 = new ScheduleDetailDo();
                                String firstofWeek = ScheduleUtils.getFirstofWeek();
                                String afterNumDay = ScheduleUtils.getAfterNumDay(firstofWeek, scheduleDo.getWeek());
                                scheduleDetailDo1.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo1.setDate(afterNumDay);
                                scheduleDetailDo1.setTime(scheduleDo.getTime());
                                scheduleDetailDo1.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo1);
                            }
                        }
                        break;
                    case 3:
                        if (scheduleDetailDo == null) {
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
                                ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo2.setDate(date);
                                scheduleDetailDo2.setTime(scheduleDo.getTime());
                                scheduleDetailDo2.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo2);
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
                                    ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                    scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                    scheduleDetailDo2.setDate(date);
                                    scheduleDetailDo2.setTime(scheduleDo.getTime());
                                    scheduleDetailDo2.setFlag(0);
                                    scheduleDetailService.insertSelective(scheduleDetailDo2);
                                } else {

                                }
                            } else {

                            }
                        } else {
                            if (ScheduleUtils.isThisMonth(scheduleDetailDo.getDate())) {

                            } else {
                                int year = ScheduleUtils.getYear();
                                int month = ScheduleUtils.getMonth();
                                String day = "";
                                if (scheduleDo.getDay() >= 1 && scheduleDo.getDay() <= 9) {
                                    day += "0" + scheduleDo.getDay();
                                } else {
                                    day += scheduleDo.getDay();
                                }
                                String date = year + "-" + month + "-" + day;
                                ScheduleDetailDo scheduleDetailDo1 = new ScheduleDetailDo();
                                scheduleDetailDo1.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo1.setDate(date);
                                scheduleDetailDo1.setTime(scheduleDo.getTime());
                                scheduleDetailDo1.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo1);
                            }
                        }
                        break;
                    case 4:
                        if (scheduleDetailDo == null) {
                            if (ScheduleUtils.getMonthInQuarter() < scheduleDo.getQuarter()) {
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
                                ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo2.setDate(date);
                                scheduleDetailDo2.setTime(scheduleDo.getTime());
                                scheduleDetailDo2.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo2);
                            } else if (ScheduleUtils.getMonthInQuarter() == scheduleDo.getQuarter()) {
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
                                    ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                    scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                    scheduleDetailDo2.setDate(date);
                                    scheduleDetailDo2.setTime(scheduleDo.getTime());
                                    scheduleDetailDo2.setFlag(0);
                                    scheduleDetailService.insertSelective(scheduleDetailDo2);
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
                                        ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                        scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                        scheduleDetailDo2.setDate(date);
                                        scheduleDetailDo2.setTime(scheduleDo.getTime());
                                        scheduleDetailDo2.setFlag(0);
                                        scheduleDetailService.insertSelective(scheduleDetailDo2);
                                    } else {

                                    }
                                }
                            } else {

                            }
                        } else {
                            if (ScheduleUtils.isThisQuarter(scheduleDetailDo.getDate())) {

                            } else {
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
                                ScheduleDetailDo scheduleDetailDo1 = new ScheduleDetailDo();
                                scheduleDetailDo1.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo1.setDate(date);
                                scheduleDetailDo1.setTime(scheduleDo.getTime());
                                scheduleDetailDo1.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo1);
                            }
                        }
                        break;
                    case 5:
                        if (scheduleDetailDo == null) {
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
                                ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo2.setDate(date);
                                scheduleDetailDo2.setTime(scheduleDo.getTime());
                                scheduleDetailDo2.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo2);
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
                                    ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                    scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                    scheduleDetailDo2.setDate(date);
                                    scheduleDetailDo2.setTime(scheduleDo.getTime());
                                    scheduleDetailDo2.setFlag(0);
                                    scheduleDetailService.insertSelective(scheduleDetailDo2);
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
                                        ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                        scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                        scheduleDetailDo2.setDate(date);
                                        scheduleDetailDo2.setTime(scheduleDo.getTime());
                                        scheduleDetailDo2.setFlag(0);
                                        scheduleDetailService.insertSelective(scheduleDetailDo2);
                                    } else {

                                    }
                                } else {

                                }
                            } else {

                            }
                        } else {
                            if (ScheduleUtils.isThisYear(scheduleDetailDo.getDate())) {

                            } else {
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
                                ScheduleDetailDo scheduleDetailDo1 = new ScheduleDetailDo();
                                scheduleDetailDo1.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo1.setDate(date);
                                scheduleDetailDo1.setTime(scheduleDo.getTime());
                                scheduleDetailDo1.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo1);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void updateScheduleDetailByScheduleId(long id) throws Exception {
        System.err.println("日程提醒开始自动更新。。。。。。");
        ScheduleDo scheduleDo = scheduleService.selectScheduleById(id);
        if (scheduleDo.getOpen() == 0) {

        } else {
            ScheduleDetailDo scheduleDetailDo = scheduleDetailService.getScheduleDetailByScheduleId(scheduleDo.getId());
            Date now = new Date();
            switch (scheduleDo.getType()) {
                case 0:
                    break;
                case 1:
                    String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
                    String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
                    String second = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
                    String nowTime = hour + ":" + minute + ":" + second;
                    if (scheduleDetailDo == null) {
                        if (ScheduleUtils.compTime(scheduleDo.getTime(), nowTime)) {
                            ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                            scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                            scheduleDetailDo2.setDate(ScheduleUtils.getDateByString());
                            scheduleDetailDo2.setTime(scheduleDo.getTime());
                            scheduleDetailDo2.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo2);
                        } else {

                        }
                    } else {
                        if (ScheduleUtils.isThisDay(scheduleDetailDo.getDate())) {

                        } else {
                            if (ScheduleUtils.compTime(scheduleDo.getTime(), nowTime)) {
                                ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo2.setDate(ScheduleUtils.getDateByString());
                                scheduleDetailDo2.setTime(scheduleDo.getTime());
                                scheduleDetailDo2.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo2);
                            } else {

                            }
                        }
                    }
                    break;
                case 2:
                    if (scheduleDetailDo == null) {
                        if (scheduleDo.getWeek() > ScheduleUtils.getWeek()) {
                            int size = scheduleDo.getWeek() - ScheduleUtils.getWeek();
                            ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                            scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                            scheduleDetailDo2.setDate(ScheduleUtils.getAfterNumDay(size));
                            scheduleDetailDo2.setTime(scheduleDo.getTime());
                            scheduleDetailDo2.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo2);
                        } else if (scheduleDo.getWeek() == ScheduleUtils.getWeek()) {
                            if (scheduleDo.getTime().compareTo(new SimpleDateFormat("HH:mm:ss").format(now)) >= 0) {
                                ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo2.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                                scheduleDetailDo2.setTime(scheduleDo.getTime());
                                scheduleDetailDo2.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo2);
                            } else {

                            }
                        } else {

                        }
                    } else {
                        if (ScheduleUtils.isThisWeek(scheduleDetailDo.getDate())) {

                        } else {
                            ScheduleDetailDo scheduleDetailDo1 = new ScheduleDetailDo();
                            String firstofWeek = ScheduleUtils.getFirstofWeek();
                            String afterNumDay = ScheduleUtils.getAfterNumDay(firstofWeek, scheduleDo.getWeek());
                            scheduleDetailDo1.setScheduleid(scheduleDo.getId());
                            scheduleDetailDo1.setDate(afterNumDay);
                            scheduleDetailDo1.setTime(scheduleDo.getTime());
                            scheduleDetailDo1.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo1);
                        }
                    }
                    break;
                case 3:
                    if (scheduleDetailDo == null) {
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
                            ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                            scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                            scheduleDetailDo2.setDate(date);
                            scheduleDetailDo2.setTime(scheduleDo.getTime());
                            scheduleDetailDo2.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo2);
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
                                ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo2.setDate(date);
                                scheduleDetailDo2.setTime(scheduleDo.getTime());
                                scheduleDetailDo2.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo2);
                            } else {

                            }
                        } else {

                        }
                    } else {
                        if (ScheduleUtils.isThisMonth(scheduleDetailDo.getDate())) {

                        } else {
                            int year = ScheduleUtils.getYear();
                            int month = ScheduleUtils.getMonth();
                            String day = "";
                            if (scheduleDo.getDay() >= 1 && scheduleDo.getDay() <= 9) {
                                day += "0" + scheduleDo.getDay();
                            } else {
                                day += scheduleDo.getDay();
                            }
                            String date = year + "-" + month + "-" + day;
                            ScheduleDetailDo scheduleDetailDo1 = new ScheduleDetailDo();
                            scheduleDetailDo1.setScheduleid(scheduleDo.getId());
                            scheduleDetailDo1.setDate(date);
                            scheduleDetailDo1.setTime(scheduleDo.getTime());
                            scheduleDetailDo1.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo1);
                        }
                    }
                    break;
                case 4:
                    if (scheduleDetailDo == null) {
                        if (ScheduleUtils.getMonthInQuarter() < scheduleDo.getQuarter()) {
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
                            ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                            scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                            scheduleDetailDo2.setDate(date);
                            scheduleDetailDo2.setTime(scheduleDo.getTime());
                            scheduleDetailDo2.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo2);
                        } else if (ScheduleUtils.getMonthInQuarter() == scheduleDo.getQuarter()) {
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
                                ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo2.setDate(date);
                                scheduleDetailDo2.setTime(scheduleDo.getTime());
                                scheduleDetailDo2.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo2);
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
                                    ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                    scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                    scheduleDetailDo2.setDate(date);
                                    scheduleDetailDo2.setTime(scheduleDo.getTime());
                                    scheduleDetailDo2.setFlag(0);
                                    scheduleDetailService.insertSelective(scheduleDetailDo2);
                                } else {

                                }
                            }
                        } else {

                        }
                    } else {
                        if (ScheduleUtils.isThisQuarter(scheduleDetailDo.getDate())) {

                        } else {
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
                            ScheduleDetailDo scheduleDetailDo1 = new ScheduleDetailDo();
                            scheduleDetailDo1.setScheduleid(scheduleDo.getId());
                            scheduleDetailDo1.setDate(date);
                            scheduleDetailDo1.setTime(scheduleDo.getTime());
                            scheduleDetailDo1.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo1);
                        }
                    }
                    break;
                case 5:
                    if (scheduleDetailDo == null) {
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
                            ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                            scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                            scheduleDetailDo2.setDate(date);
                            scheduleDetailDo2.setTime(scheduleDo.getTime());
                            scheduleDetailDo2.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo2);
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
                                ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                scheduleDetailDo2.setDate(date);
                                scheduleDetailDo2.setTime(scheduleDo.getTime());
                                scheduleDetailDo2.setFlag(0);
                                scheduleDetailService.insertSelective(scheduleDetailDo2);
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
                                    ScheduleDetailDo scheduleDetailDo2 = new ScheduleDetailDo();
                                    scheduleDetailDo2.setScheduleid(scheduleDo.getId());
                                    scheduleDetailDo2.setDate(date);
                                    scheduleDetailDo2.setTime(scheduleDo.getTime());
                                    scheduleDetailDo2.setFlag(0);
                                    scheduleDetailService.insertSelective(scheduleDetailDo2);
                                } else {

                                }
                            } else {

                            }
                        } else {

                        }
                    } else {
                        if (ScheduleUtils.isThisYear(scheduleDetailDo.getDate())) {

                        } else {
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
                            ScheduleDetailDo scheduleDetailDo1 = new ScheduleDetailDo();
                            scheduleDetailDo1.setScheduleid(scheduleDo.getId());
                            scheduleDetailDo1.setDate(date);
                            scheduleDetailDo1.setTime(scheduleDo.getTime());
                            scheduleDetailDo1.setFlag(0);
                            scheduleDetailService.insertSelective(scheduleDetailDo1);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}



