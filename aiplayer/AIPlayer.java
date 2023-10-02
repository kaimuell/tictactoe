package aiplayer;

import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import common.*;
import controller.GameStateController;
import interfaces.*;
import player.PlayerException;

/**
 * Implementiert einen KI Spieler
 *
 */
public class AIPlayer implements IPlayer, Serializable, IWinStateListener {

    private AITreeNode aktTreeNode;
    private AITreeNode treeNodeHeader;

    /**
     * Konstruktor
     * @param controller    der zugewiesene Controller 
     * @param aiPlayer      ein anderer Ki-Spieler, dessen Entscheidungsbaum übernommen werden soll
     */
    public AIPlayer(GameStateController controller, AIPlayer aiPlayer) {
        controller.addWinStateListener(this);
        this.treeNodeHeader = aiPlayer.getTreeNodeHeader();
        this.aktTreeNode = treeNodeHeader;
    }

        /**
         * Konstruktor um einen KI-Spieler zu erstellen, dessen Entscheidungsbaum aus einer Datei geladen wird
         * @param controller    der zugewiesene Controller
         * @param filename      die Datei aus der der Entscheidungsbaum geladen werden soll
         * @throws ClassNotFoundException   
         * @throws IOException
         */
    public AIPlayer(GameStateController controller, String filename) throws ClassNotFoundException, IOException {
        controller.addWinStateListener(this);
        AIPlayerLoadSaver ls = new AIPlayerLoadSaver();
        this.treeNodeHeader = ls.loadAiPlayerDecisionTree(filename);
        this.aktTreeNode = treeNodeHeader;
    }

    protected AIPlayer(GameStateController controller) {

        controller.addWinStateListener(this);
        AITreeNode aitn = new AITreeNode("_________");
        this.treeNodeHeader = aitn;
        this.aktTreeNode = aitn;
    }

    protected void resetDecisionTree() {
        aktTreeNode = treeNodeHeader;
    }

    @Override
    public Point getMove(String s) throws PlayerException {
        s = s.trim();
       int rotation = TikTakToeFieldRotationEvaluator.fieldStatesMatchInRotationNumber(s, aktTreeNode.getField());
        if (rotation != -1) {
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
        int rotation = TikTakToeFieldRotationEvaluator.fieldStatesMatchInRotationNumber(s.trim(), aktTreeNode.getField());
        if (rotation == -1) {
            throw new PlayerException("Spielzüge Stimmen nicht überein");
        }
        aktTreeNode.sortPossibleMoves();
        String formerState = TikTakToeFieldRotationEvaluator.rotateFieldToRotation(aktTreeNode.getField(), rotation);
        aktTreeNode = aktTreeNode.getPossibleMoves().get(0);
        String nextState = TikTakToeFieldRotationEvaluator.rotateFieldToRotation(aktTreeNode.getField(), rotation);
        for (int i = 0; i < s.length(); i++) {
            if (formerState.charAt(i) == '_' && nextState.charAt(i) != '_') {
                return new Point((i / 3), (i % 3));
            }
        }
        throw new PlayerException("Fehler in AIPlayer.makeFirstMove : Nummer des Nächsten Zuges nicht gefunden.");
    }

    protected void updateCurrentFieldState(String s) throws PlayerException {
        boolean found = false;
        Iterator<AITreeNode> it = aktTreeNode.getPossibleMoves().iterator();
        while (!found && it.hasNext()) {
            AITreeNode ait = it.next();
            if (TikTakToeFieldRotationEvaluator.fieldStatesMatchInRotationNumber(s.trim(), ait.getField()) != -1) {
                aktTreeNode = ait;
                found = true;
            }
        }
        if (!found) {
            throw new PlayerException(
                    "Fehler in AITreeNode.updateCurrentFieldState : Übergebener Spielfeldzustand nicht in AITree " + s
                            + " in " + aktTreeNode.getField());
        }
    }

    protected AITreeNode getAktTreeNode() {
        return aktTreeNode;
    }

    protected void setAktTreeNode(AITreeNode treeNode) {
        this.aktTreeNode = treeNode;

    }

    
    
    
    @Override
    public void newGameNotification() {
        resetDecisionTree();

    }

    @Override
    public void winNotification(EFieldState winner) {
    }
    
}
