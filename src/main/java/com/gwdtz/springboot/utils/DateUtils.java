package com.gwdtz.springboot.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @program: springboot
 * @Date: 2021-2-25 16:28
 * @Author: Miss.Chenmf
 * @Description:
 */
public class DateUtils {
    public static TimeZone tz=TimeZone.getTimeZone("Asia/Shanghai");
    public static SimpleDateFormat sdfDate0=new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdfDate1=new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat sdfDate2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sdfDate3=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    public static SimpleDateFormat sdfDate4=new SimpleDateFormat("MM-dd HH:mm");
    public static SimpleDateFormat sdfDate5=new SimpleDateFormat("yyMMddHHmmss");
    public static SimpleDateFormat sdfDate6=new SimpleDateFormat("HHmm");//用于零点任务，时间是0点0*分时
    public static SimpleDateFormat sdfDate7=new SimpleDateFormat("dd");//获取天数
    public static SimpleDateFormat sdfDate8=new SimpleDateFormat("HH");//获取当前小时
    public static SimpleDateFormat sdfDate9=new SimpleDateFormat("yyyy年MM月dd日 E" );
    public static SimpleDateFormat sdfDate10=new SimpleDateFormat("yyyy年MM月dd日" );
    public static SimpleDateFormat sdfDate11=new SimpleDateFormat("yyyyMMddHHmmss" );
    public static SimpleDateFormat sdfDate12=new SimpleDateFormat("yyyyMM");
    public static SimpleDateFormat sdfDate13=new SimpleDateFormat("yyyyMMdd HHmmss" );
    public static SimpleDateFormat sdfDate14=new SimpleDateFormat("yyyyMMdd HHmm" );
    public static SimpleDateFormat sdfDate15=new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss" );
    public static SimpleDateFormat sdfDate16=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat sdfDate17=new SimpleDateFormat("MM月dd日");
    public static SimpleDateFormat sdfDate18=new SimpleDateFormat("yyyyMMddHHmm" );
    public static SimpleDateFormat sdfDate19=new SimpleDateFormat("MM" );
    public static SimpleDateFormat sdfDate20=new SimpleDateFormat("yyyyMM" );
    public static SimpleDateFormat sdfDate21=new SimpleDateFormat("yyyy年MM月" );

    public DateUtils(){
        sdfDate0.setTimeZone(tz);
        sdfDate1.setTimeZone(tz);
        sdfDate2.setTimeZone(tz);
        sdfDate3.setTimeZone(tz);
        sdfDate4.setTimeZone(tz);
        sdfDate5.setTimeZone(tz);
        sdfDate6.setTimeZone(tz);
        sdfDate7.setTimeZone(tz);
        sdfDate8.setTimeZone(tz);
        sdfDate9.setTimeZone(tz);
        sdfDate10.setTimeZone(tz);
        sdfDate11.setTimeZone(tz);
        sdfDate12.setTimeZone(tz);
        sdfDate13.setTimeZone(tz);
        sdfDate14.setTimeZone(tz);
        sdfDate15.setTimeZone(tz);
        sdfDate16.setTimeZone(tz);
        sdfDate17.setTimeZone(tz);
        sdfDate18.setTimeZone(tz);
        sdfDate19.setTimeZone(tz);
        sdfDate20.setTimeZone(tz);
        sdfDate21.setTimeZone(tz);
    }
    //输入日期格式YYYY-mm-dd,及begin、end返回对应的时间Long的toString
    public static String getTimeInMillis(String strDate,String strStatus){
        if(strDate.equals("") || strDate.isEmpty() || strDate==null)
            return "0";
        String[] strTemp=strDate.split("-",0);
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.clear();
        if(strStatus.equals("begin"))
            cal.set(Integer.valueOf(strTemp[0]),(Integer.valueOf(strTemp[1])-1), Integer.valueOf(strTemp[2]),0,0,0);
        if(strStatus.equals("end"))
            cal.set(Integer.valueOf(strTemp[0]),(Integer.valueOf(strTemp[1])-1), Integer.valueOf(strTemp[2]),23,59,59);
        return String.valueOf(cal.getTimeInMillis());
    }
    public static Date getDate(SimpleDateFormat sdf,String time){
        //ParsePosition pp=new ParsePosition(0);
        time=time.trim();
        try{
            return sdf.parse(time);
        }
        catch(Exception ex){
            return null;
        }

    }

