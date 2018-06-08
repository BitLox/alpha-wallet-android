package io.stormbird.token.util;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

/*
 * by Weiwu, 2018. Modeled after Java8's ZonedDateTime, intended to be
 * replaced by Java8's ZonedDateTime as soon as Android 8.0 gets popular
 */
public class ZonedDateTime {
    private long time;
    private int offset;
    private TimeZone timezone;

    /* For anyone deleting this class to use Java8 ZonedDateTime:
     *
     * A LITTLE DEVIL LIES IN THE DETAIL HERE
     *
     * In Java8, ZonedDateTime.of(LocalDateTime time, ZoneID id) works
     * by taking the year, month, day, hour, minute, second tuple from
     * LocalDateTime, stripping off the timezone information, then
     * treat it as if the tuple is in ZoneID. i.e. no timezone offset applied
     * unless later toEpochSecond() is used.
     *
     * In this ZonedDateTime which uses a constructor instead of a
     * static of() method, unixTime always represent Unix Time, that
     * is, the number of seconds since Epoch as the Epoch happens at UTC.
     * Apparently timezone offset is applied in format()
     */
    public ZonedDateTime(long unixTime, TimeZone timezone) {
        this.time = unixTime * 1000L;
        this.timezone = timezone;
        this.offset = timezone.getOffset(time);
    }

    /* EVERY FUNCTION BELOW ARE SET OUT IN JAVA8 */

    public long toEpochSecond() {
        return time/1000L;
    }

    public int getHour() {
        /* experiment shows getHours start with 1, not 0. */
        return new Date(time + offset).getHours() - 1;
    }

    public int getMinute() {
        return new Date(time + offset).getMinutes();
    }

    public String format(DateFormat format) {
        format.setTimeZone(timezone);
        return format.format(new Date(time));
    }
}
