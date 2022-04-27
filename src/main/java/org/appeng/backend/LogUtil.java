package org.appeng.backend;

public class LogUtil {
    public static void addError(String tag, String msg, DataManager dataManager) {
        System.err.printf("[%s] : %s%n", tag, msg);
        dataManager.debugText += String.format("ERR [%s] : %s%n", tag, msg);
    }

    public static void add(String tag, String msg, DataManager dataManager) {
        System.out.printf("[%s] : %s%n", tag, msg);
        dataManager.debugText += String.format(" OK [%s] : %s%n", tag, msg);
    }
}
