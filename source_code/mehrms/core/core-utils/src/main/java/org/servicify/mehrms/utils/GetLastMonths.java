package org.servicify.mehrms.utils;

import org.servicify.mehrms.model.MonthDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
//获取从某个日期开始之前的数个月份
public class GetLastMonths {
//    测试该方法是否能够运行
//    public static void main(String[] args)throws Exception{
//        List<MonthDate> list=getLastMonths(new Date(), 12);
//        for(int i=0;i<list.size();i++){
//        }
//    }
//    通过传入的截至日期和所需查询的月数参数，生成对应的月数范围内的每月开始日期与结束日期组合的日期集合
    public static List<MonthDate> getLastMonths(Date date, Integer integer)throws Exception {
//        创建ArrayList
        List<MonthDate> monthDateList=new ArrayList<MonthDate>();
//        将date的日期传给cal，并设置时分秒为0；避免出现带某一天的时间或者不带某天的时间
        Calendar cal = Calendar.getInstance();
//        将传过来的date参数设置日期为当月1号
        cal.set(Calendar.DATE, 1);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
//        创建时间被转化为字符串格式的接受变量
        String dateString;
//        创建将时间转化为字符串类型的变量
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//        由于直接倒退的话，不包括传递进来的时间的当月。故integer-1，减少倒退一次
        for (int i = 0; i < integer-1; i++) {
            cal.add(Calendar.MONTH, -1);
        }
        for (int i = 0; i < integer; i++) {
            MonthDate monthDate=new MonthDate();
            dateString = sdf.format(cal.getTime());
//            截取字符串中0至6的值
            monthDate.setYearAndMonth(dateString.substring(0, 7));
//            获取当月的第一天和最后一天,赋值至另外一个变量，避免数据被脏读
            Calendar calendar = cal;
//            设置该月的第一天
            calendar.set(Calendar.DATE, 1);
            Date startDate=getDateFromCal(calendar.getTime());
            monthDate.setStartDate(startDate);
//            通过roll回滚至本月的最后一天
            calendar.roll(Calendar.DATE, -1);
            Date endDate=getDateFromCal(calendar.getTime());
            monthDate.setEndDate(endDate);
//            添加进List集合
            monthDateList.add(monthDate);
//            将当前的月份向前减1
            cal.add(Calendar.MONTH, 1);
        }
        return monthDateList;
    }
//  将日期转换为yyyy-MM-dd格式的字符串
    public static Date getDateFromCal(Date date) throws Exception{
//        创建将时间的字符串类型变量转换为Date类型的变量
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
        String dateString= dateformat.format(date);
        Date date1 = dateformat.parse(dateString);//将formate型转化成Date数据类型
        return date1;
    }
}
