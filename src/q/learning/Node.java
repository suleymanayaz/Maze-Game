/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q.learning;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author AYAZ
 */
public class Node {
    public Point p;
    private boolean duvar = false;
    private int value;
    private int sira;
    private int[] actionsFrom = new int[5];
    private ArrayList <Node> komsular = new ArrayList(); 
    public Node (int x,int y){
        this.p = new Point(x,y);
    }
    public boolean isDuvar(){
        return duvar;
    }
    public void setDuvar(boolean duvar){
        this.duvar=duvar;
    }
      public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the sira
     */
    public int getSira() {
        return sira;
    }

    /**
     * @param sira the sira to set
     */
    public void setSira(int sira) {
        this.sira = sira;
    }

    /**
     * @return the actionsFrom
     */
    public int[] getActionsFrom() {
        return actionsFrom;
    }

    /**
     * @param actionsFrom the actionsFrom to set
     */
    public void setActionsFrom(int[] actionsFrom) {
        this.actionsFrom = actionsFrom;
    }

    /**
     * @return the komsular
     */
    public ArrayList <Node> getKomsular() {
        return komsular;
    }
    
    public void addKomsular(Node grid){
        this.komsular.add(grid);
    }

    /**
     * @param komsular the komsular to set
     */
    public void setKomsular(ArrayList <Node> komsular) {
        this.komsular = komsular;
    }

  
}