    //根据sdfDat0获得sdfDat1字符串
    public static String getTimeInFormat1(String strDate){
        if(strDate.equals("") || strDate.isEmpty() || strDate==null)
            return "0";
        String[] strTemp=strDate.split("-",0);
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.clear();
        cal.set(Integer.valueOf(strTemp[0]),(Integer.valueOf(strTemp[1])-1), Integer.valueOf(strTemp[2]),23,59,59);
        return sdfDate1.format(cal.getTime());
    }
    //根据sdfDat1获得sdfDat0字符串
    public static String getTimeInFormat(String strDate){
        if(strDate.equals("") || strDate.isEmpty() || strDate==null || strDate.length()!=8)
            return "0";
        String[] strTemp=new String[3];
        strTemp[0]=strDate.substring(0,4);
        strTemp[1]=strDate.substring(4,6);
        strTemp[2]=strDate.substring(6,8);
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.clear();
        cal.set(Integer.valueOf(strTemp[0]),(Integer.valueOf(strTemp[1])-1), Integer.valueOf(strTemp[2]),23,59,59);
        return sdfDate0.format(cal.getTime());
    }
    public static String getFormatCalendar(String strTimeMillis){
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.setTimeInMillis(Long.valueOf(strTimeMillis));
        return sdfDate0.format(cal.getTime());
    }
    public static String getFormatCalendarByModel(SimpleDateFormat model,String strTimeMillis){
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.setTimeInMillis(Long.valueOf(strTimeMillis));
        return model.format(cal.getTime());
    }
    //根据Date值，返回本月第一天
    public static Date getMinOfCurrentMonth(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
    //根据Date值，返回本月最后一天
    public static Date getMaxOfCurrentMonth(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
    //根据Date值，返回下月第一天
    public static Date getMinOfNextMonth(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.setTime(date);
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
    //根据Date值，返回下月最后一天
    public static Date getMaxOfNextMonth(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);//必须先把日期设置为1号，否则，如果本月30天，下月31天，月份加一后就会出现下下月
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /*
     * 获得当前日期的下一天
     *
     * */
    public static String getNextDate(String format) {//yyyy-MM-dd或者yyyyMMdd
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        String s_date=sdf.format(c.getTime());
        return s_date;
    }
    public static String getBeforeDate(String format) {//yyyy-MM-dd或者yyyyMMdd
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        String s_date=sdf.format(c.getTime());
        return s_date;
    }
    /*
     * 根据整型的year  month  day  返回 Date值
     * added by LiuYang
     *
     */
    public static Date getDate(int year,int month,int days)
    {
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        calendar.set(year,month-1,days,0,0,0);
        return calendar.getTime();
    }
    //传入SimpleDateFormat，string,int(加减天数)，返回SimpleDateFormat格式的时间字符串
    public static String getStringOfOffsetDate(SimpleDateFormat sdf,String date,long offset){
        long temp=getDate(sdf,date).getTime();
        return sdf.format(new Date(temp+offset*86400*1000));
    }
    //传入SimpleDateFormat，string，返回用SimpleDateFormat格式的Date
    public static Date getDateFromSimpleDateFormatString(SimpleDateFormat sdf,String date){
        return getDate(sdf,date);
    }
    public static void main(String[] args){
        Date now=new Date();
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
        System.out.println(sdfDate2.format(cal.getTime()));
        System.out.println(Calendar.DAY_OF_MONTH);
        System.out.println(cal.getMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("当前日期："+sdfDate3.format(now));
        System.out.println("本月第一天："+sdfDate3.format(getMinOfCurrentMonth(now)));
        System.out.println("本月最后一天："+sdfDate3.format(getMaxOfCurrentMonth(now)));
        System.out.println("下月第一天："+sdfDate3.format(getMinOfNextMonth(now)));
        System.out.println("下月最后一天："+sdfDate3.format(getMaxOfNextMonth(now)));
    }

    public static String getCurrDateStr() {
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);

        return sdfDate2.format(cal.getTime());
    }
    public static String getCurDateStr() {
        Calendar cal=Calendar.getInstance();
        return sdfDate2.format(cal.getTime());
    }
    //added by jiangyulin
    public static String getCurrDate(){
        return (new java.sql.Date(System.currentTimeMillis())+"").replaceAll("-", "");
    }

    //added by jiangyulin
    public static String getCurrDate(int addDay){
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        calendar.add(Calendar.DATE, addDay);
        return (new java.sql.Date(calendar.getTimeInMillis())+"").replaceAll("-", "");
    }
    //added by hanlei计算两个日期之间的天数
    public static long getQuot(String time1, String time2){
        long quot = 0;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = ft.parse( time1 );
            Date date2 = ft.parse( time2 );
            quot = date1.getTime() - date2.getTime();
            quot = quot / 1000 / 60 / 60 / 24;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quot;
    }




    /**
     * @description "2099-12-30"
     * @author Eric (Oct 17, 2011)
     * @version Change History:
     * @version  <1> Oct 17, 2011 Eric  Modification purpose.
     */
    public static Date getMaxDate(){
        return getDate(sdfDate0,"2099-12-30");
    }

    /**
     * @title: getMinDate
     * @description: 最小时间
     * @return
     * @version <1> Eric  18 May 2012  create this method
     */
    public static Date getMinDate(){
        return getDate(sdfDate0,"1970-12-30");
    }
    public static String getCurrDateStrOfYYYMMDDHHMMSS(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getCurrDateStrOfYYYYMMDD() {
        return getDateStrOfYYYYMMDD(new java.util.Date());
    }

    public static String getCurrDateStrOfYYYYMM() {
        String currDate = getDateStrOfYYYYMMDD(new java.util.Date());
        return currDate.substring(0, 6);
    }

    public static String getDateStrOfYYYYMMDD(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        return sdf.format(date);
    }

    /**
     * @author zb
     * @param date:20120313
     * @return
     */
    public static String getDateStrOfYYYY_MM_DD(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    public static String getDateStrOfYYYMMDDHHMMSS(Date date){
        if(date==null)
            return null;
        return sdfDate2.format(date);
    }

    /*------------------------------liujiaxin20121214-----------------------------------------------------------*/
    public static String getDateStrOfYYYYMMDDHHMMSS() {
        Calendar cal=Calendar.getInstance();
        return sdfDate11.format(cal.getTime());
    }
    /*------------------------------liujiaxin20121214-----------------------------------------------------------*/

    public static Date getdate(String date){
        Date d = null;

        try{
            if(date!=null&&date.length()>19)
                date = date.substring(0,19);

            if(date!=null&&date.length()==19){
                d = DateUtils.sdfDate2.parse(date);
            }else if(date!=null&&date.length()==13){
                d = DateUtils.sdfDate14.parse(date);
            }else if(date!=null&&date.length()==16){
                d = DateUtils.sdfDate16.parse(date);
            }else if(date!=null&&date.length()==17){
                d = DateUtils.sdfDate3.parse(date);
            }else if(date!=null&&date.length()==14){
                d = DateUtils.sdfDate11.parse(date);
            }else if(date!=null&&date.length()==8){
                d = DateUtils.sdfDate1.parse(date);
            }else if(date!=null&&date.length()==10){
                d = DateUtils.sdfDate0.parse(date);
            }
        }catch(Exception e){
            d = null;
        }

        return d;
    }

    public static int getCurHour(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.setTime(date==null?new Date():date);

        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static Date addDate(Date date, int field, int amount){
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        cal.setTime(date);
        cal.add(field, amount);

        return cal.getTime();
    }

    public static Date getCurDate(){
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(tz);
        return cal.getTime();
    }
    public static String date2String(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date_str = "";
        try {
            date_str = sdf.format(date);
        } catch (Exception e) {
        }
        return date_str;
    }
    public static boolean compareDate(Date date1,Date date2){
        boolean bl=false;
        double d=date1.getTime()-date2.getTime();
        if(d>0){
            return true;
        }
        return bl;
    }

    // 获取下月的今天
    public static Date getCurDayOfNextMonth(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(DateUtils.tz);
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }


}
