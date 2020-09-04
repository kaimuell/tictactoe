package aiplayer;

import java.awt.Point;
import java.util.Stack;

import common.*;
import interfaces.*;
import player.PlayerException;

public class AITrainer implements IPlayer, IWinStateListener {

    private AIPlayer ai;
    private EFieldState playerState;
    private Stack<AITreeNode> nodeStack;
    private int gamesWon, gamesLost;
    private String playerfile;

    public AITrainer(AIPlayer ai, EFieldState playerState, String playerfile) {
        this.ai = ai;
        this.playerState = playerState;
        this.nodeStack = new Stack<AITreeNode>();
        this.playerfile = playerfile;
        gamesLost = 0;
        gamesWon = 0;
    }

    @Override
    public Point getMove(String s) throws PlayerException {
        s = s.trim();
        if (!s.equals(ai.getAktTreeNode().getField())){               
            ai.updateCurrentFieldState(s);
        }
        int nextForcedMove = computeCaseNextMoveIsWinningMove(s);
        Point p = null;
        if (nextForcedMove == -1) {
            p = ai.getMove(s);
            nodeStack.push(ai.getAktTreeNode());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 9; i++) {
                if (nextForcedMove != i) {
                    stringBuilder.append(s.charAt(i));
                } else {
                    char charToChange = (playerState == EFieldState.CROSS ? 'x' : 'o');
                    stringBuilder.append(charToChange);
                    p = new Point((i / 3), (i % 3));
                }
            }
            String nextForcedFieldState = stringBuilder.toString();
            searchForMatchingTreeNodeAndSetItAsAktual(nextForcedFieldState);
        }
        return p;
    }

    private void searchForMatchingTreeNodeAndSetItAsAktual(String nextForcedFieldState) {
        for (AITreeNode ait : ai.getAktTreeNode().getPossibleMoves()) {
            if (ait.getField().equals(nextForcedFieldState)) {
                ai.setAktTreeNode(ait);
                nodeStack.push(ai.getAktTreeNode());
            }
        }
    }

    @Override
    public void winNotification(EFieldState winner) {
        while (!nodeStack.isEmpty()) {
            AITreeNode tn = nodeStack.pop();
            System.out.println("Update Knoten : " + tn.getField());
            if (playerState == winner) {
                gamesWon++;
                tn.decreaseWeight();
            } else if (winner != EFieldState.EMPTY) {
                gamesLost++;
                tn.increaseWeight();
            }
        }
        ai.resetDecisionTree();
    }

    public void saveAIPlayer() {
        saveAIPlayer(playerfile);
    }

    public void saveAIPlayer(String filename) {
        AIPlayerLoadSaver pls = new AIPlayerLoadSaver();
        pls.saveAiPlayerDecisionTree(ai, filename);
    }

    public double getWinRatio() {
        return (double) gamesWon / gamesLost;
    }

    @Override
    public void newGameNotification() {
        ai.resetDecisionTree();
    }

    private int computeCaseNextMoveIsWinningMove(String s) {
        for (AITreeNode ait : ai.getAktTreeNode().getPossibleMoves()) {
            if (isMatchWon(ait.getField())) {
                for (int i = 0; i < 9; i++) {
                    if (s.charAt(i) != ait.getField().charAt(i)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    private boolean isMatchWon(String s) {
        if (!(s.charAt(0) == '_') && (s.charAt(0) == s.charAt(4) && s.charAt(4) == s.charAt(8))) {
            return true;
        }
        if (!(s.charAt(2) == '_') && (s.charAt(2) == s.charAt(4) && s.charAt(4) == s.charAt(6))) {
            return true;
        }

        for (int i = 0; i < 3; i++) {
           int j = i*3;
            if (!(s.charAt(j) == '_') && ((s.charAt(j) == s.charAt(j+ 1) && s.charAt(j + 1) == s.charAt(j + 2)))) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (!(s.charAt(i) == '_') && ((s.charAt(i) == s.charAt(i + 3) && s.charAt(i + 3) == s.charAt(i + 6)))) {
                return true;
            }
        }
        return false;
    }

}
