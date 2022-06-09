/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import UI.GameUI;
import java.awt.Color;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import struct.Game;
import struct.Node;

/**
 *
 * @author ayaz
 */
public class Q_Learning {
    /**
     * 
     */
    
    public final DecimalFormat _df = new DecimalFormat("#.##");
    public Game _game;
    public GameUI _game_UI;
    public final double _alpha = 0.8d;
    public final double _gamma = 0.9d;
    public int _states_Count;
    public double [][] _Q_matris;
    public int [][] _R_matris;
    public int [] _Y_matris;
    public int _state_Finish;
    public int _wall_Count;
    ArrayList<Point> _point;
    ArrayList<ArrayList<Point>> _points;
    ArrayList<ArrayList<Integer>> _gains;
    ArrayList<Integer> _gain;
    ArrayList<Double> _cost,_costs;
    
    public Q_Learning(Game _game, GameUI _game_UI){
        this._game = _game;
        this._game_UI = _game_UI;
        this._states_Count = _init_Sequence();
        this._R_matris = new int [this._states_Count] [this._states_Count];
        this._Q_matris = new double [this._states_Count] [this._states_Count];
        this._Y_matris = new int [this._states_Count + 1];
        this._points = new ArrayList<>();
        this._gains = new ArrayList<>();
        this._costs = new ArrayList<>();
        
        _find_Neighbour();
        _initial_Algorithm();
    }
    
    public void _initial_Algorithm(){
        this._state_Finish = _game.getGrid()[_game.getEnd_Point().x][_game.getEnd_Point().y].getSequence();
        
        for (Node _temp_Node : _game.getGrid()[_game.getEnd_Point().x][_game.getEnd_Point().y].getNeighbours()){
            if(!_game.getGrid()[_temp_Node.getP().x][_temp_Node.getP().y].isWall()){
                this._R_matris[_temp_Node.getSequence()][_state_Finish] = 100;
            }
        }
        
        for (int _rows = 0; _rows < _states_Count; _rows++) {
            for (int _cols = 0; _cols < _states_Count; _cols++) {
                if(this._R_matris[_rows][_cols] == 0){
                    if(_rows != _cols)
                        this._R_matris[_rows][_cols] = -1;
                }
                //System.out.print(this._R_matris[_rows][_cols]);
            }
            //System.out.println("");
        }
        
        
    }
    
