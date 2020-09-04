package gui;

import java.awt.Choice;

import common.*;
import controller.PlayerSetupController;
import interfaces.IPlayer;

public class PlayerChoice extends Choice {
    private PlayerSetupController playerSetupController;
    private PlayerID playerID;
    private boolean  isNetworkplayerSetup, isAIPlayerSetup;

    public PlayerChoice(PlayerID playerID, PlayerSetupController playerSetupController) {
        this.playerSetupController = playerSetupController;
        this.playerID = playerID;
        add("HUMANPLAYER");
        add("RANDOMIZER");
        isNetworkplayerSetup = false;
        isAIPlayerSetup = false;
        if (!isNetworkplayerSetup&& ((playerID == PlayerID.PLAYERONE && playerSetupController.getNetworkPlayer1() != null)
                || (playerID == PlayerID.PLAYERTWO && playerSetupController.getNetworkPlayer2() != null))) {
            add("NETWORKPLAYER");
            isNetworkplayerSetup = true;
        }
        if (!isAIPlayerSetup && ((playerID == PlayerID.PLAYERONE && playerSetupController.getAiPlayer1() != null)
                || (playerID == PlayerID.PLAYERTWO && playerSetupController.getAiPlayer2() != null))) {
            add("AIPLAYER");
            isAIPlayerSetup = true;
        }
    }
    
    protected void checkForNetworkPlayer() {
        if (!isNetworkplayerSetup && ((playerID == PlayerID.PLAYERONE && playerSetupController.getNetworkPlayer1() != null)
                || (playerID == PlayerID.PLAYERTWO && playerSetupController.getNetworkPlayer2() != null))) {
            add("NETWORKPLAYER");
            isNetworkplayerSetup = true;
        }
    }
    
    protected void checkForAIPlayer() {
        if (!isAIPlayerSetup && ((playerID == PlayerID.PLAYERONE && playerSetupController.getAiPlayer1() != null)
                || (playerID == PlayerID.PLAYERTWO && playerSetupController.getAiPlayer2() != null))) {
            add("AIPLAYER");
            isAIPlayerSetup = true;
        }
    }
    
    

    public IPlayer getSelectedPlayer() {
        IPlayer player = playerSetupController.getHumanPlayer1();
        switch (this.getSelectedItem()) {
        case "HUMANPLAYER":
            if (playerID == PlayerID.PLAYERONE) {
                player = playerSetupController.getHumanPlayer1();
            } else if (playerID == PlayerID.PLAYERTWO) {
                player = playerSetupController.getHumanPlayer2();
            }
            break;
        case "RANDOMIZER":
            if (playerID == PlayerID.PLAYERONE) {
                player = playerSetupController.getRandomPlayer();
            } else if (playerID == PlayerID.PLAYERTWO) {
                player = playerSetupController.getRandomPlayer();
            }
            break;
        case "NETWORKPLAYER":
            if (playerID == PlayerID.PLAYERONE) {
                player = playerSetupController.getNetworkPlayer1();
            } else if (playerID == PlayerID.PLAYERTWO) {
                player = playerSetupController.getNetworkPlayer2();
            }
            break;
        case "AIPLAYER":
            if (playerID == PlayerID.PLAYERONE) {
                player = playerSetupController.getAiPlayer1();
            } else if (playerID == PlayerID.PLAYERTWO) {
                player = playerSetupController.getAiPlayer2();
            }
            break;
        default:
            break;
        }
        return player;
    }
}
