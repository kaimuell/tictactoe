package foo;

import java.awt.*;

public class MatchController {
    private IPlayer p1;
    private IPlayer p2;

    public MatchController(IPlayer p1, IPlayer p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void play() {
        for (int i = 0; i < 9; i++) {
            
            boolean gerade = i % 2 == 0;
            IPlayer currentPlayer = gerade ? p1: p2;
            Point p = currentPlayer.getZug(""); // TODO String übergeben 
            System.out.println("Zug " + (i + 1) + " : " + p.x + ", " + p.y);
        }
    }

}
