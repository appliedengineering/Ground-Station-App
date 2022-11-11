package org.appeng.gui.components.organizational.chart;

import org.appeng.backend.DataPoint;
import org.appeng.backend.Util;
import org.knowm.xchart.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        chart.getStyler().setChartTitleFont(new JLabel().getFont());

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
        chart.getStyler().setYAxisTicksVisible(true);
        chart.getStyler().setYAxisDecimalPattern("#");

        panel = new XChartPanel<>(chart);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
    }

    public void updateChartWithNewData(List<DataPoint> dataRef){

        if(dataRef == null) return;

        int viewRange = 50;

        List<DataPoint> data = new ArrayList<>(dataRef);

        data = data.subList(data.size()-Math.min(data.size(), viewRange), data.size());
        Collections.reverse(data);

        List<Double> dataX = new ArrayList<>(), dataY = new ArrayList<>();
        for (DataPoint dp : data) {
            dataX.add(dp.x);
            dataY.add(dp.y);
        }


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
