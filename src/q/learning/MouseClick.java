/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q.learning;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class MouseClick extends MouseAdapter {
    OyunUI oyunUi;
    public Node tempNode;
    public MouseClick(Node m){
        tempNode = m;     
    }
     public void mouseClicked(MouseEvent e) {
         NodeUI p = (NodeUI)e.getSource();
         System.out.println(p.p);
      tempNode.setDuvar(true);
      System.out.println("Clicked + "+tempNode.p);
    }
    
}
