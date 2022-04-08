package org.appeng.backend;

import org.appeng.util.resources.images.ImageLoaderUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsManager {
    public Properties settings = new Properties();


    public void saveSettings(){
        try {
            settings.store(new FileOutputStream("settings.config"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings(){
        try {
            settings.load(new FileInputStream("settings.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
