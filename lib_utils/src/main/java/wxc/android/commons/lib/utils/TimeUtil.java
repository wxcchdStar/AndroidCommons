package wxc.android.commons.lib.utils;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

    private static final DateFormat DATE_FORMAT_MONTH_DAY_YEAR_IN_EN = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);

    public static String formatDate(long timestamp) {
        Date time = new Date(timestamp);
        return DATE_FORMAT_MONTH_DAY_YEAR_IN_EN.format(time);
    }

    private static long toDay(long millis, boolean ignoreTimeZone) {
        return (millis + (ignoreTimeZone ? 0 : TimeZone.getDefault().getOffset(millis))) / DateUtils.DAY_IN_MILLIS;
    }

    public static long toHour24(long millis, boolean ignoreTimeZone) {
        return (millis + (ignoreTimeZone ? 0 : TimeZone.getDefault().getOffset(millis))) / DateUtils.HOUR_IN_MILLIS % 24;
    }

    public static long toMinute60(long millis, boolean ignoreTimeZone) {
        return (millis + (ignoreTimeZone ? 0 : TimeZone.getDefault().getOffset(millis))) / DateUtils.MINUTE_IN_MILLIS % 60;
    }
}