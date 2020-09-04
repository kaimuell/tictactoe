package gui;

import java.awt.Choice;

import common.*;
import controller.PlayerSetupController;
import interfaces.IPlayer;

public class PlayerChoice extends Choice {
    private PlayerSetupController playerSetupController;
    private PlayerID playerID;
    private boolean isNetworkplayerSetup, isAIPlayerSetup;

    public PlayerChoice(PlayerID playerID, PlayerSetupController playerSetupController) {
        this.playerSetupController = playerSetupController;
        this.playerID = playerID;
        add("HUMANPLAYER");
        add("RANDOMIZER");
        isNetworkplayerSetup = false;
        isAIPlayerSetup = false;
        if (!isNetworkplayerSetup && (playerSetupController.getNetworkPlayer(playerID) != null)) {
            add("NETWORKPLAYER");
            isNetworkplayerSetup = true;
        }
        if (!isAIPlayerSetup && playerSetupController.getAiPlayer(playerID) != null) {
            add("AIPLAYER");
            isAIPlayerSetup = true;
        }
    }

    protected void checkForNetworkPlayer() {
        if (!isNetworkplayerSetup && playerSetupController.getNetworkPlayer(playerID) != null) {
            add("NETWORKPLAYER");
            isNetworkplayerSetup = true;
        }
    }

    protected void checkForAIPlayer() {
        if (!isAIPlayerSetup && playerSetupController.getAiPlayer(playerID) != null) {
            add("AIPLAYER");
            isAIPlayerSetup = true;
        }
    }

    public IPlayer getSelectedPlayer() {
        IPlayer player;
        switch (this.getSelectedItem()) {
        case "HUMANPLAYER":
            player = playerSetupController.getHumanPlayer(playerID);
            break;
        case "RANDOMIZER":
            player = playerSetupController.getRandomPlayer();
            break;
        case "NETWORKPLAYER":
            player = playerSetupController.getNetworkPlayer(playerID);
            break;
        case "AIPLAYER":
            player = playerSetupController.getAiPlayer(playerID);
            break;
        default:
            player = playerSetupController.getHumanPlayer(playerID);
            break;
        }
        return player;
    }
}
