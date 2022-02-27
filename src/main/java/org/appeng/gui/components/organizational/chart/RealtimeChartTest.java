package org.appeng.gui.components.organizational.chart;

import org.appeng.lookandfeel.LookAndFeelManager;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;

import java.awt.*;

public class RealtimeChartTest {

    private XYChart chart;
    private SwingWrapper<XYChart> sw;

    public static void main(String[] args) throws InterruptedException{
        new RealtimeChartTest().setUpChart();
    }

    public void setUpChart() throws InterruptedException{
        double phase = 0;
        double[][] initdata = getSineData(phase);

        LookAndFeelManager.setupDarkMode();

        // create Chart
        chart = QuickChart.getChart("Sine Wave : )", "Radians", "Sine", "sine", initdata[0], initdata[1]);

        Color bg = new Color(60,63,65);

        // style chart
        chart.getStyler().setPlotGridHorizontalLinesVisible(true);
        chart.getStyler().setPlotGridVerticalLinesVisible(false);
        chart.getStyler().setPlotGridVerticalLinesVisible(false);
        chart.getStyler().setPlotGridLinesStroke(new BasicStroke(0.005f));
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisTicksVisible(false);
        chart.getStyler().setChartBackgroundColor(bg);
        chart.getStyler().setPlotBackgroundColor(bg);
        chart.getStyler().setPlotGridLinesColor(Color.LIGHT_GRAY);
        chart.getStyler().setYAxisTickLabelsColor(Color.LIGHT_GRAY);
        chart.getStyler().setChartFontColor(Color.LIGHT_GRAY);
        chart.getStyler().setAxisTitlesVisible(false);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setPlotBorderVisible(false);


        // show it
        sw = new SwingWrapper<>(chart);
        sw.displayChart();

        while (true) {

            phase += 1 * Math.PI * 1 / 200.0;

            Thread.sleep(16);

            final double[][] data = getSineData(phase);

            XYSeries series = chart.updateXYSeries("sine", data[0], data[1], null);
            series.setLineColor(Color.GREEN);
            sw.repaintChart();
        }
    }

    private static double[][] getSineData(double phase) {

        double[] xData = new double[100];
        double[] yData = new double[100];
        for (int i = 0; i < xData.length; i++) {
            double radians = phase + (2 * Math.PI / xData.length * i);
            xData[i] = radians;
            yData[i] = Math.sin(radians);
        }
        return new double[][] { xData, yData };
    }



}
