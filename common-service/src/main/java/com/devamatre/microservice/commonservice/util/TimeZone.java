package com.devamatre.microservice.commonservice.util;

import lombok.Getter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Rohtash Lakra
 * @created 7/31/23 11:12 AM
 */
@Getter
public class TimeZone implements Comparable<TimeZone> {

    private String zoneId;
    private String zoneOffset;
    private String shortName;
    private Map<String, TimeZone> timeZones;
    private Map<String, String> allTimeZones;

    public TimeZone() {
        Instant instant = Instant.now();
        allTimeZones = ZoneId.getAvailableZoneIds()
            .stream()
            .map(ZoneId::of)
            .map(zoneId -> new TimeZone(zoneId.toString(), instant.atZone(zoneId)))
//            .sorted(comparingByValue().reversed())
            .collect(Collectors.toMap(
                TimeZone::getZoneId,
                TimeZone::getZoneOffset,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new));
    }

    /**
     *
     */
    public void printTimeZones() {
        System.out.println("\nNumber of Zone IDs:" + allTimeZones.size());
        allTimeZones.forEach((key, value) -> System.out.printf(String.format("%35s %s %n", key, value)));
    }

    /**
     * @param zoneId
     * @param zonedDateTime
     */
    public TimeZone(String zoneId, ZonedDateTime zonedDateTime) {
        this.zoneId = zoneId;
        this.zoneOffset = zonedDateTime.getOffset().getId().replaceAll("Z", "+00:00");
    }

    /**
     * Compares this object with the specified object for order.  Returns a negative integer, zero, or a positive
     * integer as this object is less than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))} for all {@code x} and {@code y}.  (This implies that
     * {@code x.compareTo(y)} must throw an exception iff {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any class that implements the
     * {@code Comparable} interface and violates this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it from being compared to this object.
     */
    @Override
    public int compareTo(TimeZone other) {
        return getZoneOffset().compareTo(other.getZoneOffset());
    }
}
