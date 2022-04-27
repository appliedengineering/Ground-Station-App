package org.appeng.backend;

import java.util.List;

public class Util {
    public static float parseFloat(String string, DataManager dataManager) {
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException e) {
            LogUtil.addError("Util", "Could not parse number! Corrupted data? " + string, dataManager);
            return 0;
        }
    }

    public static long parseLong(String string, DataManager dataManager) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            LogUtil.addError("Util", "Could not parse number! Corrupted data? " + string, dataManager);
            return 0L;
        }
    }

    public static double parseDouble(String string, DataManager dataManager) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            LogUtil.addError("Util", "Could not parse number! Corrupted data? " + string, dataManager);
            return 0;
        }
    }

    public static boolean parseBoolean(String toString, DataManager dataManager) {
        return toString.equalsIgnoreCase("true");
    }
}
