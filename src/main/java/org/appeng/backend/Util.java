package org.appeng.backend;

import java.util.List;

public class Util {
    public static float parseFloat(String string) {
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException e) {
            LogUtil.addError("Util", "Could not parse number! Corrupted data?");
            return 0;
        }
    }

    public static long parseLong(String string) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            LogUtil.addError("Util", "Could not parse number! Corrupted data?");
            return 0L;
        }
    }

    public static double parseDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            LogUtil.addError("Util", "Could not parse number! Corrupted data?");
            return 0;
        }
    }

}
