package tetris2018;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Process extends JComponent implements ActionListener {

    boolean gamestart = false,  fig_exist = false;
    int[][] position = new int[12][25];
    static int points;
    int size = 30, rand_add = 0, rand;
    Timer timer;
    Graphics2D g2d;
    Figure fig;
    GUI gui;
    TableOfRecords table;

    void addGUI(GUI qui) {
        this.gui = qui;
    }

    public Process() {
        timer = new Timer(1500, this);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 25; j++) {
                position[i][j] = 0;
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("TABLE OF POINT"))) {
            try {
                table = (TableOfRecords) ois.readObject();
            } catch (ClassNotFoundException ex) {
            table = new TableOfRecords();              
            }
        } catch (FileNotFoundException ex) {  
            table = new TableOfRecords();
            System.err.println("Error: " + ex.getMessage());
        } catch (IOException ex) {
            table = new TableOfRecords();
         System.err.println("Error: " + ex.getMessage());
        }
    }

    void stop() {
        timer.stop();
        gui.TimerRun = false;
    }

    void start() {
        timer.start();
        gui.TimerRun = true;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (!fig_exist) {
            fig = paintFigure();
            fig_exist = true;
        }
        movedown();
        clearposition_down();
        checkposition();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        gamestart = true;
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 25; j++) {
                if (position[i][j] != 0) {
                    Rectangle fig = new Rectangle(i * size, (j * size) - 150, size, size);
                    g2d.setPaint(Color.BLACK);
                    g2d.fill(fig);
                }
            }
        }
    }

    Figure paintFigure() {
        rand = rand_add;
        Figure fig = new Figure(rand);
        fig_exist = true;
        rand_add = (int) (Math.random() * 5);
        gui.fignext.rand = rand_add;
        gui.fignext.repaint();
        return fig;
    }

    int getrand() {
        return rand;
    }

    void checkposition() {
        if (fig.brick_1[0][1] > -1 && fig.brick_1[0][1] < 25) {
            position[fig.brick_1[0][0]][fig.brick_1[0][1]] = 1;
        }
        if (fig.brick_2[0][1] > -1 && fig.brick_2[0][1] < 25) {
            position[fig.brick_2[0][0]][fig.brick_2[0][1]] = 1;
        }
        if (fig.brick_3[0][1] > -1 && fig.brick_3[0][1] < 25) {
            position[fig.brick_3[0][0]][fig.brick_3[0][1]] = 1;
        }
        if (fig.brick_4[0][1] > -1 && fig.brick_4[0][1] < 25) {
            position[fig.brick_4[0][0]][fig.brick_4[0][1]] = 1;
        }
    }

    void movedown() {
        if (down_is_posible()) {
            fig.brick_1[0][1] += 1;
            fig.brick_2[0][1] += 1;
            fig.brick_3[0][1] += 1;
            fig.brick_4[0][1] += 1;
        } else {
            try {
                endOfGame();
            } catch (IOException ex) {
                Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

   void moveright() {
        if (right_is_posible()) {
            fig.brick_1[0][0] += 1;
            fig.brick_2[0][0] += 1;
            fig.brick_3[0][0] += 1;
            fig.brick_4[0][0] += 1;
        }
    }

    void moveleft() {
        if (left_is_posible()) {
            fig.brick_1[0][0] -= 1;
            fig.brick_2[0][0] -= 1;
            fig.brick_3[0][0] -= 1;
            fig.brick_4[0][0] -= 1;
        }
    }

    void clearposition_down() {
        if (fig.brick_1[0][1] > 0) {
            position[fig.brick_1[0][0]][fig.brick_1[0][1] - 1] = 0;
        }
        if (fig.brick_2[0][1] > 0) {
            position[fig.brick_2[0][0]][fig.brick_2[0][1] - 1] = 0;
        }
        if (fig.brick_3[0][1] > 0) {
            position[fig.brick_3[0][0]][fig.brick_3[0][1] - 1] = 0;
        }
        if (fig.brick_4[0][1] > 0) {
            position[fig.brick_4[0][0]][fig.brick_4[0][1] - 1] = 0;
        }
    }

    void clearposition_left() {
        if (fig.brick_1[0][1] > 0) {
            position[fig.brick_1[0][0] + 1][fig.brick_1[0][1]] = 0;
        }
        if (fig.brick_2[0][1] > 0) {
            position[fig.brick_2[0][0] + 1][fig.brick_2[0][1]] = 0;
        }
        if (fig.brick_3[0][1] > 0) {
            position[fig.brick_3[0][0] + 1][fig.brick_3[0][1]] = 0;
        }
        if (fig.brick_4[0][1] > 0) {
            position[fig.brick_4[0][0] + 1][fig.brick_4[0][1]] = 0;
        }
    }

    void clearposition_right() {
        if (fig.brick_1[0][1] > 0) {
            position[fig.brick_1[0][0] - 1][fig.brick_1[0][1]] = 0;
        }
        if (fig.brick_2[0][1] > 0) {
            position[fig.brick_2[0][0] - 1][fig.brick_2[0][1]] = 0;
        }
        if (fig.brick_3[0][1] > 0) {
            position[fig.brick_3[0][0] - 1][fig.brick_3[0][1]] = 0;
        }
        if (fig.brick_4[0][1] > 0) {
            position[fig.brick_4[0][0] - 1][fig.brick_4[0][1]] = 0;
        }

    }

    private boolean down_is_posible() {
        boolean inside_1, inside_2, inside_3, inside_4, use;
        if (fig.brick_1[0][1] == 24 || fig.brick_2[0][1] == 24 || fig.brick_3[0][1] == 24 || fig.brick_4[0][1] == 24) {
            fig_exist = false;
            fig = paintFigure();
            fig_exist = true;
            clearline();
            return false;
        }
        if (fig.brick_1[0][1] < 0 || fig.brick_2[0][1] < 0 || fig.brick_3[0][1] < 0 || fig.brick_4[0][1] < 0) {
            return true;
        }
        inside_1 = check_inside_down(fig.brick_1[0][1], fig.brick_1[0][0]);
        inside_2 = check_inside_down(fig.brick_2[0][1], fig.brick_2[0][0]);
        inside_3 = check_inside_down(fig.brick_3[0][1], fig.brick_3[0][0]);
        inside_4 = check_inside_down(fig.brick_4[0][1], fig.brick_4[0][0]);
        if (!inside_1) {
            use = (position[fig.brick_1[0][0]][fig.brick_1[0][1] + 1] == 0);
            if (use == false) {
                fig_exist = false;
                fig = paintFigure();
                fig_exist = true;
                clearline();
                return use;
            }
        }
        if (!inside_2) {
            use = (position[fig.brick_2[0][0]][fig.brick_2[0][1] + 1] == 0);
            if (use == false) {
                fig_exist = false;
                fig = paintFigure();
                fig_exist = true;
                clearline();
                return use;
            }
        }
        if (!inside_3) {
            use = (position[fig.brick_3[0][0]][fig.brick_3[0][1] + 1] == 0);
            if (use == false) {
                fig_exist = false;
                fig = paintFigure();
                fig_exist = true;
                clearline();
                return use;
            }
        }
        if (!inside_4) {
            use = (position[fig.brick_4[0][0]][fig.brick_4[0][1] + 1] == 0);
            if (use == false) {
                fig_exist = false;
                fig = paintFigure();
                fig_exist = true;
                clearline();
                return use;
            }
        }
        return true;
    }

    private boolean left_is_posible() {
        boolean inside_1, inside_2, inside_3, inside_4, use;
        if (fig.brick_1[0][0] == 0 || fig.brick_2[0][0] == 0 || fig.brick_3[0][0] == 0 || fig.brick_4[0][0] == 0) {
            return false;
        }
        inside_1 = check_inside_left(fig.brick_1[0][1], fig.brick_1[0][0]);
        inside_2 = check_inside_left(fig.brick_2[0][1], fig.brick_2[0][0]);
        inside_3 = check_inside_left(fig.brick_3[0][1], fig.brick_3[0][0]);
        inside_4 = check_inside_left(fig.brick_4[0][1], fig.brick_4[0][0]);
        if (!inside_1) {
            use = (position[fig.brick_1[0][0] - 1][fig.brick_1[0][1]] == 0);
            if (use == false) {
                return use;
            }
        }
        if (!inside_2) {
            use = (position[fig.brick_2[0][0] - 1][fig.brick_2[0][1]] == 0);
            if (use == false) {
                return use;
            }
        }
        if (!inside_3) {
            use = (position[fig.brick_3[0][0] - 1][fig.brick_3[0][1]] == 0);
            if (use == false) {
                return use;
            }
        }
        if (!inside_4) {
            use = (position[fig.brick_4[0][0] - 1][fig.brick_4[0][1]] == 0);
            if (use == false) {
                return use;
            }
        }
        return true;
    }

    private boolean right_is_posible() {
        boolean inside_1, inside_2, inside_3, inside_4, use;
        if (fig.brick_1[0][0] == 11 || fig.brick_2[0][0] == 11 || fig.brick_3[0][0] == 11 || fig.brick_4[0][0] == 11) {
            return false;
        }
        inside_1 = check_inside_right(fig.brick_1[0][1], fig.brick_1[0][0]);
        inside_2 = check_inside_right(fig.brick_2[0][1], fig.brick_2[0][0]);
        inside_3 = check_inside_right(fig.brick_3[0][1], fig.brick_3[0][0]);
        inside_4 = check_inside_right(fig.brick_4[0][1], fig.brick_4[0][0]);
        if (!inside_1) {
            use = (position[fig.brick_1[0][0] + 1][fig.brick_1[0][1]] == 0);
            if (use == false) {
                return use;
            }
        }
        if (!inside_2) {
            use = (position[fig.brick_2[0][0] + 1][fig.brick_2[0][1]] == 0);
            if (use == false) {
                return use;
            }
        }
        if (!inside_3) {
            use = (position[fig.brick_3[0][0] + 1][fig.brick_3[0][1]] == 0);
            if (use == false) {
                return use;
            }
        }
        if (!inside_4) {
            use = (position[fig.brick_4[0][0] + 1][fig.brick_4[0][1]] == 0);
            if (use == false) {
                return use;
            }
        }
        return true;
    }

    private boolean check_inside_down(int brick_y, int brick_x) {
        if (brick_y + 1 == fig.brick_1[0][1]) {
            if (brick_x == fig.brick_1[0][0]) {
                return true;
            }
        }
        if (brick_y + 1 == fig.brick_2[0][1]) {
            if (brick_x == fig.brick_2[0][0]) {
                return true;
            }
        }
        if (brick_y + 1 == fig.brick_3[0][1]) {
            if (brick_x == fig.brick_3[0][0]) {
                return true;
            }
        }
        if (brick_y + 1 == fig.brick_4[0][1]) {
            if (brick_x == fig.brick_4[0][0]) {
                return true;
            }
        }
        return false;
    }

    private boolean check_inside_left(int brick_y, int brick_x) {
        if (brick_x - 1 == fig.brick_1[0][0]) {
            if (brick_y == fig.brick_1[0][1]) {
                return true;
            }
        }
        if (brick_x - 1 == fig.brick_2[0][0]) {
            if (brick_y == fig.brick_2[0][1]) {
                return true;
            }
        }
        if (brick_x - 1 == fig.brick_3[0][0]) {
            if (brick_y == fig.brick_3[0][1]) {
                return true;
            }
        }
        if (brick_x - 1 == fig.brick_4[0][0]) {
            if (brick_y == fig.brick_4[0][1]) {
                return true;
            }
        }
        return false;
    }

    private boolean check_inside_right(int brick_y, int brick_x) {
        if (brick_x + 1 == fig.brick_1[0][0]) {
            if (brick_y == fig.brick_1[0][1]) {
                return true;
            }
        }
        if (brick_x + 1 == fig.brick_2[0][0]) {
            if (brick_y == fig.brick_2[0][1]) {
                return true;
            }
        }
        if (brick_x + 1 == fig.brick_3[0][0]) {
            if (brick_y == fig.brick_3[0][1]) {
                return true;
            }
        }
        if (brick_x + 1 == fig.brick_4[0][0]) {
            if (brick_y == fig.brick_4[0][1]) {
                return true;
            }
        }
        return false;
    }

    private void rotate_check() {
        position[fig.brick_1[0][0]][fig.brick_1[0][1]] = 0;
        position[fig.brick_2[0][0]][fig.brick_2[0][1]] = 0;
        position[fig.brick_3[0][0]][fig.brick_3[0][1]] = 0;
        position[fig.brick_4[0][0]][fig.brick_4[0][1]] = 0;
    }

    void rotate(int rand) {
        switch (rand) {
            case 0:
                break;
            case 1: {
                if ((fig.brick_1[0][0] < 2 || fig.brick_1[0][0] > 9) && (fig.brick_4[0][0] < 2 || fig.brick_4[0][0] > 9)) {
                    break;
                }
                rotate_check();
                if (fig.brick_3[0][1] == fig.brick_1[0][1]) {
                    fig.brick_1[0][0] = fig.brick_3[0][0];
                    fig.brick_2[0][0] = fig.brick_3[0][0];
                    fig.brick_4[0][0] = fig.brick_3[0][0];
                    fig.brick_1[0][1] = fig.brick_1[0][1] + 2;
                    fig.brick_2[0][1] = fig.brick_2[0][1] + 1;
                    fig.brick_4[0][1] = fig.brick_4[0][1] - 1;
                    break;
                } else {
                    fig.brick_1[0][1] = fig.brick_3[0][1];
                    fig.brick_2[0][1] = fig.brick_3[0][1];
                    fig.brick_4[0][1] = fig.brick_3[0][1];
                    fig.brick_1[0][0] = fig.brick_1[0][0] - 2;
                    fig.brick_2[0][0] = fig.brick_2[0][0] - 1;
                    fig.brick_4[0][0] = fig.brick_4[0][0] + 1;
                    break;
                }
            }
            case 2: {
                if ((fig.brick_2[0][0] < 1 || fig.brick_2[0][0] > 10) && (fig.brick_4[0][0] < 1 || fig.brick_4[0][0] > 10)) {
                    break;
                }
                rotate_check();
                if (fig.brick_1[0][0] == fig.brick_3[0][0] + 1 && fig.brick_1[0][1] == fig.brick_3[0][1] - 1) {
                    fig.brick_1[0][0] = fig.brick_3[0][0] + 1;
                    fig.brick_2[0][0] = fig.brick_3[0][0] + 1;
                    fig.brick_4[0][0] = fig.brick_3[0][0] - 1;
                    fig.brick_1[0][1] = fig.brick_3[0][1] + 1;
                    fig.brick_2[0][1] = fig.brick_3[0][1];
                    fig.brick_4[0][1] = fig.brick_3[0][1];
                    break;
                }
                if (fig.brick_1[0][0] == fig.brick_3[0][0] + 1 && fig.brick_1[0][1] == fig.brick_3[0][1] + 1) {
                    fig.brick_1[0][0] = fig.brick_3[0][0] - 1;
                    fig.brick_2[0][0] = fig.brick_3[0][0];
                    fig.brick_4[0][0] = fig.brick_3[0][0];
                    fig.brick_1[0][1] = fig.brick_3[0][1] + 1;
                    fig.brick_2[0][1] = fig.brick_3[0][1] + 1;
                    fig.brick_4[0][1] = fig.brick_3[0][1] - 1;
                    break;
                }
                if (fig.brick_1[0][0] == fig.brick_3[0][0] - 1 && fig.brick_1[0][1] == fig.brick_3[0][1] + 1) {
                    fig.brick_1[0][0] = fig.brick_3[0][0] - 1;
                    fig.brick_2[0][0] = fig.brick_3[0][0] - 1;
                    fig.brick_4[0][0] = fig.brick_3[0][0] + 1;
                    fig.brick_1[0][1] = fig.brick_3[0][1] - 1;
                    fig.brick_2[0][1] = fig.brick_3[0][1];
                    fig.brick_4[0][1] = fig.brick_3[0][1];
                    break;
                }
                if (fig.brick_1[0][0] == fig.brick_3[0][0] - 1 && fig.brick_1[0][1] == fig.brick_3[0][1] - 1) {
                    fig.brick_1[0][0] = fig.brick_3[0][0] + 1;
                    fig.brick_2[0][0] = fig.brick_3[0][0];
                    fig.brick_4[0][0] = fig.brick_3[0][0];
                    fig.brick_1[0][1] = fig.brick_3[0][1] - 1;
                    fig.brick_2[0][1] = fig.brick_3[0][1] - 1;
                    fig.brick_4[0][1] = fig.brick_3[0][1] + 1;
                    break;
                }
            }
            case 3: {
                if (fig.brick_3[0][0] > 10) {
                    break;
                }
                rotate_check();
                if (fig.brick_4[0][0] == fig.brick_3[0][0]) {
                    fig.brick_1[0][0] = fig.brick_3[0][0] - 1;
                    fig.brick_2[0][0] = fig.brick_3[0][0];
                    fig.brick_4[0][0] = fig.brick_3[0][0] + 1;
                    fig.brick_1[0][1] = fig.brick_3[0][1] - 1;
                    fig.brick_2[0][1] = fig.brick_3[0][1] - 1;
                    fig.brick_4[0][1] = fig.brick_3[0][1];
                    break;
                } else {
                    fig.brick_1[0][0] = fig.brick_3[0][0] - 1;
                    fig.brick_2[0][0] = fig.brick_3[0][0] - 1;
                    fig.brick_4[0][0] = fig.brick_3[0][0];
                    fig.brick_1[0][1] = fig.brick_3[0][1] + 1;
                    fig.brick_2[0][1] = fig.brick_3[0][1];
                    fig.brick_4[0][1] = fig.brick_3[0][1] - 1;
                    break;
                }
            }
            case 4: {
                if ((fig.brick_2[0][0] > 10 && fig.brick_4[0][0] > 10) || (fig.brick_2[0][0] < 1 && fig.brick_4[0][0] < 1)) {
                    break;
                }
                rotate_check();
                if (fig.brick_1[0][0] == fig.brick_3[0][0] && fig.brick_1[0][1] == fig.brick_3[0][1] + 1) {
                    fig.brick_1[0][0] = fig.brick_3[0][0] - 1;
                    fig.brick_2[0][0] = fig.brick_3[0][0];
                    fig.brick_4[0][0] = fig.brick_3[0][0];
                    fig.brick_1[0][1] = fig.brick_3[0][1];
                    fig.brick_2[0][1] = fig.brick_3[0][1] - 1;
                    fig.brick_4[0][1] = fig.brick_3[0][1] + 1;
                    break;
                }
                if (fig.brick_1[0][0] == fig.brick_3[0][0] - 1 && fig.brick_1[0][1] == fig.brick_3[0][1]) {
                    fig.brick_1[0][0] = fig.brick_3[0][0];
                    fig.brick_2[0][0] = fig.brick_3[0][0] + 1;
                    fig.brick_4[0][0] = fig.brick_3[0][0] - 1;
                    fig.brick_1[0][1] = fig.brick_3[0][1] - 1;
                    fig.brick_2[0][1] = fig.brick_3[0][1];
                    fig.brick_4[0][1] = fig.brick_3[0][1];
                    break;
                }
                if (fig.brick_1[0][0] == fig.brick_3[0][0] && fig.brick_1[0][1] == fig.brick_3[0][1] - 1) {
                    fig.brick_1[0][0] = fig.brick_3[0][0] + 1;
                    fig.brick_2[0][0] = fig.brick_3[0][0];
                    fig.brick_4[0][0] = fig.brick_3[0][0];
                    fig.brick_1[0][1] = fig.brick_3[0][1];
                    fig.brick_2[0][1] = fig.brick_3[0][1] + 1;
                    fig.brick_4[0][1] = fig.brick_3[0][1] - 1;
                    break;
                }
                if (fig.brick_1[0][0] == fig.brick_3[0][0] + 1 && fig.brick_1[0][1] == fig.brick_3[0][1]) {
                    fig.brick_1[0][0] = fig.brick_3[0][0];
                    fig.brick_2[0][0] = fig.brick_3[0][0] - 1;
                    fig.brick_4[0][0] = fig.brick_3[0][0] + 1;
                    fig.brick_1[0][1] = fig.brick_3[0][1] + 1;
                    fig.brick_2[0][1] = fig.brick_3[0][1];
                    fig.brick_4[0][1] = fig.brick_3[0][1];
                    break;
                }
            }
        }
    }

    private void clearline() {
        int q = 0;
        for (int i = 24; i > -1; i--) {
            for (int j = 0; j < 12; j++) {
                if (position[j][i] != 0) {
                    q++;
                }
            }
            if (q > 11) {
                for (int j = 0; j < 12; j++) {
                    for (int k = i; k > 0; k--) {
                        position[j][k] = position[j][k - 1];
                    }
                }
                i++;
                points += 10;
                if (points == 10000) {
                    points = 0;
                }
                String p = new String("Points: " + String.valueOf(points));
                gui.point.setText(p);
                gui.point.update(g2d);
            }
            q = 0;
        }
    }

    private void endOfGame() throws FileNotFoundException, IOException {
        if (down_is_posible() == false) {
            if (fig.brick_1[0][1] < 4 || fig.brick_2[0][1] < 4 || fig.brick_3[0][1] < 4 || fig.brick_4[0][1] < 4) {
                System.out.println("game over");
                timer.stop();        
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("TABLE OF POINT"))) {
                    records();
                    oos.writeObject(table);
                }           
                newGame();              
            }
        }
    }

    private void records() {
        boolean record = false;             
            for (int i = 0; i < table.list.length; i++) {
                if (table.list[i] == null) {
                record = true;
                    String nick = JOptionPane.showInputDialog(gui, "Enter your name", "NEW RECORD! ", JOptionPane.INFORMATION_MESSAGE);
                    Records newrecord = new Records(nick, points);                    
                    table.list[i] = newrecord;
                    break;
                }
                if (points > table.list[i].points) {
                    record = true;
                    String nick = JOptionPane.showInputDialog(gui, "Enter your name", "NEW RECORD! ", JOptionPane.INFORMATION_MESSAGE);
                    Records newrecord = new Records(nick, points);
                    for (int j = table.list.length - 1; j > i; j--) {
                        table.list[j] = table.list[j - 1];
                    }
                    table.list[i] = newrecord;
                    break;
                }
            }           
                if (!record){
                System.out.println("low");
                JOptionPane gameOverDialog = new JOptionPane();
                gameOverDialog.setFont(gui.default_font);
                gameOverDialog.showMessageDialog(gui, "GAME OVER", "TETRIS", JOptionPane.INFORMATION_MESSAGE);
            }
    }

    private void newGame() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 25; j++) {
                position[i][j] = 0;
                points = 0;
                gui.point.setText(String.valueOf(points));
                gui.point.update(g2d);
                gui.TimerRun = false;
                gui.timer_driver.setText("START");
                repaint();
            }
        }
    }
}
