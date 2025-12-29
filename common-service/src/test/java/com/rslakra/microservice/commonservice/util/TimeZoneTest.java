package com.rslakra.microservice.commonservice.util;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void testWriteTimezonesToFile() throws IOException {
        TimeZone timeZone = new TimeZone();
        Map<String, String> allTimeZones = timeZone.getAllTimeZones();
        
        // Define the output file path (in target directory for test output)
        Path outputDir = Paths.get("target", "test-output");
        Files.createDirectories(outputDir);
        Path timezoneFile = outputDir.resolve("timezones.txt");
        
        // Write timezones to file
        try (FileWriter writer = new FileWriter(timezoneFile.toFile())) {
            writer.write("Number of Zone IDs: " + allTimeZones.size() + "\n\n");
            writer.write(String.format("%-35s %s%n", "Zone ID", "Zone Offset"));
            writer.write(String.format("%-35s %s%n", "-------", "-----------"));
            
            allTimeZones.forEach((zoneId, zoneOffset) -> {
                try {
                    writer.write(String.format("%-35s %s%n", zoneId, zoneOffset));
                } catch (IOException e) {
                    throw new RuntimeException("Error writing timezone to file", e);
                }
            });
        }
        
        // Verify file was created and has content
        assertTrue(Files.exists(timezoneFile), "Timezone file should be created");
        assertTrue(Files.size(timezoneFile) > 0, "Timezone file should not be empty");
        
        System.out.println("Timezones written to: " + timezoneFile.toAbsolutePath());
    }

}
