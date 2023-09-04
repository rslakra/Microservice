package com.rslakra.microservice.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rslakra.microservice.framework.exception.DeserializationException;
import com.rslakra.microservice.framework.exception.InvalidUUIDException;
import com.rslakra.microservice.framework.exception.InvalidValueException;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Handles the common operations.
 *
 * @author Rohtash Lakra
 * @created 7/27/23 8:39 PM
 */
public enum CommonUtils {

    INSTANCE;

    public static final String
        ERR_DIVIDE_BY_ZERO =
        "Cannot calculate an average velocity when the time interval is 0!";

    public static final String ERR_DESERIALIZATION_FAILED = "Unable to deserialize the requested object!";
    public static final String ERR_INVALID_PERCENTAGE = "The percentage should be between 0 and 100!";
    public static final String ERR_INVALID_LATITUDE = "Latitude must be between -90 and 90!";
    public static final String ERR_INVALID_LONGITUDE = "Longitude must be between -180 and 180!";
    public static final String ERR_INVALID_SERIAL = "Serial Number must be a number!";
    public static final double ZERO = 0d;

    /**
     * Converts String to UUID and throws exception if string is not a valid UUID
     *
     * @param id
     * @param errorMessage
     * @return
     * @throws InvalidUUIDException
     */
    public static UUID toUUID(String id, String errorMessage) throws InvalidUUIDException {
        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUUIDException(String.format(errorMessage, id, ex.getMessage()));
        }

