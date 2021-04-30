/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q.learning;

import java.awt.Point;

/**
 *
 * @author AYAZ
 */
public class Node {
    public Point p;
    private boolean duvar = false;
    private int value;
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
}
