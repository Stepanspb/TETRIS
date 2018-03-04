package tetris2018;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI extends JFrame {

    int size = 30;
    int count_x = 12;
    int count_y = 20;
    int ORIGIN_POSITION_X = 5,
            ORIGIN_POSITION_Y = 0;
    JFrame frame;
    JPanel field = new JPanel(new BorderLayout());
    JPanel nextfig = new JPanel(new BorderLayout());
    JPanel menu;
    JLabel point;
    JButton timer_driver;
    JButton recordShow;
    Process fig;
    NextFig fignext;
    boolean TimerRun = false;
    Font default_font = new Font("Impact", Font.ROMAN_BASELINE, 16);
    Font default_font2 = new Font("Arial", Font.ITALIC, 12);

    ActionListener timeraction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TimerRun == false) {
                fig.start();
                timer_driver.setText("STOP");
            } else {
                fig.stop();
                timer_driver.setText("START");
            }
        }
    };

    ActionListener recordtable = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame recordsFrame = new JFrame("Records");
            recordsFrame.setResizable(false);
            recordsFrame.setSize(500, 205);
            recordsFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            recordsFrame.setVisible(true);
            JPanel jp = new JPanel(new BorderLayout());
            jp.setPreferredSize(new Dimension(500, 450));
            jp.setBackground(Color.WHITE);
            JTable table = new JTable(11, 3);
            table.setFont(default_font);
            table.setValueAt("№", 0, 0);
            table.setValueAt("Name", 0, 1);
            table.setValueAt("Points", 0, 2);
            for (int i = 1; i < 11; i++) {
                table.setValueAt(i, i, 0);
            }
            for (int i = 0; i < fig.table.list.length; i++) {
                table.setValueAt(fig.table.list[i], i + 1, 1);
                if (fig.table.list[i] != null) {
                    table.setValueAt(fig.table.list[i].getPoints(), i + 1, 2);
                }
            }
            jp.add(table);
            recordsFrame.add(jp);
        }
    };

    void setfig(Process fig) {
        this.fig = fig;
    }

    public GUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame = new JFrame("TETRIS");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(new Dimension(550, 630));
        frame.setResizable(false);
        JPanel game_field = new JPanel(new BorderLayout());
        game_field.setSize(new Dimension(550, 620));
        game_field.setBackground(Color.LIGHT_GRAY.darker());
        menu = new JPanel();
        menu.setPreferredSize(new Dimension(180, 600));
        menu.setBackground(Color.LIGHT_GRAY);
        menu.setBorder(BorderFactory.createLineBorder(Color.black));
        point = new JLabel();
//        point.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        point.setPreferredSize(new Dimension(100, 50));
        String p = new String("Point: " + String.valueOf(Process.points));
        point.setText(p);
        point.setFont(default_font);
        menu.add(point);
        JLabel text1 = new JLabel("Next Figure:");
        text1.setPreferredSize(new Dimension(100, 30));
        text1.setFont(default_font);
        point.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        menu.add(text1);
        field.setPreferredSize(new Dimension(size * count_x, size * count_y));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.black));
        nextfig.setPreferredSize(new Dimension(100, 100));
        nextfig.setBackground(Color.white);
        nextfig.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        menu.add(nextfig);
        fignext = new NextFig();
        nextfig.add(fignext);
        timer_driver = new JButton("STOP");
        timer_driver.setFont(default_font);
        timer_driver.setPreferredSize(new Dimension(90, 50));
        timer_driver.addActionListener(timeraction);
        timer_driver.setFocusable(false);
        recordShow = new JButton("Records");
        recordShow.setFont(default_font);
        recordShow.setPreferredSize(new Dimension(90, 50));
        recordShow.addActionListener(recordtable);
        recordShow.setFocusable(false);
        menu.add(timer_driver);
        menu.add(recordShow);
        Dimension dim = new Dimension(150, 20);
        JLabel howtoplay1 = new JLabel("              CONTROL :");
        howtoplay1.setFont(default_font);
        howtoplay1.setPreferredSize(dim);
        JLabel howtoplay2 = new JLabel("Arrows to move left, right ");
        howtoplay2.setFont(default_font2);
        howtoplay2.setPreferredSize(dim);
        JLabel howtoplay3 = new JLabel("and down. Space to rotate.");
        howtoplay3.setFont(default_font2);
        howtoplay3.setPreferredSize(dim);
        menu.add(howtoplay1);
        menu.add(howtoplay2);
        menu.add(howtoplay3);
        game_field.add(field, BorderLayout.WEST);
        game_field.add(menu, BorderLayout.EAST);
        frame.add(game_field);
        frame.setVisible(true);
    }

    void control() {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    fig.moveleft();
                    fig.clearposition_left();
                    fig.checkposition();
                    fig.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    fig.moveright();
                    fig.clearposition_right();
                    fig.checkposition();
                    fig.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    fig.movedown();
                    fig.clearposition_down();
                    fig.checkposition();
                    fig.repaint();

                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    fig.rotate(fig.getrand());
                    fig.checkposition();
                    fig.repaint();
                }

            }
        });
    }
}
