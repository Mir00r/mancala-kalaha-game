package com.bol.mancala.utils;

/**
 * @author mir00r on 9/2/22
 * @project IntelliJ IDEA
 */
public class ExceptionUtil {

    public static String getNotFoundMsg(String domain, String message, Long reference) {
        return domain + " " + message + " " + reference;
    }

    public static String getNotFoundMsg(String domain, String message, String reference) {
        return domain + " " + message + " " + reference;
    }
}
