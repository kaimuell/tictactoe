package aiplayertraining;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import aiplayer.AIPlayer;
import aiplayer.AITrainer;
import aiplayer.AITrainingMatchController;
import aiplayer.AITreeNode;
import common.EFieldState;
import common.Model;
import controller.GameStateController;
import interfaces.*;
import player.RandomPlayer;

public class StartAITrainingPartOne {
    public static void main(String[] args) {
        IPlayer p1 = new RandomPlayer();
        String path = FileSystems.getDefault().getPath("src", "ai", "tiktaktoe.ai").toString();
        GameStateController gsc = new GameStateController();
        IModel model = new Model();
        List<IView> viewList = new ArrayList<>(2);
        AIPlayer ai =null;
        try {
            ai = new AIPlayer(gsc,path);
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assert ai != null;
        for (AITreeNode tn : ai.getTreeNodeHeader().getPossibleMoves()) {
            if (tn.getField().equals("____x____")) {
                System.out.println("Feld gefunden");
                for (int i = 0; i < 50; i++) {
                    tn.decreaseWeight();
                    System.out.println("Gewicht gesenkt");
                }
                System.out.println("Gewicht von " + tn.getField() + " : " + tn.getWeight());
            }
        }

        AITrainer trainer = new AITrainer(ai, EFieldState.CROSS, path);

        AITrainingMatchController mc = new AITrainingMatchController(trainer, p1, model, viewList, gsc, 500000);

        gsc.addWinStateListener(trainer);
        mc.addAITrainer(trainer);

        mc.play();
    }
}
