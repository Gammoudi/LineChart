package org.exoplatform.tqa;

/**
 * Created by nagui on 27/02/14.
 */

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadExcel{
    public static void main(String[]args){
        short a=0;
        short b=1;
        short c=2;
        int i=0;
        String x="";
        String y="";
        String z="";
        String filename ="/home/nagui/Bureau/test.xlsx";
        XYSeries plf_old_data = new XYSeries("PLF 4.0.4");
        XYSeries plf_new_data = new XYSeries("PLF 4.0.5");
        XYSeries plf_one_sec = new XYSeries("one sec");
        XYSeries plf_two_sec = new XYSeries("one sec");
        XYSeries plf_three_sec = new XYSeries("one sec");
        if(filename != null && !filename.equals("")){
            try{
                FileInputStream fs =new FileInputStream(filename);
                XSSFWorkbook wb = new XSSFWorkbook(fs);
                    XSSFSheet sheet = wb.getSheetAt(1);
                    int rows = sheet.getPhysicalNumberOfRows();
                    for(int r = 1; r < rows; r++){
                        XSSFRow row = sheet.getRow(r);
                        XSSFCell cell1 = row.getCell(a);
                        cell1.setCellType(1);
                        x =cell1.getStringCellValue();
                        XSSFCell cell2 = row.getCell(b);
                       cell2.setCellType(1);
                        y =cell2.getStringCellValue();
                        XSSFCell cell3 = row.getCell(c);
                        cell3.setCellType(1);
                        z =cell3.getStringCellValue();
                        plf_old_data.add(Double.parseDouble(x), Double.parseDouble(y));
                        plf_new_data.add(Double.parseDouble(x), Double.parseDouble(z));
                        plf_one_sec.add(Double.parseDouble(x), 1);
                        plf_two_sec.add(Double.parseDouble(x), 2);
                        plf_three_sec.add(Double.parseDouble(x), 3);
                    i++;
                }
            }catch(Exception e){
                System.out.println(e);

            }

        }

        /* Add all XYSeries to XYSeriesCollection */
        //XYSeriesCollection implements XYDataset
        XYSeriesCollection my_data_series= new XYSeriesCollection();

        my_data_series.addSeries(plf_one_sec);
        my_data_series.addSeries(plf_two_sec);
        my_data_series.addSeries(plf_three_sec);
        my_data_series.addSeries(plf_old_data);
        my_data_series.addSeries(plf_new_data);
        JFreeChart chart = ChartFactory.createXYLineChart("Intranet Home Page - Response Time (Tomcat)", "Concurrent VUs", "Throughout(rq/s)", my_data_series,
                PlotOrientation.VERTICAL,true, true, false);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer)plot.getRenderer();

        r.setSeriesPaint(0, ChartColor.LIGHT_GREEN);
        r.setSeriesPaint(1, ChartColor.GREEN);
        r.setSeriesPaint(2, ChartColor.DARK_GREEN);
        r.setSeriesShapesFilled(0, false);
        r.setSeriesShapesFilled(1, false);
        r.setSeriesShapesFilled(2, false);

        try {
            ChartUtilities.saveChartAsJPEG(new File("/home/nagui/Bureau/chart.jpg"), chart, 1000, 500);
        } catch (IOException e) {
            System.out.println("Problem in creating chart.");
        }
    }
}