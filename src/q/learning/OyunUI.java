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
        if(oyun.getStart().x == 0 && oyun.getStart().y == 0){
            randomStart(); // ilklendirme yapılmadıysa
        }
        if(oyun.getFinish().x == 0 && oyun.getFinish().y == 0){
            randomFinish(); // ilklendirme yapılmadıysa
        }
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
                value.setText(Integer.toString(oyun.getGrid()[i][j].getValue()));
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
