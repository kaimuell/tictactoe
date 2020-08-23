package foo;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class JComponentTestFrame extends JFrame {

    public JComponentTestFrame(JComponent component) {
        this.add(component);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
