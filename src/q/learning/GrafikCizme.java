/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q.learning;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author AYAZ
 */
public class GrafikCizme{
       final DecimalFormat df = new DecimalFormat("#.##");
   ArrayList<ArrayList<Integer>> kazanclar;
   ArrayList<ArrayList<Double>> maliyetler;
  ArrayList<ArrayList<Point>> pointler;

   int deger;
    public GrafikCizme(ArrayList<ArrayList<Integer>> kazanc,ArrayList<ArrayList<Double>> maliyet,int deger){
        kazanclar = kazanc;
        maliyetler = maliyet;
        this.deger = deger;
    
        ChartFrame frame = new ChartFrame("Graph",grafikAyarlari());
           
        frame.setVisible(true);
        frame.setSize(900,900);
    }
    public GrafikCizme(ArrayList<ArrayList<Point>> point){
        this.pointler = point;
        ChartFrame frame = new ChartFrame("Graph2",grafik2Ayarlari());
         frame.setVisible(true);
         frame.setLocation(885, 0);
         frame.setSize(900,900);
    }
     public JFreeChart grafik2Ayarlari(){
          int counter = 0;
          XYSeries series = new XYSeries("Graph");
          for(ArrayList<Point> ar : pointler){
              counter++;
              series.add(counter,(double)ar.size());
          }
          XYSeriesCollection dataset = new XYSeriesCollection(series);
      dataset.setAutoWidth(true);

      JFreeChart chart = ChartFactory.createXYBarChart("Episode via cost Step", "Episode",false,"Steps", dataset, PlotOrientation.VERTICAL, false,false,false);
      XYPlot plot = chart.getXYPlot();
        
       XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
       renderer.setSeriesPaint(0, Color.BLUE);
       renderer.setSeriesStroke(deger, new BasicStroke(0.1f));
       renderer.setBaseShapesFilled(false);
       renderer.setDrawOutlines(false);
       plot.getDomainAxis().setUpperMargin(0);
       plot.setRangeGridlinesVisible(true);
       plot.setRangeGridlinePaint(Color.BLACK);
       
    
           
      return chart; 
     }
    public JFreeChart grafikAyarlari(){
      int counter = 0;
      XYSeries series = new XYSeries("sasa");
      for(ArrayList<Double> ar: maliyetler){
          counter++;
          for(int i=0;i<ar.size();i++){
              series.add(counter,(double)ar.get(i));
          }
      }
     
      XYSeriesCollection dataset = new XYSeriesCollection(series);
      dataset.setAutoWidth(true);

      JFreeChart chart = ChartFactory.createXYBarChart("Episode via cost Graph", "Episode",false,"Cost", dataset, PlotOrientation.VERTICAL, false,false,false);
      // JFreeChart chart = ChartFactory.createBarChart("Kazanç Maliyet Grafiği", "Maliyet","Kazanç", dataset, PlotOrientation.VERTICAL, false,true,true);
      //JFreeChart chart = ChartFactory.createLineChart("Kazanç Maliyet Grafiği", "Maliyet","Kazanç", dataset, PlotOrientation.VERTICAL, false,true,true);
        XYPlot plot = chart.getXYPlot();
        
       XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
       renderer.setSeriesPaint(0, Color.BLUE);
       renderer.setSeriesStroke(deger, new BasicStroke(0.1f));
       renderer.setBaseShapesFilled(false);
       renderer.setDrawOutlines(false);
       plot.getDomainAxis().setUpperMargin(0);
       plot.setRangeGridlinesVisible(true);
       plot.setRangeGridlinePaint(Color.BLACK);
       
    
           
      return chart;
    }
}
