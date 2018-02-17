package tetris2018;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

public class NextFig extends JComponent {
int rand = 0;
Graphics2D g2d;        
        
    @Override
    protected void paintComponent(Graphics g) {      
        super.paintComponent(g);
        g2d = (Graphics2D) g;

        switch (rand) {
            case 0: {
            g2d.fillRect(30, 30, 40, 40);
            break;            
            }
            case 1: {
            g2d.fillRect(40, 10, 20, 80);
            break;            
            }
            case 2: {
            g2d.fillRect(35, 20, 20, 60);
            g2d.fillRect(55, 20, 20, 20);
            break;            
            }
            case 3: {
            g2d.fillRect(30, 40, 20, 40);
            g2d.fillRect(50, 20, 20, 40);
            break;            
            }
            case 4: {
            g2d.fillRect(20, 30, 60, 20);
            g2d.fillRect(40, 50, 20, 20);
            break;            
            }
        
        }
    }
}
