package proj;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by beatrizgomes on 28/03/18.
 */
public class Date implements Comparable<Date> {
    public int day;
    public int month;
    public int year;
    public int hour;
    public int minute;
    public int diferenca = 30;

    public Date() {
        Calendar gregCalendar = new GregorianCalendar();
        this.day = gregCalendar.get(Calendar.DAY_OF_MONTH);
        this.month = gregCalendar.get(Calendar.MONTH) + 1;
        this.year = gregCalendar.get(Calendar.YEAR);
        this.hour = gregCalendar.get(Calendar.HOUR_OF_DAY);
        this.minute = gregCalendar.get(Calendar.MINUTE);
    }

    public Date(int day, int month, int year, int hour, int minute) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * @param d a given date
     * @return true if this date is before d date, false otherwise
     */
    public boolean beforeDate(Date d) {
        if (this.year < d.year) {
            return true;
        }
        if (this.year == d.year) {
            if (this.month < d.month) {
                return true;
            }
            if (this.month == d.month) {
                if (this.day < d.day)
                    return true;
                if (this.day == d.day) {
                    if (this.hour < d.hour)
                        return true;
                    if (this.hour == d.hour)
                        return this.minute < d.minute;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getMinute() + "/" + this.getHour() + "/" + this.getDay() + "/" + this.getMonth() + "/" + this.getYear();
    }

    /**
     * @param d a given date
     * @return returns 0 if this date is equal to d date, -1 if this date is
     * before d date or 1 if this date is after d date
     */
    public int compareTo(Date d) {
        if (this.year == d.getYear() && this.month == d.month && this.day == d.getDay() && this.hour == d.hour && this.minute == d.minute) {
            return 0;
        }
        return this.beforeDate(d) ? -1 : 1;
    }

    /**
     * @param d a given date
     * @return true if this date is after d date, false otherwise
     */
    public boolean afterDate(Date d) {
        if (this.year == d.year && this.month == d.month && this.day == d.day && this.hour == d.hour && this.minute == d.minute)
            return false;

        return !beforeDate(d);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}