    public void _find_Neighbour(){
        int _temp_Start;
        int _temp_End;
        
         for(int _rows = 0 ; _rows < this._game.getRows(); _rows++){
            for(int _cols =0; _cols < this._game.getCols(); _cols++){
                if( _rows -1 >= 0){
                    if(_game.getGrid()[_rows -1 ][_cols].isWall() == false){
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows - 1][_cols].getSequence();
                        _R_matris[_temp_Start][_temp_End] = 3;
                    }else{
                        _wall_Count = _wall_Count + 1;
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows - 1][_cols].getSequence();
                        _R_matris[_temp_Start][_temp_End] = -5;
                    }
                    _game.getGrid()[_rows][_cols].getNeighbours().add(_game.getGrid()[_rows -1 ][_cols]);
                    
                }
                
                if ( _rows + 1 < _game.getRows()) {
                     if(_game.getGrid()[_rows + 1 ][_cols].isWall() == false){
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows + 1][_cols].getSequence();
                        _R_matris[_temp_Start][_temp_End] = 3;
                     }else{
                        _wall_Count = _wall_Count + 1;
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows + 1][_cols].getSequence();
                        _R_matris[_temp_Start][_temp_End] = -5;
                     }
                    _game.getGrid()[_rows][_cols].getNeighbours().add(_game.getGrid()[_rows +1 ][_cols]);

                   
                }
                
                if (_cols - 1 >= 0){
                     if(_game.getGrid()[_rows  ][_cols -1 ].isWall() == false){
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows ][_cols -1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = 3;
                     }else{
                        _wall_Count = _wall_Count + 1;
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows ][_cols -1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = -5;
                     }
                    _game.getGrid()[_rows][_cols].getNeighbours().add(_game.getGrid()[_rows ][_cols -1 ]);
                     
                }
                
                if (_cols + 1 < _game.getCols()){
                    if(_game.getGrid()[_rows  ][_cols + 1 ].isWall() == false){
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows ][_cols + 1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = 3;
                     }else{
                        _wall_Count = _wall_Count + 1;
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows ][_cols + 1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = -5;
                     }
                    _game.getGrid()[_rows][_cols].getNeighbours().add(_game.getGrid()[_rows ][_cols + 1 ]);
                      
                }
                if( _rows -1 >= 0 && _cols + 1 < _game.getCols()){
                    if(_game.getGrid()[_rows -1 ][_cols + 1 ].isWall() == false){
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows -1 ][_cols + 1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = 3;
                     }else{
                        _wall_Count = _wall_Count + 1;
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows -1 ][_cols + 1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = -5;
                     }
                    _game.getGrid()[_rows][_cols].getNeighbours().add(_game.getGrid()[_rows -1 ][_cols + 1 ]);
                     
                }
                if( _rows -1 >=0 && _cols -1 >= 0){
                    if(_game.getGrid()[_rows - 1  ][ _cols - 1].isWall() == false){
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows - 1 ][_cols - 1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = 3;
                     }else{
                        _wall_Count = _wall_Count + 1;
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows - 1 ][_cols - 1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = -5;
                     }
                    _game.getGrid()[_rows][_cols].getNeighbours().add(_game.getGrid()[_rows - 1 ][_cols - 1 ]);
                     
                }
                
                if( _rows + 1 < _game.getRows() && _cols + 1 < _game.getCols()){
                    if(_game.getGrid()[_rows + 1 ][_cols + 1 ].isWall() == false){
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows + 1 ][_cols + 1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = 3;
                     }else{
                        _wall_Count = _wall_Count + 1;
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows + 1 ][_cols + 1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = -5;
                     }
                    _game.getGrid()[_rows][_cols].getNeighbours().add(_game.getGrid()[_rows + 1 ][_cols + 1 ]);
                     
                }
                
                if( _rows + 1 < _game.getRows() && _cols - 1 >= 0){
                    if(_game.getGrid()[_rows + 1 ][_cols - 1 ].isWall() == false){
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows + 1][_cols - 1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = 3;
                     }else{
                        _wall_Count = _wall_Count + 1;
                        _temp_Start = _game.getGrid()[_rows][_cols].getSequence();
                        _temp_End = _game.getGrid()[_rows + 1 ][_cols - 1 ].getSequence();
                        _R_matris[_temp_Start][_temp_End] = -5;
                     }
                    _game.getGrid()[_rows][_cols].getNeighbours().add(_game.getGrid()[_rows + 1 ][_cols - 1 ]);
                     
                }
            }
            
            
         }
        
    }
    
    public int _init_Sequence(){
        int _return_Sequence = 0;
         for(int _rows = 0 ; _rows < this._game.getRows(); _rows++){
            for(int _cols =0; _cols < this._game.getCols(); _cols++){
                _return_Sequence = _return_Sequence + 1;
            }
         }
          return _return_Sequence;
    }
   
    
    public void _move (){
        Random _rnd = new Random();
        int _temp_Iteration_Number;
        
        if(_game.getCols() > _game.getRows() ){
            _temp_Iteration_Number = _game.getCols() * _game.getCols();
        } else if ( _game.getCols() < _game.getRows() ){
            _temp_Iteration_Number = _game.getRows() * _game.getRows();
        }else {
            _temp_Iteration_Number = _game.getRows() * _game.getCols();
        }
       
        
        for (int _temp_Counter = 0; _temp_Counter < _temp_Iteration_Number ; _temp_Counter++){
            _gain = new ArrayList<>();
            _cost = new ArrayList<>();
            _point = new ArrayList<>();
            
            
            
            int _state = _game.getGrid()[_game.getStart_Point().x][_game.getStart_Point().y].getSequence();
            
            while (_state != _state_Finish){
               
                Point _temp_P = _game._find_Grid_Point(_game, _state);
                
                int _index = _rnd.nextInt(_game.getGrid()[_temp_P.x][_temp_P.y].getNeighbours().size());
                int _action = _game.getGrid()[_temp_P.x][_temp_P.y].getNeighbours().get(_index).getSequence();
                Point _action_Point = _game._find_Grid_Point(_game, _action);
                
                _point.add(_action_Point);
                
                int _next_State = _action;
                double _q = _Q_matris(_state,_action);
                double _max_Q = _max_Q(_next_State);
                int _r = _R_matris(_state,_action);
                
                if(!_gain.contains(_r)){
                    _gain.add(_r);
                }
                
                if(_next_State == _state_Finish){
                    double _value = _r;
                    _cost.add(_value);
                    _set_Q_matris(_state,_action,_value);
                    _state = _next_State;
                }else{
                    double _value = (_r + (_gamma * (_max_Q) ));
                    if(!_cost.contains(_value))
                        _cost.add(_value);
                    _set_Q_matris(_state,_action,_value);
                    
                    if(!_game.getGrid()[_action_Point.x][_action_Point.y].isWall()){
                        _state = _next_State;
                    }          
                    else
                        _state = _game.getGrid()[_game.getStart_Point().x][_game.getStart_Point().y].getSequence();
                }
                
            }
            _gains.add(_gain);
            double _temp_d = 0.d;
            for(double _temp_c : _cost){
                _temp_d = _temp_d + _temp_c;
            }
            _costs.add(_temp_d);
            _points.add(_point);
        }
    }
    
    public double _Q_matris(int _s, int _a){
        return _Q_matris[_s][_a];
    }
    public void _set_Q_matris(int _s, int _a, double _value){
        _Q_matris[_s][_a] = _value;
    }
    
    public int _R_matris(int _s, int _a){
        return _R_matris[_s][_a];
    }
    
    public void _set_R_matris(int _s, int _a, int _value){
        _R_matris[_s][_a] = _value;
    }
    
    public void _set_Y_matris (int _s, int _a ){
        _Y_matris[_s] = _a;
    }
   
    
    public double _max_Q(int _s){
        double _max_Value = Double.MIN_VALUE;
        ArrayList<Node> _temp_Neighbour = _game.getGrid()[_game._find_Grid_Point(_game, _s).x][_game._find_Grid_Point(_game, _s).y].getNeighbours();
        for (int i = 0; i < _temp_Neighbour.size(); i++) {
            int _next_State = _temp_Neighbour.get(i).getSequence();
            double _value = _Q_matris[_s][_next_State];
            if (_value > _max_Value) {
                _max_Value = _value;
            }
        }
        return _max_Value;
    }
    
    
    public void _print_Result (){
       System.out.println("Print result");
        for (int i = 0; i < _Q_matris.length; i++) {
            System.out.print("out from " + i + ":  ");
            for (int j = 0; j < _Q_matris[i].length; j++) {
                System.out.print(_df.format(_Q_matris[i][j]) + " ");
            }
            System.out.println();
        } 
    }
    
    public int _policy(int _state) {
        double _max_Value = Double.MIN_VALUE;
        Point _p = _game._find_Grid_Point(_game, _state);
        int _policy_Go_to_State = _state;
        ArrayList<Node> _temp_Node = _game.getGrid()[_p.x][_p.y].getNeighbours();
        for (int i = 0; i < _temp_Node.size(); i++) {
            int _next_State = _temp_Node.get(i).getSequence();
            double _value = _Q_matris[_state][_next_State];
            if (_value  > _max_Value) {
                _max_Value =_value ;
                _policy_Go_to_State = _next_State;
            }
        }
        return _policy_Go_to_State;
    }
    
    public ArrayList<Point> _show_Policy() {
        System.out.println("\nshowPolicy");
        ArrayList<Point> _point_Ar = new ArrayList();
        for (int i = 0; i < _game.getRows(); i++) {
            for (int j = 0; j < _game.getCols(); j++) {
                if (!_game.getGrid()[i][j].isWall()) {
                    int from = _game.getGrid()[i][j].getSequence();
                    int to = _policy(from);
                    Point p = _game._find_Grid_Point(_game, to);
                    //pointAr.add(oyun.getGrid()[i][j].p);
                    //pointAr.add(p);
                    // index [0] = 1 
                    _set_Y_matris(_game.getGrid()[i][j].getSequence(), _game.getGrid()[p.x][p.y].getSequence());
                    System.out.println("From :" + _game.getGrid()[i][j].getSequence()+ " --> GO TO :" + _game.getGrid()[p.x][p.y].getSequence());
                }

            }
        }
        return _point_Ar;
    }
    
    
}


