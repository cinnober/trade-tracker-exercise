/*
 * Copyright (c) 2013 Cinnober Financial Technology AB, Stockholm,
 * Sweden. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Cinnober Financial Technology AB, Stockholm, Sweden. You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Cinnober.
 *
 * Cinnober makes no representations or warranties about the suitability
 * of the software, either expressed or implied, including, but not limited
 * to, the implied warranties of merchantibility, fitness for a particular
 * purpose, or non-infringement. Cinnober shall not be liable for any
 * damages suffered by licensee as a result of using, modifying, or
 * distributing this software or its derivatives.
 */
package com.cinnober.tradetracker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Instant;

/**
 * This is a utility calss to handle date functions.
 * 
 * @author suresh
 *
 */
public class DateTimeUtil {

	/**
	 * @return the system time in millis
	 */
	public static long getCurrentTimeStamp() {
		return System.currentTimeMillis();
	}

	/**
	 * @return the system date in epoch (days since 1970-01-01)
	 */
	public static int getCurrentSystemDate() {
		DateTime dateTime = new DateTime(System.currentTimeMillis());
		Instant epoch = new Instant(0);
		Days daysSinceEpoch = Days.daysBetween(epoch, dateTime);
		return daysSinceEpoch.getDays();
	}
	
	/**
	 * @param Days since epoch
	 * @return the date string
	 */
	public static String getDateString(int daysSinceEpoch) {
        LocalDate date = LocalDate.ofEpochDay(daysSinceEpoch);
        return date.toString();
    }
    
	/**
	 * @param timestamp in millis
	 * @return the time stamp in "yyyy-MM-dd HH:mm:ss.SSS" format
	 */
	public static String getDateTimeString(long timestamp) {
		DateTime dt = new DateTime(timestamp);
		return dt.toString("yyyy-MM-dd HH:mm:ss.SSS");
    }
	
	/**
	 * @param date in epoch (days since 1970-01-01)
	 * @return the time stamp in millis. Appends the system time to the input date.
	 */
	public static long getTimeStamp(int date) {
		try {
			DateTime systemDate = new DateTime(System.currentTimeMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date businessDate = (Date) format.parse(getDateString(date));

			Calendar cal = new GregorianCalendar();
			cal.setTime(businessDate);
			cal.set(Calendar.HOUR, systemDate.getHourOfDay());
			cal.set(Calendar.MINUTE, systemDate.getMinuteOfHour());
			cal.set(Calendar.SECOND, systemDate.getSecondOfMinute());
			cal.set(Calendar.MILLISECOND, systemDate.getMillisOfSecond());
			return cal.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis();
	}
	
	/**
	 * @param date
	 *            in String (i.e. yyyy-MM-dd)
	 * @return the epoch date (days since 1970-01-01)
	 */
	public static int getEpochDate(String date) {
		LocalDate localDate = LocalDate.parse(date);
		return (int) localDate.toEpochDay();
	}
}
