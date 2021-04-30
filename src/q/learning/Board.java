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
    
    int boardx = 50,boardy=50;
    JPanel oyun,settings,generate,sonuc,sonuclar,oyuncular;
    JButton start;
    JTextField startLoc,finishLoc;
    JLabel startLocLab,finishLocLab;
    Oyun oyunBoard;
    OyunUI oyunUI;
    File file = new File("C:\\Users\\AYAZ\\Documents\\NetBeansProjects\\Q-Learning\\engel.txt");

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
        String string1 = "X,Y";
        startLoc =  new JTextField(string1,5);
        startLocLab = new JLabel("Start Location :  ");
        finishLoc = new JTextField(string1,5);
        finishLocLab = new JLabel("Finish Location : ");
        
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
                    }
                }
            }
           

            fw.close();
        }catch(IOException e){
            System.out.println("HATAA");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(startLoc.getText());
        System.out.println(finishLoc.getText());
        boolean giris = true;
        String [] startLocString = startLoc.getText().split(",",2);
        for(String s : startLocString){
            if(s.equals("X") || s.equals("Y")){
                Frame frameForPopUp = new JFrame();
                JOptionPane.showMessageDialog(frameForPopUp,"Start  or finish input invalid  location!!");
                giris = false; 
            }
        }
        if(giris){
            int startLocX = Integer.parseInt(startLocString[0]);
            int startLocY = Integer.parseInt(startLocString[1]);
            startLocString = finishLoc.getText().split(",",2);
            int finishLocX = Integer.parseInt(startLocString[0]);
            int finishLocY = Integer.parseInt(startLocString[1]);
            oyunBoard.setStart(new Point(startLocX,startLocY));
            oyunBoard.setFinish(new Point(startLocX,startLocY));
            JLabel start = new JLabel();
            start.setText("A");
            oyunUI.grid[startLocX][startLocY].setBackground(Color.blue);
            oyunUI.grid[startLocX][startLocY].add(start);  
            JLabel finish = new JLabel();
            finish.setText("B");
            oyunUI.grid[finishLocX][finishLocY].setBackground(Color.green);
            oyunUI.grid[finishLocX][finishLocY].add(finish);
            fileWrite();
        }
        
        
        
    }

 

    
    
    
    
  
    
    
}
