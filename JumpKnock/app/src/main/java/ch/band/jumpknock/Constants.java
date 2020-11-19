package ch.band.jumpknock;

import java.util.Objects;

/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class Constants {
    private static final String PACKAGE_NAME = Objects.requireNonNull(Constants.class.getPackage()).getName();
    private static final String RESOURCE_URI_PREFIX = "android.resource://" + PACKAGE_NAME + "/";

    public static String getPackageName() {
        return PACKAGE_NAME;
    }

    public static String getResourceUriPrefix() {
        return RESOURCE_URI_PREFIX;
    }
}
