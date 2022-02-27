package org.appeng.gui.components.organizational;

import org.appeng.constants.DataParametersConstants;
import org.appeng.gui.components.misc.BoldLabel;
import org.appeng.gui.components.organizational.chart.RealtimeChart;
import org.knowm.xchart.internal.chartpart.Chart;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DataPane extends JPanel {

    private Map<String, RealtimeChart> chartHashMap = new HashMap<>();

    private JScrollPane dashboardScroll;
    private JPanel contentPane;

    public DataPane() {
        init();
    }

    private void init() {


        this.setLayout(new BorderLayout());

        JLabel label = new JLabel("Dashboard Panel");

        int padding = 20;
//        label.setBorder(
//                new EmptyBorder(padding, padding, padding, 0)
//        );




        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        dashboardScroll = new JScrollPane(contentPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        dashboardScroll.setBorder(null);
//        this.setBorder(
//                BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY)
//                );

        contentPane.setBorder(new EmptyBorder(padding, padding, padding, padding));

        // contentPane.add(label);

        for (String chartId : DataParametersConstants.DATA_PROPERTIES_IDS) {
            RealtimeChart newChart = new RealtimeChart(chartId);
            chartHashMap.put(chartId, newChart);
            contentPane.add(newChart);
        }



        this.add(dashboardScroll, BorderLayout.CENTER);

        // update data
        new Thread(new Runnable() {
            @Override
            public void run() {
                double phase = 0;
                while(true) {
                    phase += 1 * Math.PI * 1 / 200.0;
                    for (String chartId : DataParametersConstants.DATA_PROPERTIES_IDS) {
                        RealtimeChart chart = chartHashMap.get(chartId);
                        // chart.getDataX().add(Math.random()*100);
                        // chart.getDataY().add(phase);
                        chart.updateChartWithNewData(getSineData(phase).get(0), getSineData(phase).get(1));
                    }
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();

    }

    private static List<List<Double>> getSineData(double phase) {

        List<Double> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            double radians = phase + (2 * Math.PI / 100 * i);
            xData.add(radians);
            yData.add(Math.sin(radians));
        }

        List<List<Double>> lists = new ArrayList<>();
        lists.add(xData);
        lists.add(yData);

        return lists;
    }
}
