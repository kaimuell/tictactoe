package aiplayer;

import java.awt.Point;
import java.util.*;

import foo.*;

public class AITrainingMatchController extends MatchController implements Runnable {
    private IPlayer p1;
    private IPlayer p2;
    private IModel model;
    private List<IView> views;
    private List<AITrainer> aiTrainerList;
    private boolean won;
    int durchlauefe;
    private GameStateController gameStateController;

    public AITrainingMatchController(IPlayer p1, IPlayer p2, IModel model, List<IView> views, GameStateController gameStateController, int durchlaufe) {
        super(p1, p2, model, views, gameStateController);
        this.p1 = p1;
        this.p2 = p2;
        this.model = model;
        this.views = views;
        this.gameStateController = gameStateController;
        won = false;
        this.durchlauefe = durchlaufe;
        this.aiTrainerList = new ArrayList<>(2);
    }

    @Override
    protected void play() {
        for (int j = 0; j < durchlauefe; j++) {
            gameStateController.notifyOfNewGame();
            System.out.println("Spiel Nummer : " + j);
            model = new Model();
            won = false;
            for (int i = 0; i < 9; i++) {
                if (!won) { // TODO Wenn Funktional for und if durch while(!won) ersetzen
                    boolean gerade = i % 2 == 0;
                    IPlayer currentPlayer = gerade ? p1 : p2;
                    EFieldState currentState = gerade ? EFieldState.CROSS : EFieldState.CIRCLE;
                    try {
                        Point p = currentPlayer.getZug(model.toServerString());
                        model.setFeldZustand(p.x, p.y, currentState);
                        won = matchWon();
                        for (IView aktView : views) {
                            aktView.refresh();
                        }
                    } catch (PlayerException e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                }
            gameStateController.notifyOfGameEnd(getWinner());
            }
        }
        for (AITrainer aiTrainer : aiTrainerList) {
            aiTrainer.saveAIPlayer();
            System.out.println(aiTrainer.getWinRatio());
        }
    }


    public void addAITrainer(AITrainer trainer) {
        aiTrainerList.add(trainer);
    }

    private boolean matchWon() {
        if (model.getFeldZustand(1, 1) != EFieldState.EMPTY && model.getFeldZustand(0, 0) == model.getFeldZustand(1, 1)
                && model.getFeldZustand(1, 1) == model.getFeldZustand(2, 2)) {
            model.setWinningFields(0, 0);
            model.setWinningFields(1, 1);
            model.setWinningFields(2, 2);
            return true;
        }
        if (model.getFeldZustand(1, 1) != EFieldState.EMPTY && model.getFeldZustand(0, 2) == model.getFeldZustand(1, 1)
                && model.getFeldZustand(1, 1) == model.getFeldZustand(2, 0)) {
            model.setWinningFields(0, 2);
            model.setWinningFields(1, 1);
            model.setWinningFields(2, 0);
            return true;
        }

        for (int i = 0; i < 3; i++) {
            if (model.getFeldZustand(i, 0) != EFieldState.EMPTY
                    && model.getFeldZustand(i, 0) == model.getFeldZustand(i, 1)
                    && model.getFeldZustand(i, 1) == model.getFeldZustand(i, 2)) {
                model.setWinningFields(i, 0);
                model.setWinningFields(i, 1);
                model.setWinningFields(i, 2);
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (model.getFeldZustand(0, i) != EFieldState.EMPTY
                    && model.getFeldZustand(0, i) == model.getFeldZustand(1, i)
                    && model.getFeldZustand(1, i) == model.getFeldZustand(2, i)) {
                model.setWinningFields(0, i);
                model.setWinningFields(1, i);
                model.setWinningFields(2, i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        play();
    }

    @Override
    public boolean isMatchWon() {
        return won;
    }

    @Override
    public EFieldState getWinner() {
        if (!model.getWinningFields().isEmpty()) {
            Point p = model.getWinningFields().get(0);
            return model.getFeldZustand(p.x, p.y);
        }
        return EFieldState.EMPTY;
    }
}
