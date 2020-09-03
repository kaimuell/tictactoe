package aiplayer;

import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import controller.GameStateController;
import foo.*;

public class AIPlayer implements IPlayer, Serializable, IWinStateListener {

    private AITreeNode aktTreeNode;
    private AITreeNode treeNodeHeader;
    

    public AIPlayer(GameStateController controller, AIPlayer aiPlayer) {
        this.treeNodeHeader = aiPlayer.getTreeNodeHeader();
        this.aktTreeNode = treeNodeHeader;
        controller.addWinStateListener(this);
    }

    public AIPlayer(GameStateController controller, String filename) {
        controller.addWinStateListener(this);
        AIPlayerLoadSaver ls = new AIPlayerLoadSaver();
        try {
            this.treeNodeHeader = ls.loadAIPlayer(filename).getTreeNodeHeader();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Konnte Datei nicht öffnen");
            e.printStackTrace();
        }
        this.aktTreeNode = treeNodeHeader;
    }

    public AIPlayer(GameStateController controller) {
        
        controller.addWinStateListener(this);
        AITreeNode aitn = new AITreeNode("_________");
        this.aktTreeNode = aitn;
        this.treeNodeHeader = aitn;
    }

    public void resetDecisionTree() {
        aktTreeNode = treeNodeHeader;
    }

    @Override
    public Point getZug(String s) throws PlayerException {
        s = s.trim();
        if (s.equals(aktTreeNode.getField())) {
            return makeNextMove(s);
        } else {
            updateCurrentFieldState(s);
            return makeNextMove(s);
        }

    }

    protected AITreeNode getTreeNodeHeader() {
        return treeNodeHeader;
    }

    private Point makeNextMove(String s) throws PlayerException {
        if (!aktTreeNode.getField().equals(s.trim())) {
            throw new PlayerException("Spielzüge Stimmen nicht überein");
        }
        aktTreeNode.sortPossibleMoves();
        String formerState = s.trim();
        aktTreeNode = aktTreeNode.getPossibleMoves().get(0);
        String nextState = aktTreeNode.getField();
        for (int i = 0; i < s.length(); i++) {
            if (formerState.charAt(i) == '_' && nextState.charAt(i) != '_') {
                return new Point((i / 3), (i % 3));
            }
        }
        throw new PlayerException("Fehler in AIPlayer.makeFirstMove : Nummer des Nächsten Zuges nicht gefunden.");
    }

    private void updateCurrentFieldState(String s) throws PlayerException {
        boolean found = false;
        Iterator<AITreeNode> it = aktTreeNode.getPossibleMoves().iterator();
        while (!found && it.hasNext()) {
            AITreeNode ait = it.next();
            if (ait.getField().equals(s.trim())) {
                aktTreeNode = ait;
                found = true;
            }
        }
        if (!found) {
            throw new PlayerException(
                    "Fehler in AITreeNode.updateCurrentFieldState : Übergebener Spielfeldzustand nicht in AITree " + s + " in " +aktTreeNode.getField());
        }
    }

    protected AITreeNode getAktTreeNode() {
        return aktTreeNode;
    }

    @Override
    public void newGameNotification() {
        resetDecisionTree();

    }

    @Override
    public void winNotification(EFieldState winner) {
    }

}
