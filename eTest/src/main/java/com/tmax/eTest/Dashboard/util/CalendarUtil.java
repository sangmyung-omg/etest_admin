package com.tmax.eTest.Dashboard.util;

import java.util.Calendar;

public class CalendarUtil {
    private static Calendar increaseByThursday(int year, int month, int day) {
        int dayOfWeek;
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(year, month - 1, day);

        int currentWeek = calendar.get(Calendar.WEEK_OF_MONTH);
        int maximumWeek = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

        if (currentWeek == maximumWeek){
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (Calendar.SUNDAY < dayOfWeek && dayOfWeek < Calendar.THURSDAY)
                calendar.add(Calendar.DATE, 1);
        }
        else if (currentWeek == 1){
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (!(Calendar.SUNDAY < dayOfWeek && dayOfWeek < Calendar.FRIDAY))
                calendar.set(Calendar.DATE, -1);
        }
        return calendar;
    }

    public static String getCurrentWeekOfMonth(int year, int month, int day)  {
        int weekIncrease;
        Calendar calendar = increaseByThursday(year, month, day);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int currentWeek = calendar.get(Calendar.WEEK_OF_MONTH);

        calendar.set(Calendar.DATE, 1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if ((Calendar.MONDAY <= dayOfWeek) && (dayOfWeek <= Calendar.THURSDAY))
            weekIncrease = 0;
        else
            weekIncrease = -1;

        return (calendar.get(Calendar.MONTH) + 1) + "월" + " " + (currentWeek + weekIncrease) + "주차";
    }
}