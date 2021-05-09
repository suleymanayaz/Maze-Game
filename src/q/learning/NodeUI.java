/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q.learning;

/**
 *
 * @author AYAZ
 */


import javax.swing.*;
import java.awt.*;
public class NodeUI extends JPanel {
    
  public Point p;
  
  public NodeUI (Point p){
      this.p = p;
  }
  
  @Override
  public Dimension getPreferredSize(){
      return new Dimension(50,50);
  }

}
