package com.rslakra.microservice.framework;

import static com.rslakra.microservice.framework.CommonUtils.asIntegerPercentage;
import static com.rslakra.microservice.framework.CommonUtils.deserialize;
import static com.rslakra.microservice.framework.CommonUtils.validateSerialNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rslakra.microservice.framework.exception.DeserializationException;
import com.rslakra.microservice.framework.exception.InvalidValueException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 7/25/23 5:08 PM
 */
public class CommonUtilsTest {

    // create known time interval (30 minutes)
    private static final long TEST_DURATION_MINUTES = 30;
    private static final LocalDateTime TEST_START_DATE_TIME =
        LocalDateTime.of(2020, 10, 30, 12, 00);
    private static final LocalDateTime TEST_END_DATE_TIME = TEST_START_DATE_TIME.plusMinutes(TEST_DURATION_MINUTES);

    // known distance between the following two lat/lon pairs (based on existing python app calc)
    private static final double TEST_DISTANCE = 48.31d;
    private static final double TEST_START_LAT = 40.58901;
    private static final double TEST_START_LON = -74.4754;
    private static final double TEST_END_LAT = 40.73061;
    private static final double TEST_END_LON = -73.935242;

    /**
     *
     */
    @Test
    public void calculateDistance_shouldReturnTheDistanceBetweenTwoPoints() {
        double testDistance = CommonUtils.calculateDistance(TEST_START_LAT, TEST_START_LON, TEST_END_LAT, TEST_END_LON);
        assertEquals(TEST_DISTANCE, testDistance);
    }

    /**
     *
     */
    @Test
    public void calculateDurationMinutes_shouldReturnTheMinutesBetweenTwoTimestamps() {
        double minutes = CommonUtils.calculateDurationMinutes(TEST_START_DATE_TIME, TEST_END_DATE_TIME);
        assertEquals(Double.valueOf(TEST_DURATION_MINUTES), minutes);
    }

    /**
     * @throws InvalidValueException
     */
    @Test
    public void calculateVelocity_shouldReturnTheVelocityBetweenTwoPoints() throws InvalidValueException {
        double speed = TEST_DISTANCE / (TEST_DURATION_MINUTES / 60d);
        double calculatedVelocity =
            CommonUtils.calculateVelocity(TEST_START_LAT, TEST_START_LON, TEST_START_DATE_TIME, TEST_END_LAT,
                                          TEST_END_LON,
                                          TEST_END_DATE_TIME);
        assertEquals(speed, calculatedVelocity);
    }

    /**
     * @throws InvalidValueException
     */
    @Test
    public void convertLatToDouble_shouldReturnTheDoubleValue_ifTheValueIsValid() throws InvalidValueException {
        String latStr = "45.5";
        Double latitude = 45.5d;
        assertEquals(latitude, CommonUtils.asDoubleLatitude(latStr, "testing error!"));
    }

    /**
     *
     */
    @Test
    public void convertLatToDouble_shouldThrowAnException_ifTheValueIsNotADouble() {
        assertThrows(InvalidValueException.class, () -> CommonUtils.asDoubleLatitude("garbage", "error!"));
    }

    /**
     *
     */
    @Test
    public void convertLatToDouble_shouldThrowAnException_ifTheValueOutsideTheValidRange() {
        assertThrows(InvalidValueException.class, () -> CommonUtils.asDoubleLatitude("-94", "error!"));
        assertThrows(InvalidValueException.class, () -> CommonUtils.asDoubleLatitude("100", "error!"));
    }

    /**
     * @throws InvalidValueException
     */
    @Test
    public void convertLanToDouble_shouldReturnTheDoubleValue_ifTheValueIsValid() throws InvalidValueException {

        String lonStr = "-73.5";
        Double longitude = -73.5d;
        assertEquals(longitude, CommonUtils.asDoubleLongitude(lonStr, "error!"));
    }

    /**
     *
     */
    @Test
    public void convertLanToDouble_shouldThrowAnException_ifTheValueIsNotADouble() {

        assertThrows(InvalidValueException.class, () -> CommonUtils.asDoubleLongitude("garbage", "error!"));
    }

    /**
     *
     */
    @Test
    public void convertLanToDouble_shouldThrowAnException_ifTheValueIsOutsideTheValidRange() {
        assertThrows(InvalidValueException.class, () -> CommonUtils.asDoubleLongitude("-194", "error!"));
        assertThrows(InvalidValueException.class, () -> CommonUtils.asDoubleLongitude("310", "error!"));
    }


    /**
     * Test Class
     */
    public static class MicroTestClass {

        private String stringField;
        private int intField;

        public String getStringField() {
            return stringField;
        }

        public void setStringField(String stringField) {
            this.stringField = stringField;
        }

        public int getIntField() {
            return intField;
        }

        public void setIntField(int intField) {
            this.intField = intField;
        }
    }

    /**
     *
     */
    @Test
    public void deserialize_shouldThrowAnException_ifTheObjectCantBeDeserialized() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        data.put("invalidField", "someValue");
//        assertThrows(MicroTestClass.class, () -> deserialize(mapper, data, MicroTestClass.class));
    }

    /**
     * @throws DeserializationException
     */
    @Test
    public void deserialize_shouldReturnTheObject_ifTheObjectIsValid() throws DeserializationException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        data.put("stringField", "value");
        data.put("intField", 5);

        MicroTestClass result = deserialize(mapper, data, MicroTestClass.class);

        assertEquals("value", result.getStringField());
        assertEquals(5, result.getIntField());
    }

    /**
     * @throws InvalidValueException
     */
    @Test
    public void asIntegerPercentage_shouldReturnTheIntegerValue_ifTheValueIsValid() throws InvalidValueException {
        String batteryStr = "45";
        Integer battery = 45;
        assertEquals(battery, asIntegerPercentage(batteryStr));
    }

    /**
     *
     */
    @Test
    public void asIntegerPercentage_shouldThrowAnException_ifTheValueIsNotAnInteger() {
        assertThrows(InvalidValueException.class, () -> asIntegerPercentage("garbage"));
    }

    /**
     *
     */
    @Test
    public void asIntegerPercentage_shouldThrowAnException_ifTheValueIsOutsideTheValidRange() {
        assertThrows(InvalidValueException.class, () -> asIntegerPercentage("-5"));
        assertThrows(InvalidValueException.class, () -> asIntegerPercentage("110"));
    }

    /**
     * @throws InvalidValueException
     */
    @Test
    public void validateSerialNumber_shouldReturnTrue_ifTheSerialNumberIsAValidInteger() throws InvalidValueException {
        assertTrue(validateSerialNumber("1234"));
    }

    /**
     *
     */
    @Test
    public void validateSerialNumber_shouldThrowAnException_ifTheSerialNumberIsNotAnInteger() {
        assertThrows(InvalidValueException.class, () -> validateSerialNumber("Invalid"));
    }

    /**
     *
     */
    @Test
    public void validateSerialNumber_shouldThrowAnException_ifTheSerialNumberIsOutsideTheValidRange() {
        assertThrows(InvalidValueException.class, () -> validateSerialNumber("111111111111111111111"));
        assertThrows(InvalidValueException.class, () -> validateSerialNumber("-111111111111111111111"));
    }

}
