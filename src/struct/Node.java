/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package struct;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author ayaz
 */
public class Node {
    /**
     * 
     */
    private boolean _wall;
    private int _value;
    private int _sequence;
    private int [] _actions_From;
    private ArrayList <Node> _neighbours;
    private Point _p;
    public Node (int _x, int _y){
        this._p = new Point(_x,_y);
        _wall = false;
        _actions_From = new int[5];
        _neighbours = new ArrayList<>();
    }

    /**
     * @return the _wall
     */
    public boolean isWall() {
        return _wall;
    }

    /**
     * @param _wall the _wall to set
     */
    public void setWall(boolean _wall) {
        this._wall = _wall;
    }

    /**
     * @return the _value
     */
    public int getValue() {
        return _value;
    }

    /**
     * @param _value the _value to set
     */
    public void setValue(int _value) {
        this._value = _value;
    }

    /**
     * @return the _sequence
     */
    public int getSequence() {
        return _sequence;
    }

    /**
     * @param _sequence the _sequence to set
     */
    public void setSequence(int _sequence) {
        this._sequence = _sequence;
    }

    /**
     * @return the _actions_From
     */
    public int[] getActions_From() {
        return _actions_From;
    }

    /**
     * @param _actions_From the _actions_From to set
     */
    public void setActions_From(int[] _actions_From) {
        this._actions_From = _actions_From;
    }

    /**
     * @return the _neighbours
     */
    public ArrayList <Node> getNeighbours() {
        return _neighbours;
    }

    /**
     * @param _neighbours the _neighbours to set
     */
    public void setNeighbours(ArrayList <Node> _neighbours) {
        this._neighbours = _neighbours;
    }

    /**
     * @return the _p
     */
    public Point getP() {
        return _p;
    }

    /**
     * @param _p the _p to set
     */
    public void setP(Point _p) {
        this._p = _p;
    }
    
    
    
}
