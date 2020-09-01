package aiplayer;

import java.awt.Point;
import java.util.Stack;

import foo.*;

public class AITrainer implements IPlayer, IWinStateListener {

    //Lässt KI Spieler gegen Random Server oder anderen KI Spieler antreten => Überanpassung?
    
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
        gamesLost  = 0;
        gamesWon = 0;    
    }

    @Override
    public Point getZug(String s) throws PlayerException {
       Point p = ai.getZug(s);
       nodeStack.push(ai.getAktTreeNode());
       return p;
    }

    @Override
    public void winNotification(EFieldState winner) {
        System.out.println("Winner = " +winner);
        while(!nodeStack.isEmpty()) {
            AITreeNode tn =  nodeStack.pop();
            System.out.println("Update Knoten : " + tn.getField() + " aktuell  :" + tn.getWeight());
            if (playerState == winner) {
                gamesWon++;
                tn.decreaseWeight();
            }else if (winner != EFieldState.EMPTY){
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
        pls.saveAIPlayer(ai, filename);
    }

    public double getWinRatio() {
        return (double) gamesWon/gamesLost;
    }

    @Override
    public void newGameNotification() {
        ai.resetDecisionTree(); 
    }
    
    
    
       
}
