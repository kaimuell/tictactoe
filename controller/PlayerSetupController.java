package controller;

import java.nio.file.FileSystems;

import aiplayer.AIPlayer;
import foo.*;

public class PlayerSetupController {
    HumanPlayer humanPlayer1, humanPlayer2;
    NetworkPlayer networkPlayer1, networkPlayer2;
    AIPlayer aiPlayer1, aiPlayer2;
    GameStateController gameStateController;

    public PlayerSetupController(IInputDevice inputDevice, GameStateController gameStateController) {
        this.gameStateController = gameStateController;
        defaultSetup(inputDevice);
    }

    private void defaultSetup(IInputDevice inputdevice) {
        this.humanPlayer1 = new HumanPlayer(inputdevice);
        this.humanPlayer2 = new HumanPlayer(inputdevice);

        try {
            this.networkPlayer1 = new NetworkPlayer("localhost");
            networkPlayer1.getZug("_________");
        } catch (Exception e) {
            this.networkPlayer1 = null;
        }
        this.networkPlayer2 = networkPlayer1;

        try {
            String path = FileSystems.getDefault().getPath("src", "ai", "tiktaktoe.ai").toString();
            this.aiPlayer1 = new AIPlayer(gameStateController, path);
            this.aiPlayer2 = new AIPlayer(gameStateController, aiPlayer1);
        } catch (Exception e) {
            System.out.println("KI Spieler konnte nicht geladen werden");
            aiPlayer1 = null;
            aiPlayer2 = null;
        }

    }

    public HumanPlayer getHumanPlayer1() {
        return humanPlayer1;
    }

    public HumanPlayer getHumanPlayer2() {
        return humanPlayer2;
    }

    public NetworkPlayer getNetworkPlayer1() {
        return networkPlayer1;
    }

    public NetworkPlayer getNetworkPlayer2() {
        return networkPlayer2;
    }

    public AIPlayer getAiPlayer1() {
        return aiPlayer1;
    }

    public AIPlayer getAiPlayer2() {
        return aiPlayer2;
    }

    public void setHumanPlayersInputDevice(IInputDevice inputDevice) {
        this.humanPlayer1 = new HumanPlayer(inputDevice);
    }

    public void setNetworkPlayerServer(String adress) {

        this.networkPlayer1 = new NetworkPlayer(adress);
        this.networkPlayer2 = networkPlayer1;
    }

    public void setNetworkPlayer2(NetworkPlayer networkPlayer2) {
        this.networkPlayer2 = networkPlayer2;
    }

    public void setAiPlayerSource(String source) {
        this.aiPlayer1 = new AIPlayer(gameStateController, source);
        this.aiPlayer2 = new AIPlayer(gameStateController, aiPlayer1);
    }

}
