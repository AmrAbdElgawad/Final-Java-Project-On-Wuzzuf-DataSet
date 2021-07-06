package com.example.Wuzzuf_Data_Analysis;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Drawing {
    Color getRandomColors(){
        Random rand = new Random();
        int upperbound = 255;
        int r = rand.nextInt(upperbound);
        int g= rand.nextInt(upperbound);
        int b = rand.nextInt(upperbound);
        return new Color(r,g,b);
    }
    public  void categoryCharts(Dataset<Row> data,String chartTitle,String x,String y,String retName) throws IOException {
        CategoryChart chart = new CategoryChartBuilder().width(1900).height(1000).title(chartTitle).xAxisTitle(x).yAxisTitle(y).build();
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setStacked(true);
        List<Row> listJobs = data.collectAsList();
        List<Integer> jobsCount=new ArrayList<Integer>();
        List<String> jobTitles=new ArrayList<String>();
        Color[] colorArray = new Color[listJobs.size()];
        int idx = 0;
        for (Row row :listJobs){
            int nums = Integer.valueOf(row.get(1).toString());
            String str=row.get(0).toString();
            jobsCount.add(nums);
            jobTitles.add(str);
            colorArray[idx] =getRandomColors();
            idx++;

            if (idx==10){
                break;
            }
        }
            chart.addSeries(x,jobTitles,jobsCount);

        chart.getStyler().setSeriesColors(colorArray);
        File f=new File("target/classes/"+retName);
        if(f.exists()) {
            f.delete();
        }
        BitmapEncoder.saveJPGWithQuality(chart, "target/classes/"+retName, 0.95f);
    }
    public  void pieChart(Dataset<Row> data,String retName) throws IOException {
        PieChart chart=new PieChartBuilder().width(2000).height(1000).title("Number of Job Offers for Each Company").build();
        List<Row> listComp= data.collectAsList();
        Color[] colorArray = new Color[listComp.size()];
        int idx = 0;
        for (Row row :listComp){
            int nums = Integer.valueOf(row.get(1).toString());
            String str=row.get(0).toString();
            if (nums>=15){
                chart.addSeries(str,nums);
                colorArray[idx] =getRandomColors();
                idx++;
            }
        }
        chart.getStyler().setSeriesColors(colorArray);
        File f=new File("target/classes/"+retName);
        if(f.exists()) {
            f.delete();
        }
        BitmapEncoder.saveJPGWithQuality(chart, "target/classes/"+retName, 0.95f);

    }



}
