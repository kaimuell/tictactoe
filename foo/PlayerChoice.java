package foo;

import java.awt.Choice;

import aiplayer.AIPlayer;

public class PlayerChoice extends Choice {
    IInputDevice inputDevice;
    private GameStateController gameStateController;

    public PlayerChoice(IInputDevice inputDevice, GameStateController gameStateController) {
        this.gameStateController = gameStateController;
        this.inputDevice = inputDevice;
        add("HUMANPLAYER);");
        add("NETWORKPLAYER");
        add("AIPLAYER");
    }

    public IPlayer getSelectedPlayer() {
        IPlayer player;
        switch (this.getSelectedItem()) {
        case "HUMANPLAYER":
            player = new HumanPlayer(inputDevice);
            break;
        case "NETWORKPLAYER":
            player = new NetworkPlayer("localhost"); // TODO mehr Optionen für NetworkPlayer
            break;
        case "AIPLAYER" :
            player = new AIPlayer(gameStateController); //TODO mehr Optionen für AIPlayer. Setup Screen?
            break;
        default:
            player = new HumanPlayer(inputDevice);
            break;
        }
        return player;
    }
}
