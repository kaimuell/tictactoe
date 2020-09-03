package controller;

import java.awt.Point;
import java.util.List;

import foo.*;

public class MatchController implements Runnable {
    private IPlayer p1;
    private IPlayer p2;
    private IModel model;
    private List<IView> views;
    private boolean won;
    private GameStateController gameStateController;

    public MatchController(IPlayer p1, IPlayer p2, IModel model, List<IView> views,
            GameStateController gameStateController) {
        this.p1 = p1;
        this.p2 = p2;
        this.model = model;
        this.views = views;
        this.gameStateController = gameStateController;
        won = false;
    }

    protected void play() {
        gameStateController.notifyOfNewGame();
        for (int i = 0; i < 9; i++) {
            if (!isMatchWon()) {
                boolean gerade = i % 2 == 0;
                IPlayer currentPlayer = gerade ? p1 : p2;
                EFieldState currentState = gerade ? EFieldState.CROSS : EFieldState.CIRCLE;
                boolean moved = false;
                while (!moved) {
                    try {
                        Point p = currentPlayer.getZug(model.toServerString());
                        if (isValidMove(p)) {
                            model.setFeldZustand(p.x, p.y, currentState);
                            moved = true;
                        }
                        won = matchWon();
                        if (won) {
                            gameStateController.notifyOfGameEnd(getWinner());
                        }
                        for (IView aktView : views) {
                            aktView.refresh();
                        }
                    } catch (PlayerException e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                }
            }
        }
        if (!won) {
            gameStateController.notifyOfGameEnd(EFieldState.EMPTY);
        }
    }

    public boolean isValidMove(Point selectedPoint) {
        if (model.getFeldZustand(selectedPoint.x, selectedPoint.y) == EFieldState.EMPTY) {
            return true;
        } else {
            return false;
        }
    }

    public void setPlayerOne(IPlayer player) {
        this.p1 = player;
    }

    public void setPlayerTwo(IPlayer player) {
        this.p2 = player;
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

    public boolean isMatchWon() {
        return won;
    }

    public EFieldState getWinner() {
        if (!model.getWinningFields().isEmpty()) {
            Point p = model.getWinningFields().get(0);
            return model.getFeldZustand(p.x, p.y);
        }
        return null;
    }

    public void resetGame() {
        model.resetModel();
        won = false;
        for (IView aktView : views) {
            aktView.refresh();
        }
    }

}
