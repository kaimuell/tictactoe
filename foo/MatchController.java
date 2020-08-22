package foo;

import java.awt.Point;
import java.util.List;

public class MatchController {
    private IPlayer p1;
    private IPlayer p2;
    private IModel model;
    private List<IView> views;
    
    

    public MatchController(IPlayer p1, IPlayer p2, IModel model, List<IView> views) {
        this.p1 = p1;
        this.p2 = p2;
        this.model = model;
        this.views = views;
    }

    public void play() {
        for (int i = 0; i < 9; i++) {
            
            boolean gerade = i % 2 == 0;
            IPlayer currentPlayer = gerade ? p1: p2;
            EFieldState currentState = gerade ? EFieldState.CROSS : EFieldState.CIRCLE;
            try {
                Point p = currentPlayer.getZug(model.toServerString());
                                                         model.setFeldZustand(p.x, p.y, currentState);
                for (IView aktView : views) {
                    aktView.refresh();    
                }
            } catch (PlayerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.exit(0);
            }
            
        }
    }

}                 
