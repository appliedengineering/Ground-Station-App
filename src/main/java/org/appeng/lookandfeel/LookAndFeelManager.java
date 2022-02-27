package org.appeng.lookandfeel;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class LookAndFeelManager {

    public static void setupLightMode(){
        FlatLightLaf.setup();
    }

    public static void setupDarkMode(){
        FlatDarculaLaf.setup();
    }

}
