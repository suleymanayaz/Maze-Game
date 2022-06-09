/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package struct;

import java.awt.Point;
import main.*;

/**
 *
 * @author ayaz
 */
public class Game {
    /**
     * 
     */
    private int _rows;
    private int _cols;
    private Node [][] _grid;
    private Point _start_Point;
    private Point _end_Point;
    
    public Board _board;
    
    public Game (int _rows, int _cols){
        this._rows = _rows;
        this._cols = _cols;
        this._grid = new Node[_rows][_cols];
        this._start_Point = new Point(0,0);
        this._end_Point = new Point(0,0);
        _initial_Grid();
    }
    
    private void _initial_Grid(){
        Node _new_Node;
        int _temp_Counter = 0;
         for(int _rows = 0 ; _rows < this._rows; _rows++){
            for(int _cols =0; _cols < this._cols; _cols++){
                _new_Node = new Node(_rows,_cols);
                _grid[_rows][_cols] = _new_Node;
                getGrid()[_rows][_cols].setSequence(_temp_Counter);
                _temp_Counter = _temp_Counter + 1;
            }
         }
    }
    
     public Point _find_Grid_Point(Game _game, int _input){
        Point _return_Point = null;
        for(int _rows = 0 ; _rows < _game._rows; _rows++){
            for(int _cols =0; _cols < _game._cols; _cols++){
                if (_game.getGrid()[_rows][_cols].getSequence() == _input)
                    _return_Point = new Point(_rows,_cols);
            }
        }
        return _return_Point;
    }
     
    /**
     * @return the _rows
     */
    public int getRows() {
        return _rows;
    }

    /**
     * @return the _cols
     */
    public int getCols() {
        return _cols;
    }

    /**
     * @return the _grid
     */
    public Node[][] getGrid() {
        return _grid;
    }

    /**
     * @return the _start_Point
     */
    public Point getStart_Point() {
        return _start_Point;
    }

    /**
     * @return the _end_Point
     */
    public Point getEnd_Point() {
        return _end_Point;
    }

    /**
     * @param _start_Point the _start_Point to set
     */
    public void setStart_Point(Point _start_Point) {
        this._start_Point = _start_Point;
    }

    /**
     * @param _end_Point the _end_Point to set
     */
    public void setEnd_Point(Point _end_Point) {
        this._end_Point = _end_Point;
    }
}
