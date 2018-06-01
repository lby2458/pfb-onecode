package pfb.onecode.api.util;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * 时间类型转换
 * 
 * @author：sl 2017年12月7日
 */
public class DateUtil {
	private static final Logger logger = Logger.getLogger(DateUtil.class);
	public static final String CH_DATE_FORMAT = "yyyy年MM月dd日";
	public static final String EN_DATE_FORMAT = "yyyy-MM-dd";
	public static final String EN_LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将String类型的时间串按照给定的格式解析为Date
	 *
	 * @param dateStr
	 *            待解析的时间串
	 * @param format
	 *            时间串解析的格式
	 * @return
	 */
	public static Date stringToDate(String dateStr, String format) {
		if (dateStr == null || dateStr.equals("")) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 将Date类型的时间对象按照给定的格式格式化为String
	 *
	 * @param date
	 *            时间对象
	 * @param format
	 *            格式
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateTemp = null;
		try {
			dateTemp = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTemp;
	}
	/**
	 * 查看当天是当月第几天
	 * @return
	 */
	public static int getDayOfMonth(){
		Calendar c = Calendar.getInstance();
		return c.get(c.DAY_OF_MONTH);
	}
	/**
	 * 获取当月一共有多少天
	 * @return
	 */
	public static int getDaysOfCurrentMonth(){
		Calendar c = Calendar.getInstance();
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 获取指定日期的月一共有多少天
	 * @param date
	 * @return
	 */
	public static int getDaysOfDateMonth(Date date){
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		return now.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 返回当前时间距离当天24点的毫秒值
	 * 
	 * @return
	 */
	public static long getLockTime() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis() - new Date().getTime();
	}

	/**
	 * @category 比较两个日期大小
	 * @param DATE1
	 * @param DATE2
	 * @param pattern
	 * @return 1：前面日期大于后面日期 -1前面日期小于后面日期 0 相等
	 */
	public static int compare_date(String DATE1, String DATE2, String pattern) {

		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd";
		}
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	/**
	 * 1：前面日期大于后面日期 -1前面日期小于后面日期 0 相等
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compare_date(Date date1,Date date2){
		try {
			if (date1.getTime() > date2.getTime()) {
				return 1;
			} else if (date1.getTime() < date2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	// 当前日期是否在该日期内
	public static boolean compareDate(Date startTime, Date endTime) {
		long time = System.currentTimeMillis();
		try {
			if (time > startTime.getTime() && time < endTime.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

	}

	/**
	 * 根据当前日期计算几天后的日期
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
	/**
	 * 获取当前时间偏移月数的时间
	 * @param date
	 * @param offsetMonths
	 * @return
	 */
	public static Date getOffsetMonthDate(Date date,int offsetMonths){
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + offsetMonths);
		return now.getTime();
	}
	/**
	 * 根据当前日期计算几个月前的日期
	 * 
	 * @param month
	 * @return
	 */
	public static Date getDateBefore(int month) {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.MONTH, now.get(Calendar.MONTH)-month);
		return now.getTime();
	}

