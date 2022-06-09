/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import struct.*;
import main.*;
/**
 *
 * @author ayaz
 */
public class GameUI extends JPanel implements MouseListener{
    /**
     * 
     */
    private NodeUI[][] _grid;
    private Game _game;
    private GridBagConstraints _gbc;
    private GridBagLayout _gbl;
    private Node _current_Node;
    public Color _dbg;
    public JLabel _start_Text;
    public JLabel _finish_Text;
    public JLabel _value_Text;
    public Board _board;
    
    public double [][] _Q_matris;
    public int [][] _R_matris;
    public final double _alpa_Parameter = 0.1d;
    public final double _gamma_Parameter = 0.9d;
    public int [][] _Actions_matris;
    public int _temp_Index;
    
    public GameUI (Game _game){
        this._game = _game;
        this._gbl = new GridBagLayout();
        this.setLayout(_gbl);
        this._grid = new NodeUI[_game.getRows()][_game.getCols()];
        this._gbc = new GridBagConstraints();
        this._dbg = getBackground();
        this._Q_matris = new double [2][3];
        this._R_matris = new int [2][3];
        this._Actions_matris = new int [6][];

        _reset_Game_Board();
        this.paint(this.getGraphics());
        
        System.out.println("Board initialized...");
    }
    
    public void _reset_Game_Board (){
        Point _new_Point;
        NodeUI _new_Node;
        for(int _rows = 0 ; _rows < _game.getRows(); _rows++){
            for(int _cols =0; _cols < _game.getCols(); _cols++){
                _new_Point = new Point(_rows,_cols);
                if(_grid[_rows][_cols] == null){
                    _new_Node = new NodeUI(_game.getGrid()[_rows][_cols].getP());
                    _grid[_rows][_cols] = _new_Node;
                }
                _gbc.gridx = _cols;
                _gbc.gridy = _rows;
                this.setBackground(_dbg);
                _value_Text = new JLabel();
                _value_Text.setText(Integer.toString(_game.getGrid()[_rows][_cols].getSequence()));
                _grid[_rows][_cols].add(_value_Text);
                _grid[_rows][_cols].addMouseListener(this);
                _grid[_rows][_cols].setBackground(_dbg);
                
                Border border = null;
                if(_rows < _game.getRows() -1){
                    if(_cols < _game.getCols() -1)
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    else
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY); 
                }else{
                    if(_cols < _game.getCols() -1 )
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    else
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                }
                _grid[_rows][_cols].setBorder(border);
                this.add(_grid[_rows][_cols],_gbc);
            }
        }
    }
    
    public void _reset_Board_Background(Game _game, GameUI _game_UI){
         for(int _rows = 0 ; _rows < _game.getRows(); _rows++){
            for(int _cols =0; _cols < _game.getCols(); _cols++){
                if(_game.getGrid()[_rows][_cols].isWall())
                    continue;
                else
                    _game_UI.getGrid()[_rows][_cols].setBackground(_game_UI._dbg);
            }
         }
    }
    
    
    public void _print_Ways( int [] _Y_matris){
        int _start_State = _game.getGrid()[_game.getStart_Point().x][_game.getStart_Point().y].getSequence();
        int _end_State = _game.getGrid()[_game.getEnd_Point().x][_game.getEnd_Point().y].getSequence();
        int _state = _start_State;
        
        while(_state != _end_State){
            _state = _Y_matris[_state];
            if(_state == _end_State)
                continue;
            Point _temp_P = _game._find_Grid_Point(_game, _state);
            _grid[_temp_P.x][_temp_P.y].setBackground(Color.gray);
        }
    }
    
    
    
    
    
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        NodeUI _new_Panel = (NodeUI)e.getSource();
        if(_game.getGrid()[_new_Panel._p.x][_new_Panel._p.y].isWall()){
            _game.getGrid()[_new_Panel._p.x][_new_Panel._p.y].setWall(false);
            _new_Panel.setBackground(_dbg);
        }else{
            _game.getGrid()[_new_Panel._p.x][_new_Panel._p.y].setWall(true);
            _new_Panel.setBackground(Color.RED);
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

    /**
     * @return the _grid
     */
    public NodeUI[][] getGrid() {
        return _grid;
    }

    /**
     * @param _grid the _grid to set
     */
    public void setGrid(NodeUI[][] _grid) {
        this._grid = _grid;
    }
    
}
