package org.appeng.gui.components.organizational.chart;

import org.appeng.lookandfeel.LookAndFeelManager;
import org.knowm.xchart.*;

import java.awt.*;
import java.text.DecimalFormat;

public class DialChartTest {
    private DialChart chart;
    private SwingWrapper<DialChart> sw;



    public static void main(String[] args) throws InterruptedException{
        new DialChartTest().setUpChart();
    }

    public void setUpChart() throws InterruptedException{
        double phase = 0;
        // double[][] initdata = getSineData(phase);

        LookAndFeelManager.setupDarkMode();

        // create Chart
        chart = new DialChartBuilder().width(300).height(300).title("test").build();
        DialSeries series = chart.addSeries("data", Math.abs(Math.sin(phase)), "hello");
        Color bg = new Color(60,63,65);

        // style chart
        // chart.getStyler().setPlotGridHorizontalLinesVisible(true);
        // chart.getStyler().setPlotGridVerticalLinesVisible(false);
        // chart.getStyler().setPlotGridVerticalLinesVisible(false);
        // chart.getStyler().setPlotGridLinesStroke(new BasicStroke(0.005f));
        chart.getStyler().setLegendVisible(false);
        // chart.getStyler().setXAxisTicksVisible(false);
        chart.getStyler().setChartBackgroundColor(bg);
        chart.getStyler().setPlotBackgroundColor(bg);
        // chart.getStyler().setPlotGridLinesColor(Color.LIGHT_GRAY);
        // chart.getStyler().setYAxisTickLabelsColor(Color.LIGHT_GRAY);
        chart.getStyler().setChartFontColor(Color.LIGHT_GRAY);
        // chart.getStyler().setAxisTitlesVisible(false);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setPlotBorderVisible(false);

        chart.getStyler().setArrowColor(Color.WHITE);
        chart.getStyler().setLowerColor(Color.WHITE);
        chart.getStyler().setUpperColor(Color.WHITE);
        chart.getStyler().setMiddleColor(Color.WHITE);



        // show it
        sw = new SwingWrapper<>(chart);
        sw.displayChart();

        while (true) {

            phase += 1 * Math.PI * 1 / 200.0;

            Thread.sleep(16);

            // final double[][] data = getSineData(phase);

            chart.removeSeries("dataId");
            chart.addSeries("dataId", Math.abs(Math.sin(phase)), "RPM");
            // series.setLineColor(Color.GREEN);
            sw.repaintChart();
        }
    }
}
