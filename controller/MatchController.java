package controller;

import java.awt.Point;
import java.util.List;

import common.EFieldState;
import interfaces.*;
import player.PlayerException;

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
                        Point p = currentPlayer.getMove(model.toServerString());
                        if (isValidMove(p)) {
                            model.setFieldState(p.x, p.y, currentState);
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
        if (model.getFieldState(selectedPoint.x, selectedPoint.y) == EFieldState.EMPTY) {
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
        if (model.getFieldState(1, 1) != EFieldState.EMPTY && model.getFieldState(0, 0) == model.getFieldState(1, 1)
                && model.getFieldState(1, 1) == model.getFieldState(2, 2)) {
            model.setWinningFields(0, 0);
            model.setWinningFields(1, 1);
            model.setWinningFields(2, 2);
            return true;
        }
        if (model.getFieldState(1, 1) != EFieldState.EMPTY && model.getFieldState(0, 2) == model.getFieldState(1, 1)
                && model.getFieldState(1, 1) == model.getFieldState(2, 0)) {
            model.setWinningFields(0, 2);
            model.setWinningFields(1, 1);
            model.setWinningFields(2, 0);
            return true;
        }

        for (int i = 0; i < 3; i++) {
            if (model.getFieldState(i, 0) != EFieldState.EMPTY
                    && model.getFieldState(i, 0) == model.getFieldState(i, 1)
                    && model.getFieldState(i, 1) == model.getFieldState(i, 2)) {
                model.setWinningFields(i, 0);
                model.setWinningFields(i, 1);
                model.setWinningFields(i, 2);
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (model.getFieldState(0, i) != EFieldState.EMPTY
                    && model.getFieldState(0, i) == model.getFieldState(1, i)
                    && model.getFieldState(1, i) == model.getFieldState(2, i)) {
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
            return model.getFieldState(p.x, p.y);
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
