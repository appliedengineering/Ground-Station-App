package org.appeng.gui.components.organizational.chart;

import org.knowm.xchart.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntToDoubleFunction;

public class RealtimeChart extends JPanel {

    private XYChart chart;
    private String chartName;

    private List<Double> dataX, dataY;

    private XChartPanel<XYChart> panel;

    public RealtimeChart(String chartName) {
        this.chartName = chartName;
        dataX = new ArrayList<>();
        dataY = new ArrayList<>();

        // add one point
        dataX.add(1.0);
        dataY.add(1.0);

        initChart();
    }

    public void initChart() {
        // create Chart
        chart = QuickChart.getChart(chartName, "X", "Y", "defaultSeries", dataX, dataY);

        Color bg = new Color(60,63,65);

        setPreferredSize(new Dimension(300, 150));

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

        panel = new XChartPanel<>(chart);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
    }

    public void updateChartWithNewData(List<Double> dataX, List<Double> dataY){
        XYSeries series = chart.updateXYSeries("defaultSeries", dataX, dataY, null);
        series.setLineColor(Color.GREEN);
        panel.revalidate();
        panel.repaint();
    }

    // GETTERS AND SETTERS

    public List<Double> getDataX() {
        return dataX;
    }

    public void setDataX(List<Double> dataX) {
        this.dataX = dataX;
    }

    public List<Double> getDataY() {
        return dataY;
    }

    public void setDataY(List<Double> dataY) {
        this.dataY = dataY;
    }
}