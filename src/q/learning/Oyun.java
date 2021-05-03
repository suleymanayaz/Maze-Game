/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q.learning;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JPanel;

/**
 *
 * @author AYAZ
 */
public class Oyun {
    private int lines,cols;
    private Node[][] grid;
    private Point start,finish;
    Board board;
    int counter = 0;
    public Oyun(int lines,int cols){
        this.lines = lines;
        this.cols=cols;
        this.grid = new Node[lines][cols];
        this.start = new Point(0,0);
        this.finish = new Point(0,0);
        initGrid(); 
    }

    private void initGrid(){
       
        for(int i = 0;i<lines;i++){
                for(int j =0;j<cols;j++ ){
                    Node n = new Node(i,j);
                    grid[i][j]= n;
                    grid[i][j].setSira(counter);
                    counter++;
                }
        }
    }
   
    
   
    
    
    public Node getNode(Point p) {
        return grid[p.x][p.y];
    }
    public int getLines(){
        return lines;
    }
    
    public int getCols(){
        return cols;
    }
    
    public Node[][] getGrid(){
        return grid;
    }

    /**
     * @return the start
     */
    public Point getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Point start) {
        this.start = start;
    }

    /**
     * @return the finish
     */
    public Point getFinish() {
        return finish;
    }

    /**
     * @param finish the finish to set
     */
    public void setFinish(Point finish) {
        this.finish = finish;
    }

    /**
     * @return the settingBool
     */

    
    
}