        return uuid;
    }

    /**
     * @param rawString
     * @param errorMessage
     * @return
     * @throws InvalidValueException
     */
    public static Integer asInteger(String rawString, String errorMessage) throws InvalidValueException {
        Integer value = null;
        try {
            value = Integer.parseInt(rawString);
        } catch (NumberFormatException ex) {
            throw new InvalidValueException(errorMessage);
        }

        return value;
    }

    /**
     * @param rawString
     * @param errorMessage
     * @return
     * @throws InvalidValueException
     */
    public static Double asDouble(String rawString, String errorMessage) throws InvalidValueException {
        Double value = null;
        try {
            value = Double.parseDouble(rawString);
        } catch (NumberFormatException e) {
            throw new InvalidValueException(errorMessage);
        }

        return value;
    }

    /**
     * @param rawString
     * @param errorMessage
     * @return
     * @throws InvalidValueException
     */
    public static Double asDoubleLatitude(String rawString, String errorMessage) throws InvalidValueException {
        Double latitude = asDouble(rawString, errorMessage);
        if ((latitude < -90) || (latitude > 90)) {
            throw new InvalidValueException(errorMessage);
        }

        return latitude;
    }

    /**
     * @param rawString
     * @param errorMessage
     * @return
     * @throws InvalidValueException
     */
    public static Double asDoubleLongitude(String rawString, String errorMessage) throws InvalidValueException {
        Double longitude = asDouble(rawString, errorMessage);
        if ((longitude < -180) || (longitude > 180)) {
            throw new InvalidValueException(errorMessage);
        }

        return longitude;
    }

    /**
     * @param rawString
     * @param errorMessage
     * @return
     * @throws InvalidValueException
     */
    public static BigDecimal asBigDecimal(String rawString, String errorMessage) throws InvalidValueException {
        BigDecimal value = null;
        try {
            value = new BigDecimal(rawString);
        } catch (NumberFormatException ex) {
            throw new InvalidValueException(errorMessage);
        }

        return value;
    }

    /**
     * @param rawString
     * @param errorMessage
     * @return
     * @throws InvalidValueException
     */
    public static BigDecimal asBigDecimalLatitude(String rawString, String errorMessage) throws InvalidValueException {
        return BigDecimal.valueOf(asDoubleLatitude(rawString, errorMessage));
    }

    /**
     * @param rawString
     * @param errorMessage
     * @return
     * @throws InvalidValueException
     */
    public static BigDecimal asBigDecimalLongitude(String rawString, String errorMessage) throws InvalidValueException {
        return BigDecimal.valueOf(asDoubleLongitude(rawString, errorMessage));
    }

    /**
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     * @return
     */
    private static double distance(double latitude1, double longitude1, double latitude2, double longitude2) {
        GeodeticCalculator geoCalc = new GeodeticCalculator();
        // select a reference ellipsoid
        Ellipsoid reference = Ellipsoid.WGS84;
        // set first coordinates
        GlobalCoordinates start = new GlobalCoordinates(latitude1, longitude1);
        // set second coordinates
        GlobalCoordinates end = new GlobalCoordinates(latitude2, longitude2);
        // calculate the geodetic curve
        GeodeticCurve geoCurve = geoCalc.calculateGeodeticCurve(reference, start, end);

        return (geoCurve.getEllipsoidalDistance() / 1000.0);
    }

    /**
     * Finds the distance between two points, in kilometers, to a precision of 10 meters.
     *
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     * @return
     */
    public static double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        return Math.round(distance(latitude1, longitude1, latitude2, longitude2) * 100d) / 100d;
    }

    /**
     * returns the time between two timestamps, in decimal minutes
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static double calculateDurationMinutes(LocalDateTime startTime, LocalDateTime endTime) {
        return calculateDuration(startTime, endTime).toMillis() / 60000d;
    }

    /**
     * @param startTime
     * @param endTime
     * @return
     */
    private static Duration calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime);
    }

    /**
     * Finds the magnitude of the velocity, in kilometers per hour
     *
     * @param latitude1
     * @param longitude1
     * @param startTime
     * @param latitude2
     * @param longitude2
     * @param endTime
     * @return
     * @throws InvalidValueException
     */
    public static double calculateVelocity(double latitude1, double longitude1, LocalDateTime startTime,
                                           double latitude2, double longitude2, LocalDateTime endTime)
        throws InvalidValueException {
        double distanceTravelled = calculateDistance(latitude1, longitude1, latitude2, longitude2);
        if (ZERO == distanceTravelled) {
            return ZERO;
        }

        double hoursElapsed = calculateDurationMinutes(startTime, endTime) / 60d;
        if (ZERO == hoursElapsed) {
            throw new InvalidValueException(ERR_DIVIDE_BY_ZERO);
        }

        // round to 2 dec places to make it look nice :)
        return Math.round(distanceTravelled / hoursElapsed * 100d) / 100d;
    }

    /**
     * @param mapper
     * @param data
     * @param classType
     * @param <T>
     * @return
     * @throws DeserializationException
     */
    public static <T> T deserialize(ObjectMapper mapper, Map<String, Object> data, Class<T> classType)
        throws DeserializationException {
        try {
            return mapper.convertValue(data, classType);
        } catch (IllegalArgumentException ex) {
            throw new DeserializationException(ERR_DESERIALIZATION_FAILED + " " + ex.getMessage());
        }
    }

    /**
     * @param textPercentage
     * @return
     * @throws InvalidValueException
     */
    public static Integer asIntegerPercentage(String textPercentage) throws InvalidValueException {
        Integer battery = asInteger(textPercentage, ERR_INVALID_PERCENTAGE);
        if ((battery < 0) || (battery > 100)) {
            throw new InvalidValueException(ERR_INVALID_PERCENTAGE);
        }

        return battery;
    }

    /**
     * @param rawString
     * @return
     * @throws InvalidValueException
     */
    public static Double asDoubleLatitude(String rawString) throws InvalidValueException {
        return CommonUtils.asDoubleLatitude(rawString, ERR_INVALID_LATITUDE);
    }

    /**
     * @param rawString
     * @return
     * @throws InvalidValueException
     */
    public static Double asDoubleLongitude(String rawString) throws InvalidValueException {
        return CommonUtils.asDoubleLongitude(rawString, ERR_INVALID_LONGITUDE);
    }


    /**
     * @param rawString
     * @return
     * @throws InvalidValueException
     */
    public static BigDecimal asBigDecimalLatitude(String rawString) throws InvalidValueException {
        return CommonUtils.asBigDecimalLatitude(rawString, ERR_INVALID_LATITUDE);
    }

    /**
     * @param rawString
     * @return
     * @throws InvalidValueException
     */
    public static BigDecimal asBigDecimalLongitude(String rawString) throws InvalidValueException {
        return CommonUtils.asBigDecimalLongitude(rawString, ERR_INVALID_LONGITUDE);
    }

    /**
     * @param serialNumber
     * @return
     * @throws InvalidValueException
     */
    public static boolean validateSerialNumber(String serialNumber) throws InvalidValueException {
        try {
            Integer.parseInt(serialNumber);
        } catch (NumberFormatException ex) {
            throw new InvalidValueException(ERR_INVALID_SERIAL);
        }

        return true;
    }
}
