package common;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class TicTacToeFrame extends JFrame {

    public TicTacToeFrame(JComponent component) {
        super("TICTACTOE");
        this.add(component);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
