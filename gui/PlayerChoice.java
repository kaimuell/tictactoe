package gui;

import java.awt.Choice;

import controller.PlayerSetupController;
import foo.*;

public class PlayerChoice extends Choice {
    private PlayerSetupController playerSetupController;
    private PlayerID playerID;

    public PlayerChoice(PlayerID playerID, PlayerSetupController playerSetupController) {
        this.playerSetupController = playerSetupController;
        this.playerID = playerID;
        add("HUMANPLAYER");
        if ((playerID == PlayerID.PLAYERONE && playerSetupController.getNetworkPlayer1() != null)
                || (playerID == PlayerID.PLAYERTWO && playerSetupController.getNetworkPlayer2() != null)) {
            add("NETWORKPLAYER");
        }
        if ((playerID == PlayerID.PLAYERONE && playerSetupController.getAiPlayer1() != null)
                || (playerID == PlayerID.PLAYERTWO && playerSetupController.getAiPlayer2() != null)) {
            add("AIPLAYER");
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