	/**
	 * 日期与当前日期做比较
	 */
	public static boolean compareDate(String time) {
		boolean flag = true;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = df.parse(time);
			if (date1.before(new Date())) {
				flag = false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 日期格式转换
	 */
	public static Date dateToDate(Date dateStr) {
		if (dateStr == null) {
			return null;
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf1.format(dateStr);
		date = date + " 23:59:59";
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = null;
		try {
			time = sdf2.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 获取两个日期之间的天数
	 */
	public static int getDaySpace(Date date1) {
		if (date1 == null || "".equals(date1))
			return 0;
		Date date = new Date();
		int day = 0;
		if (date.getTime() > date1.getTime()) {
			long aa = date.getTime() - date1.getTime();
			System.out.println(aa + "-----------");
			day = (int) (aa / (1000 * 60 * 60 * 24));
			day += 1;
		}
		return day;
	}


	/**
	 * 获取当前日期计算几天前的日期
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(now.getTime()) + " 00:00:00";
		return date;
	}

	// 获取当前日期的下一个工作日
	public static Date nextWorkDate() {
		Date date;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime();

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 6) {
			calendar.setTime(date);
			calendar.add(calendar.DATE, 2);
			date = calendar.getTime();
		}
		if (dayOfWeek == 0) {
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);
			date = calendar.getTime();
		}
		return date;
	}
	/**
	 * 获取上一个工作日 如果今天就是工作日 那么就是今天
	 */
	public static Date preWorkDate(){
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		while(dayOfWeek == 6 || dayOfWeek == 0){
			c.setTime(date);
			c.add(c.DATE, -1);
			date = c.getTime();
		}
		return date;
	}
	
	// 获取当前日期的第N个工作日
	public static Date nextNWorkDate(int day) {
		Date date;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime();

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 6) {
			calendar.setTime(date);
			calendar.add(calendar.DATE, 2);
			date = calendar.getTime();
		}
		if (dayOfWeek == 0) {
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);
			date = calendar.getTime();
		}
		return date;
	}

	// 获取当前时间的上一小时(YYYY-MM-dd HH:mm:dd)
	public static Date getBeforeHourTimeDate(int h) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)
				- h);

		return calendar.getTime();

	}

	// 获取当前日期跟当天21点的毫秒数
	public static int getMinusMillis() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int minute = calendar.get(Calendar.MINUTE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int second = calendar.get(Calendar.SECOND);
		int x = (17 * 60 * 60 - hour * 60 * 60 - minute * 60 - second) * 1000;

		return x;
	}

	// 获取给定日期的前一天
	public static String beforeDate(String fitTime) {
		if ("".equals(fitTime))
			return fitTime;
		String time = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(fitTime);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(date);

			gc.add(5, -1); // 2表示月的加减，年代表1依次类推(３周....5天。。)
			// 把运算完的时间从新赋进对象
			gc.set(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DATE));
			// 在格式化回字符串时间
			time = sdf.format(gc.getTime());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}


	/**
	 * 获取指定时间前/后 days天
	 * 
	 * @param days
	 * @param patter
	 * @return
	 */
	public static String getDateByDays(int days, String patter, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(patter);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return sdf.format(calendar.getTime());
	}


	/**
	 * 获取时间和当前时间之间的毫秒数
	 * @param timeStr
	 * @return
	 */
	public static long getExpireTime(String timeStr) {
		Date date = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time = date.getTime() - new Date().getTime();
		return time;
	}

	/**
	 * 获取当前指定格式的美东时间
	 * @param timeFormat
	 * @return
	 */
	public static String getFormatUSATime(String timeFormat){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return sdf.format(date);
	}
	/**
	 * 获取months个月之后的最后一天23:59:59
	 * @param months
	 * @return
	 */
	public static Date getNextXMonthLastDate(int months){
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+months+1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.DATE, calendar.get(calendar.DATE)-1);
		return calendar.getTime();
	}
	/**
	 * 判断当前日期是否是当月最后一天
	 * @return
	 */
	public static boolean isLastDayOfMonth(){
		Calendar calendar = Calendar.getInstance();  
		int date = calendar.get(Calendar.DATE);
		if(date == calendar.getActualMaximum(Calendar.DATE)){
			return true;
		}
		return false;
	}
	/**
	 * 判断当前日期是否是当月第一天
	 * @return
	 */
	public static boolean isFirstDayOfMonth(){
		Calendar calendar = Calendar.getInstance();  
		int date = calendar.get(Calendar.DATE);
		if(date == 1){
			return true;
		}
		return false;
	}

	/**
	 * 得到此刻的时间format
	 * @return
	 */
	public static String getTodayFormat(String format){

		if ("".equals(format)){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		return new SimpleDateFormat(format).format(new Date());
	}
}
