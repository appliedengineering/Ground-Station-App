package org.appeng.util.resources.images;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ImageLoaderUtil {

    public static ImageIcon getLogo() {
        try {
            Image image = ImageIO.read(ImageLoaderUtil.class.getResourceAsStream("/ae-logo.png"));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
