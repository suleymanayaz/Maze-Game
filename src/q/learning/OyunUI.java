/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q.learning;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

/**
 *
 * @author AYAZ
 */
public class OyunUI extends JPanel implements MouseListener{
    public  NodeUI[][] grid;
    private Oyun oyun;
    private GridBagConstraints gbc;
    private GridBagLayout gbl;
    private Node currentNode;
    public Color dbg;
    JLabel start,finish,value;
    Board board;
    double [][] Q = new double[2][3];
    int [][] R = new int[2][3];
    final double alpha = 0.1;
    final double gamma = 0.9;
    int [][] actions = new int [6][];
    int indexA = 0;
    public OyunUI(Oyun oyun){
        this.oyun = oyun;
        gbl = new GridBagLayout();
        setLayout(gbl);
        this.grid= new NodeUI[oyun.getLines()][oyun.getCols()];
        gbc = new GridBagConstraints();
        dbg = getBackground();
        reset();
        paint(getGraphics());       
        System.out.println("HARİTA OLUSTURULDU!!!");
    }
     public NodeUI[][] getGridUI(){
        return this.grid;
    }
      public void randomStart(){
        int randIntx =0,randInty = 0;
        while(randIntx !=0 && randInty !=0){
           randIntx=((int)(Math.random()*oyun.getLines()-1));
           randInty=((int)(Math.random()*oyun.getCols()-1));
           if(!oyun.getGrid()[randIntx][randInty].isDuvar()){
               oyun.setStart(new Point(randIntx,randInty));
           }else{
               randIntx=0;randInty=0;
           }
        }
        
      }
      public void randomFinish(){
        int randIntx =0,randInty = 0;
        while(randIntx !=0 && randInty !=0){
           randIntx=((int)(Math.random()*oyun.getLines()-1));
           randInty=((int)(Math.random()*oyun.getCols()-1));
           if(!oyun.getGrid()[randIntx][randInty].isDuvar()){
               oyun.setFinish(new Point(randIntx,randInty));
           }else{
               randIntx=0;randInty=0;
           }
        }
        
      }
      public void reset(){
        NodeUI n;
     
        for(int i = 0;i < oyun.getLines(); i++){
            for(int j = 0;j < oyun.getCols(); j++){
                Point newPoint = new Point(i,j);
                if(grid[i][j] == null){
                    n = new NodeUI(oyun.getGrid()[i][j].p);
                    grid[i][j] = n;
                }
                gbc.gridx = j; 
                gbc.gridy = i;
                setBackground(dbg);
                value = new JLabel();
                value.setText(Integer.toString(oyun.getGrid()[i][j].getSira()));
                grid[i][j].add(value);
                grid[i][j].addMouseListener(this);
                grid[i][j].setBackground(dbg);
                   
                Border border = null;
                if( i < oyun.getLines() - 1){
                    if(j < oyun.getCols() - 1){
                        border = new MatteBorder(1, 1, 0, 0, Color.DARK_GRAY);
                    } else{
                        border = new MatteBorder(1, 1, 0, 1, Color.DARK_GRAY);
                    }
                }
                else{
                    if(j < oyun.getCols() -1){
                        border = new MatteBorder(1, 1, 1, 0, Color.DARK_GRAY);
                    } else{
                        border = new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY);
                    }
                }
                grid[i][j].setBorder(border);
                add(grid[i][j],gbc);  
              
            }
        } 
    }
      
      public void cizme(ArrayList<Point> pointArr){
           for(Point p : pointArr){
               if(oyun.getStart().equals(p)){
                   grid[p.x][p.y].setBackground(Color.yellow);
                    this.paintAll(getGraphics());
                     try {
                 Thread.sleep(100); // delay
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                  grid[p.x][p.y].setBackground(Color.blue);    
               }else if ( oyun.getFinish().equals(p)){
                   grid[p.x][p.y].setBackground(Color.yellow);
                    this.paintAll(getGraphics());
                     try {
                 Thread.sleep(100); // delay
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                  grid[p.x][p.y].setBackground(Color.GREEN);
               }else{
                     grid[p.x][p.y].setBackground(Color.yellow);
                      this.paintAll(getGraphics());
                  try {
                 Thread.sleep(100); // delay
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                }  
             
                grid[p.x][p.y].setBackground(dbg);
                
               }
             
            }
      }
      
      public void yolcizme(int [] Y){
          int start = oyun.getGrid()[oyun.getStart().x][oyun.getStart().y].getSira();
          int finish = oyun.getGrid()[oyun.getFinish().x][oyun.getFinish().y].getSira();
          int state = start;
          while(state != finish){
              state = Y[state];
              if(state == finish){
                  continue;
              }
              Point p = findGrid(state);
              grid[p.x][p.y].setBackground(Color.gray);
          }
      }
       public Point findGrid(int s){
        Point p = null;
        for(int i=0;i<oyun.getLines();i++){
            for(int j=0;j<oyun.getCols();j++){
                if(oyun.getGrid()[i][j].getSira() == s){
                    p = new Point(i,j);
                }
            }
        }
        return p ;
    }
      
 
      
    @Override
    public void mouseClicked(MouseEvent e) {
       NodeUI panel = (NodeUI)e.getSource();
       if(oyun.getGrid()[panel.p.x][panel.p.y].isDuvar()){
           oyun.getGrid()[panel.p.x][panel.p.y].setDuvar(false);
           panel.setBackground(dbg);
       }else{
           oyun.getGrid()[panel.p.x][panel.p.y].setDuvar(true);
           panel.setBackground(Color.RED);
       }
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
    
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
