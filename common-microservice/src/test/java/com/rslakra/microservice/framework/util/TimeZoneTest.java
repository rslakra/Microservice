package com.rslakra.microservice.framework.util;

import org.junit.jupiter.api.Test;

/**
 * @author Rohtash Lakra
 * @created 7/31/23 11:27 AM
 */
public class TimeZoneTest {

    @Test
    public void testPrintTimezones() {
        TimeZone timeZone = new TimeZone();
        timeZone.printTimeZones();
    }

}
