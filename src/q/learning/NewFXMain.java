/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q.learning;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author AYAZ
 */
public class NewFXMain extends Application {
    ArrayList<ArrayList<Integer>> Kazanclar;
    ArrayList<ArrayList<Double>> Maliyetler;
    public void basla(ArrayList<ArrayList<Integer>> kazanclar,ArrayList<ArrayList<Double>> maliyetler){
          this.Kazanclar  = new ArrayList(kazanclar);
          this.Maliyetler = new ArrayList(maliyetler);
          String [] args = null;
          NewFXMain.main(args);
    }
    public ArrayList getK (){
        return this.Kazanclar;
    }
    @Override
    public void start(Stage stage) {
       System.out.println(getK());
        NumberAxis xAxis = new NumberAxis(1960,2020,10);
        xAxis.setLabel("Maliyetler");
        NumberAxis yAxis = new NumberAxis   (0, 350, 50); 
        yAxis.setLabel("Kazançlar"); 
        LineChart linechart = new LineChart(xAxis, yAxis);  
        XYChart.Series series = new XYChart.Series(); 
        series.setName("No of schools in an year"); 
        series.getData().add(new XYChart.Data(1970, 15)); 
        series.getData().add(new XYChart.Data(1980, 30)); 
        series.getData().add(new XYChart.Data(1990, 60)); 
        series.getData().add(new XYChart.Data(2000, 120)); 
        series.getData().add(new XYChart.Data(2013, 240)); 
        series.getData().add(new XYChart.Data(2014, 300)); 
        linechart.getData().add(series);  
        Group root = new Group(linechart); 
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Grafik"); 
         
        //Adding scene to the stage 
        stage.setScene(scene);
	   
        //Displaying the contents of the stage 
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
       
    }
    
}
