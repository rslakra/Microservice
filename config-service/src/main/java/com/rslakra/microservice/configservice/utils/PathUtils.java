package com.rslakra.microservice.configservice.utils;

import java.io.File;

/**
 * @author Rohtash Lakra
 * @created 11/17/22 3:48 PM
 */
public enum PathUtils {

    INSTANCE;

    /**
     * @param serverUrl
     * @param port
     * @param pathComponents
     * @return
     */
    public static String pathString(final String serverUrl, final int port, final String... pathComponents) {
        final StringBuilder pathBuilder = new StringBuilder(serverUrl);
        pathBuilder.append(port);
        if (pathComponents != null) {
            for (String path : pathComponents) {
                if (!path.startsWith(File.separator)) {
                    pathBuilder.append(File.separator);
                }
                pathBuilder.append(path);
            }
        }

        return pathBuilder.toString();
    }

    /**
     * @param port
     * @param pathComponents
     * @return
     */
    public static String pathString(final int port, final String... pathComponents) {
        return pathString("http://localhost:", port, pathComponents);
    }
}
