package org.appeng.backend;

public class LogUtil {
    public static void addError(String tag, String msg) {
        System.err.printf("[%s] : %s%n", tag, msg);
    }

    public static void add(String tag, String msg) {
        System.out.printf("[%s] : %s%n", tag, msg);
    }
}
