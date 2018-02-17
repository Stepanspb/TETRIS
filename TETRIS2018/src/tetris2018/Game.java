package tetris2018;

import javax.swing.SwingUtilities;

public class Game {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Process fig = new Process();
                GUI window = new GUI();
                fig.addGUI(window);
                window.field.add(fig);
                window.setfig(fig);
                window.control();
                fig.start();
            }
        });

    }
}
