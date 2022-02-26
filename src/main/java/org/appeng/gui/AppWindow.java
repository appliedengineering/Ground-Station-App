package org.appeng.gui;

import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {
    public void setExpanded(){
        int state = this.getExtendedState();
        this.setExtendedState(state | Frame.MAXIMIZED_BOTH);
        this.setSize(800, 500);
        this.setVisible(true);
    }
}
