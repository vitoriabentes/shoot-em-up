package graphics;

import javax.swing.JFrame;
import java.awt.Graphics;


@SuppressWarnings("serial")
class MyFrame extends JFrame {

    public MyFrame(String title){

        super(title);
    }

    public void paint(Graphics g){ }

    public void update(Graphics g){ }

    public void repaint(){ }
}