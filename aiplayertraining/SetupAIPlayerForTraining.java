package aiplayertraining;

import java.nio.file.FileSystems;

import aiplayer.AIPlayer;
import aiplayer.AIPlayerLoadSaver;
import controller.GameStateController;

public class SetupAIPlayerForTraining {
    public static void main(String[] args) {
        String path = FileSystems.getDefault().getPath("src", "ai", "tiktaktoe.ai").toString();
        GameStateController gsc = new GameStateController();
        AIPlayer ai = new AIPlayer(gsc);
        AIPlayerLoadSaver aiPLS = new AIPlayerLoadSaver();
        aiPLS.saveAiPlayerDecisionTree(ai, path);
        System.out.println("KI gespeichert");
    }
}
