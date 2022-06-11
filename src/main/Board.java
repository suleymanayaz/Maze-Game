/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import struct.*;
import UI.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 *
 * @author ayaz
 */
public class Board extends JFrame implements ActionListener{
    /**
     * 
     * @param e 
     */
    public int _board_Lines;
    public int _board_Cols;
    public JPanel _board_Game, _board_Settings, _board_Generate, _board_Result, _board_Results, _board_Players, _board_Graph;
    public JButton _board_Game_Start_Button, _board_Game_Reset_Button,_board_Get_Graph_Button;
    public JTextField _board_Game_Player_Start_Locate, _board_Game_Player_End_Locate;
    public JLabel _board_Game_Player_Start_Locate_Text, _board_Game_Player_End_Locate_Text;
    public ChartPanel _chart_Panel_1, _chart_Panel_2;
    public Game _game_Board;
    public GameUI _game_UI;
    public File _file;
    public ArrayList<ArrayList<Integer>> _gains_List;
    public ArrayList<Double> _costs_List;
    public ArrayList<ArrayList<Point>> _points_List;
    public int _Y_matris[];
    public Board(){
        this.setLayout(new GridLayout(1, 3));
        this.setTitle("Q-Learning");
        this._board_Lines = 10;
        this._board_Cols = 10;
        this._board_Game = new JPanel();  
        this._game_Board = new Game(_board_Lines,_board_Cols);
        this._game_UI = new GameUI(_game_Board);
        this._board_Game.add(_game_UI);
        
        _set_Board_Settings();
        this._board_Game.add(_board_Settings);
        this.add(_board_Game);
        _create_Graph();
        
        //this.add(_board_Graph);
        this.setMinimumSize(new Dimension(720, 720));
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
   
    public void _create_Graph(){
        _board_Graph = new JPanel();
        _board_Graph.setLayout(new BoxLayout(_board_Graph,BoxLayout.Y_AXIS));
        _board_Graph.setSize(200, 200);
       
        XYSeries _series = new XYSeries("Graph");
        XYSeriesCollection _data_set = new XYSeriesCollection(_series);
        _data_set.setAutoWidth(true);
        JFreeChart _chart_Cost = ChartFactory.createXYBarChart("Episode via cost Graph", "Episode", false, "Cost", _data_set, PlotOrientation.VERTICAL, false, false, false);
        JFreeChart _chart_Cost_Step = ChartFactory.createXYBarChart("Episode via cost Step", "Episode", false, "Cost", _data_set, PlotOrientation.VERTICAL, false, false, false);
        _chart_Panel_1 = new ChartPanel(_chart_Cost);
        _chart_Panel_2 = new ChartPanel(_chart_Cost_Step);
        _board_Graph.add(_chart_Panel_1);
        _board_Graph.add(_chart_Panel_2);
    }
    
    public void _set_Board_Settings(){
        _board_Settings = new JPanel();
        _board_Settings.setLayout( new BoxLayout(_board_Settings,BoxLayout.Y_AXIS));
        _board_Settings.setBackground(Color.YELLOW);
        
        _board_Generate = new JPanel();
        _board_Generate.setLayout(new FlowLayout());
        
        _board_Game_Start_Button = new JButton("Start");
        _board_Game_Start_Button.addActionListener(this);
        
        _board_Game_Player_Start_Locate = new JTextField("Number", 5);
        _board_Game_Player_Start_Locate_Text = new JLabel("Start Location : ");
        _board_Game_Player_End_Locate = new JTextField("Number", 5);
        _board_Game_Player_End_Locate_Text = new JLabel("Finish Location : ");
        
        _board_Game_Reset_Button = new JButton("Reset");
        _board_Game_Reset_Button.addActionListener(this);
        
        _board_Get_Graph_Button = new JButton("Get Graph");
        _board_Get_Graph_Button.addActionListener(this);
        //
        
        JPanel _temp_Button_Panel = new JPanel();
        _temp_Button_Panel.add(_board_Game_Start_Button);
        _temp_Button_Panel.add(_board_Game_Reset_Button);
        _temp_Button_Panel.add(_board_Get_Graph_Button);
        
        JPanel _temp_Settings_Location_Panel = new JPanel();
        _temp_Settings_Location_Panel.setLayout(new FlowLayout());
        _temp_Settings_Location_Panel.add(_board_Game_Player_Start_Locate_Text);
        _temp_Settings_Location_Panel.add(_board_Game_Player_Start_Locate);
        _temp_Settings_Location_Panel.add(_board_Game_Player_End_Locate_Text);
        _temp_Settings_Location_Panel.add(_board_Game_Player_End_Locate);
        
        JPanel _temp_Settings_In = new JPanel();
        _temp_Settings_In.setLayout(new BoxLayout(_temp_Settings_In,BoxLayout.Y_AXIS));
        _temp_Settings_In.add(_temp_Settings_Location_Panel);
        
        _temp_Settings_In.add(_temp_Button_Panel);
        _board_Generate.add(_temp_Settings_In);
        _board_Generate.setBorder(BorderFactory.createTitledBorder("Settings"));
        _board_Settings.add(_board_Generate);
    }
    
    
    
   
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
         JButton _button_Source = (JButton) e.getSource();
         Frame _temp_Pop_Up = null;
        if(_button_Source == _board_Game_Start_Button){
            System.out.println("Start Locate : "+ _board_Game_Player_Start_Locate.getText());
            System.out.println("End Locate : "+ _board_Game_Player_End_Locate.getText());
            _game_UI._reset_Board_Background(_game_Board, _game_UI);           
            if(_board_Game_Player_Start_Locate.getText().equals("Number") ||_board_Game_Player_End_Locate.getText().equals("Number")){
                _temp_Pop_Up = new JFrame();
                JOptionPane.showMessageDialog(_temp_Pop_Up,"Start or Finish input invalid  location!!");
                
            }else{
                Point _input_Start_P = _game_Board._find_Grid_Point(_game_Board, Integer.parseInt(_board_Game_Player_Start_Locate.getText()));
                Point _input_End_P = _game_Board._find_Grid_Point(_game_Board, Integer.parseInt(_board_Game_Player_End_Locate.getText()));
               
                if(_input_Start_P == null || _input_End_P == null ||_game_Board.getGrid()[_input_Start_P.x][_input_Start_P.y].isWall() ||_game_Board.getGrid()[_input_End_P.x][_input_End_P.y].isWall() )
                    JOptionPane.showMessageDialog(_temp_Pop_Up,"Start or Finish input invalid  location!!");
                else{
                    _game_Board.setStart_Point(_input_Start_P);
                    _game_Board.setEnd_Point(_input_End_P);
                    _game_UI.getGrid()[_input_Start_P.x][_input_Start_P.y].setBackground(Color.blue);
                    _game_UI.getGrid()[_input_End_P.x][_input_End_P.y].setBackground(Color.green);
                    
                    // _file_Write();
                    
                    Q_Learning _q = new Q_Learning(_game_Board,_game_UI);
                    
                    _q._move();
                    //_q._print_Result();
                    _q._show_Policy();
                    
                    _game_UI._print_Ways(_q._Y_matris);
                    
                    _gains_List = _q._gains;
                    _points_List = _q._points;
                    _costs_List = _q._costs;
                    _Y_matris = _q._Y_matris;

                    
                }
            }
        }else if (_button_Source == _board_Game_Reset_Button){
            _board_Game.removeAll();
            _game_Board = new Game(_board_Lines,_board_Cols);
            _game_UI = new GameUI(_game_Board);
            _board_Game.add(_game_UI);
            _board_Game.add(_board_Settings);
            _board_Game.repaint();
            _board_Game.revalidate();
            
        }else{
            ChartPanel _chart_Panel_2 = new ChartPanel(_set_Graph_Cost(_costs_List));
            _board_Graph.removeAll();
            _board_Graph.add(_chart_Panel_2);
            _chart_Panel_2 = new ChartPanel(_set_Graph_Cost_Step(_points_List));
            _board_Graph.add(_chart_Panel_2);
            this.repaint();
            this.revalidate();
            
            this.add(_board_Graph);
            
            
        }
    }
    
    
    public JFreeChart _set_Graph_Cost (ArrayList<Double> _costs){
        int _temp_Counter = 0;
        XYSeries _series = new XYSeries("_series_1");
        for (Double _temp_Value : _costs){
            _temp_Counter = _temp_Counter + 1;
            _series.add(_temp_Counter,_temp_Value);
        }
         XYSeriesCollection _data_set = new XYSeriesCollection(_series);
         _data_set.setAutoWidth(true);
         JFreeChart  _chart = ChartFactory.createXYBarChart("Episode via cost Graph", "Episode", false, "Cost", _data_set, PlotOrientation.VERTICAL, false, false, false);
         XYPlot _plot = _chart.getXYPlot();
         XYLineAndShapeRenderer _renderer = new XYLineAndShapeRenderer();
         _renderer.setSeriesPaint(0, Color.BLUE);
         int _rnd = 0;
         _renderer.setSeriesStroke(_rnd, new BasicStroke(0.1f));
         _renderer.setDrawOutlines(false);
         _plot.getDomainAxis().setUpperMargin(0);
         _plot.setRangeGridlinesVisible(true);
         _plot.setRangeGridlinePaint(Color.BLACK);
         return _chart;
    }
    
    
    public JFreeChart _set_Graph_Cost_Step (ArrayList<ArrayList<Point>> _points){
          int _temp_Counter = 0;
        XYSeries _series = new XYSeries("Graph");
        for (ArrayList<Point> _temp_Point : _points){
            _temp_Counter = _temp_Counter + 1;
            _series.add(_temp_Counter,(double) _temp_Point.size());
        }
         XYSeriesCollection _data_set = new XYSeriesCollection(_series);
         _data_set.setAutoWidth(true);
         JFreeChart  _chart = ChartFactory.createXYBarChart("Episode via cost Step", "Episode", false, "Step", _data_set, PlotOrientation.VERTICAL, false, false, false);
         XYPlot _plot = _chart.getXYPlot();
         XYLineAndShapeRenderer _renderer = new XYLineAndShapeRenderer();
         _renderer.setSeriesPaint(0, Color.BLUE);
         int _rnd = 0;
         _renderer.setSeriesStroke(_rnd, new BasicStroke(0.5f));
         _renderer.setDrawOutlines(false);
         _plot.getDomainAxis().setUpperMargin(0);
         _plot.setRangeGridlinesVisible(true);
         _plot.setRangeGridlinePaint(Color.BLACK);
         return _chart;
         
    }
    
    public static void main(String[] args){
        new Board();
    }
}
