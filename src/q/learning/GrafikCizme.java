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
import org.jfree.chart.ChartPanel;
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
   ArrayList<Double> maliyetler;
  ArrayList<ArrayList<Point>> pointler;
  int []Y;


   int deger;
    public GrafikCizme(ArrayList<ArrayList<Integer>> kazanc,ArrayList<Double> maliyet,int deger){
        kazanclar = kazanc;
        maliyetler = maliyet;
        this.deger = deger;
        //ChartFrame frame = new ChartFrame("Graph",grafikAyarlari());
        JFrame frame = new JFrame("Graph");   
        ChartPanel chartPanel = new ChartPanel(grafikAyarlari());
        frame.add(chartPanel);
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
     public GrafikCizme(int [] Y,Oyun oyun){
        this.Y = Y;
        ChartFrame frame = new ChartFrame("Graph3",grafik3Ayarlari(oyun));
         frame.setVisible(true);
         frame.setSize(900,900);
     }
     
     
     
    public JFreeChart grafik3Ayarlari(Oyun oyun){
          XYSeries series = new XYSeries("Graph");
          System.out.println(Y.length);
          int start = oyun.getGrid()[oyun.getStart().x][oyun.getStart().y].getSira();
          int finish = oyun.getGrid()[oyun.getFinish().x][oyun.getFinish().y].getSira();
          int state = start;
          int counter = 0;
          series.add(counter,start);
          counter++;
          while(state != finish){
             state = Y[state];
              if(state == finish){
                  continue;
              }
              series.add(counter, state);
              counter++;
          }
          series.add(counter,finish);
          XYSeriesCollection dataset = new XYSeriesCollection(series);
      dataset.setIntervalWidth(0.2f);

      JFreeChart chart = ChartFactory.createXYBarChart("YOL CIZIMI", "Episode",false,"State", dataset, PlotOrientation.VERTICAL, false,false,false);
      XYPlot plot = chart.getXYPlot();    
       XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
       renderer.setSeriesPaint(0, Color.BLUE);
       renderer.setSeriesStroke(deger, new BasicStroke(0.5f));
       renderer.setBaseShapesFilled(false);
       renderer.setDrawOutlines(false);
       plot.getDomainAxis().setUpperMargin(1);
       plot.setRangeGridlinesVisible(true);
       plot.setRangeGridlinePaint(Color.BLACK);
       
       return chart;
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
       renderer.setSeriesStroke(deger, new BasicStroke(0.5f));
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
      for(Double ar: maliyetler){
                counter++;
              series.add(counter,ar);
         
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
