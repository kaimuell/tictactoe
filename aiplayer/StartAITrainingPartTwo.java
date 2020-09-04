package aiplayer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import common.EFieldState;
import common.Model;
import controller.GameStateController;
import interfaces.*;
import player.RandomPlayer;

public class StartAITrainingPartTwo {
    public static void main(String[] args) {
        IPlayer p1 = new RandomPlayer();
        String path = FileSystems.getDefault().getPath("src", "ai", "tiktaktoe.ai").toString();
        GameStateController gsc = new GameStateController();
        IModel model = new Model();
        List<IView> viewList = new ArrayList<IView>(2);
        AIPlayer ai = null;
        try {
           ai = new AIPlayer(gsc, path);
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Konnte KI nicht laden.");
            System.exit(0);
        }


        AITrainer trainer = new AITrainer(ai, EFieldState.CIRCLE, path);

        AITrainingMatchController mc = new AITrainingMatchController( p1, trainer, model, viewList, gsc, 3000000);

        gsc.addWinStateListener(trainer);
        mc.addAITrainer(trainer);

        mc.play();
    }
}
