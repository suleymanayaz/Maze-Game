/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q.learning;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author AYAZ
 */
public class Board  extends JFrame implements ActionListener{
    
    int boardx = 10,boardy=10;
    JPanel oyun,settings,generate,sonuc,sonuclar,oyuncular;
    JButton start,reset,grafik1,grafik2;
    JTextField startLoc,finishLoc;
    JLabel startLocLab,finishLocLab;
    Oyun oyunBoard;
    int deger;
    OyunUI oyunUI;
    File file = new File("C:\\Users\\AYAZ\\Documents\\NetBeansProjects\\Q-Learning\\engel.txt");
    ArrayList <ArrayList<Integer> > gecici1;
    ArrayList <ArrayList<Double>> gecici2;
      ArrayList <ArrayList<Point>> gecici3;
    public static void main(String []args){
        new Board();
    }
    
    public Board(){
        setLayout(new FlowLayout(0,100,0));      
        //setLayout(new GridLayout(1,2));  
        setTitle("Q-Learning");
        oyun = new JPanel();
        oyunBoard = new Oyun(boardx,boardy);
        oyunUI = new OyunUI(oyunBoard);
        oyun.add(oyunUI);
        add(oyun);
        createSettings();      
        add(settings);
        setMinimumSize(new Dimension(1366,768));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void createSettings(){
        settings = new JPanel();
        settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
        settings.setBackground(Color.YELLOW);
        generate = new JPanel();
        generate.setLayout(new FlowLayout());
        start = new JButton("Start");
        start.addActionListener(this);
        String string1 = "Number";
        startLoc =  new JTextField(string1,5);
        startLocLab = new JLabel("Start Location :  ");
        finishLoc = new JTextField(string1,5);
        finishLocLab = new JLabel("Finish Location : ");
        reset = new JButton("Reset");
        reset.addActionListener(this);
        grafik1 = new JButton("Grafik1");
        grafik1.addActionListener(this);
        grafik2 = new JButton("Grafik2");
        grafik2.addActionListener(this);
        JPanel settingLocationStart = new JPanel();
        settingLocationStart.setLayout(new FlowLayout());
        settingLocationStart.add(startLocLab);
        settingLocationStart.add(startLoc);
        JPanel settingLocationFinish = new JPanel();
        settingLocationFinish.setLayout(new FlowLayout());
        settingLocationFinish.add(finishLocLab);
        settingLocationFinish.add(finishLoc);
        JPanel settingIn = new JPanel();
        settingIn.setLayout(new BoxLayout(settingIn,BoxLayout.Y_AXIS));
        settingIn.add(settingLocationStart);
        settingIn.add(settingLocationFinish);
        settingIn.add(start);
        settingIn.add(reset);
        settingIn.add(grafik1);
        settingIn.add(grafik2);
        generate.add(settingIn);
        generate.setBorder(BorderFactory.createTitledBorder("Settings"));
        settings.add(generate);
     
    }
    
     public void fileWrite(){
        try{
            FileWriter fw = new FileWriter(file);
            for(int i=0;i<oyunBoard.getLines();i++){
                for(int j=0;j<oyunBoard.getCols();j++){
                    if(oyunBoard.getGrid()[i][j].isDuvar()){
                         fw.write("\n("+i+","+j+",K)"+"\r\n");
                    }else if (oyunBoard.getGrid()[i][j].p.equals(oyunBoard.getStart())){
                         fw.write("\n("+i+","+j+",M)"+"\r\n");
                    }else if (oyunBoard.getGrid()[i][j].p.equals(oyunBoard.getFinish())){
                         fw.write("\n("+i+","+j+",Y)"+"\r\n");
                    }else{
                         fw.write("\n("+i+","+j+",B)"+"\r\n");
                    }
                }
            }
           

            fw.close();
        }catch(IOException e){
            System.out.println("HATAA");
        }
    }
    public void resetBackground(){
        for(int i=0;i<this.oyunBoard.getLines();i++){
            for(int j=0;j<this.oyunBoard.getCols();j++){
                if(oyunBoard.getGrid()[i][j].isDuvar()){
                    continue;
                }else{
                      oyunUI.getGridUI()[i][j].setBackground(oyunUI.dbg);
                }
              
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
         JButton source = (JButton) e.getSource();
         if(source == start){
              System.out.println(startLoc.getText());
        System.out.println(finishLoc.getText());
        resetBackground();
        boolean giris = true;
        
        String startLocString = startLoc.getText();
        String finishLocString = finishLoc.getText();
                if(startLocString.equals("Number") || finishLocString.equals("Number") || startLocString.equals("") || finishLocString.equals("")){
                Frame frameForPopUp = new JFrame();
                JOptionPane.showMessageDialog(frameForPopUp,"Start  or finish input invalid  location!!");
                giris = false; 
            }
        
        if(giris){
            Point startP = findGrid(Integer.parseInt(startLocString));
            Point finishP = findGrid(Integer.parseInt(finishLocString));
            if(startP == null ||finishP == null || oyunBoard.getGrid()[startP.x][startP.y].isDuvar() ||oyunBoard.getGrid()[finishP.x][finishP.y].isDuvar() ){ // if leri ayırabilrisn.
                 Frame frameForPopUp = new JFrame();
                JOptionPane.showMessageDialog(frameForPopUp,"Start  or finish input invalid  location!!");
            }else{
            int startLocX = startP.x;
            int startLocY = startP.y;
            int finishLocX = finishP.x;
            int finishLocY = finishP.y;
            oyunBoard.setStart(new Point(startLocX,startLocY));
            oyunBoard.setFinish(new Point(finishLocX,finishLocY));

                oyunUI.grid[startLocX][startLocY].setBackground(Color.blue);
                oyunUI.grid[finishLocX][finishLocY].setBackground(Color.green);     
            // ----------------------------------------------------- //
            fileWrite();
            // ----------------------------------------------------- //
            Q_Learning q = new Q_Learning(oyunBoard);
         
            q.run();
            q.printResult(); 
            //ArrayList <Point> pointAr = q.showPolicy();
            //oyunUI.cizme(q.pointler);
            q.showPolicy(); 
            oyunUI.yolcizme(q.Y);
            gecici1 = q.kazanclar;
            gecici2 = q.maliyetler;
            gecici3 = q.pointler;
            }
           
          }
        }else if (source ==reset){
        oyun.removeAll();     
        oyunBoard = new Oyun(boardx,boardy);
        oyunUI = new OyunUI(oyunBoard);
        oyun.add(oyunUI);
        oyun.repaint();
        oyun.revalidate();
          
        }else if (source ==  grafik1){
           new GrafikCizme(gecici1,gecici2,deger);
    
        } else if (source == grafik2){
           new GrafikCizme(gecici3);
        }
    }
    public Point findGrid(int s){
        Point p = null;
        for(int i=0;i<oyunBoard.getLines();i++){
            for(int j=0;j<oyunBoard.getCols();j++){
                if(oyunBoard.getGrid()[i][j].getSira() == s){
                    p = new Point(i,j);
                }
            }
        }
        return p ;
    }

 
    
    
    
    
  
    
    
}
