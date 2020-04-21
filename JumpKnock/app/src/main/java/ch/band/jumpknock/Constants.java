package ch.band.jumpknock;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class Constants {
    private static final String PACKAGE_NAME = Constants.class.getPackage().getName();
    private static final String RESSOURCE_URI_PREFIX = "android.resource://" + PACKAGE_NAME + "/";

    public static String getPackageName() {
        return PACKAGE_NAME;
    }

    public static String getRessourceUriPrefix() {
        return RESSOURCE_URI_PREFIX;
    }
}
