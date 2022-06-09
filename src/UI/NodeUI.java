
package UI;

/**
 *
 * @author ayaz
 */
import java.awt.*;
import javax.swing.*;

public class NodeUI extends JPanel{
    public Point _p;
    
    public NodeUI (Point _p){
       this._p = _p;
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(35,35);
    }
}
