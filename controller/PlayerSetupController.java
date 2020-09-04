package controller;

import java.util.ArrayList;

import aiplayer.AIPlayer;
import interfaces.IInputDevice;
import interfaces.IPlayerSetupListener;
import player.*;

public class PlayerSetupController {
    HumanPlayer humanPlayer1, humanPlayer2;
    RandomPlayer randomPlayer;
    NetworkPlayer networkPlayer1, networkPlayer2;
    AIPlayer aiPlayer1, aiPlayer2;
    GameStateController gameStateController;
    ArrayList<IPlayerSetupListener> playerSetupListenerList;

    public PlayerSetupController(IInputDevice inputDevice, GameStateController gameStateController) {
        this.gameStateController = gameStateController;
        defaultSetup(inputDevice);
        this.playerSetupListenerList = new ArrayList<>();
    }

    public void addPlayerSetupListener(IPlayerSetupListener playerSetupListener) {
        playerSetupListenerList.add(playerSetupListener);
    }

    private void defaultSetup(IInputDevice inputdevice) {
        this.humanPlayer1 = new HumanPlayer(inputdevice);
        this.humanPlayer2 = new HumanPlayer(inputdevice);
        randomPlayer = new RandomPlayer();
    }

    public HumanPlayer getHumanPlayer1() {
        return humanPlayer1;
    }

    public HumanPlayer getHumanPlayer2() {
        return humanPlayer2;
    }

    public RandomPlayer getRandomPlayer() {
        return randomPlayer;
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

    public void setHumanPlayerOnesInputDevice(IInputDevice inputDevice) {
        this.humanPlayer1 = new HumanPlayer(inputDevice);
        informPlayerSetupListeners();
    }
    
    public void setHumanPlayerTwosInputDevice(IInputDevice inputDevice) {
        this.humanPlayer2 = new HumanPlayer(inputDevice);
        informPlayerSetupListeners();
    }

    public void setNetworkPlayer(NetworkPlayer networkPlayer) {

        this.networkPlayer1 = networkPlayer;
        this.networkPlayer2 = networkPlayer1;
        informPlayerSetupListeners();
    }

    public void setNetworkPlayerServer(String adress) {

        this.networkPlayer1 = new NetworkPlayer(adress);
        this.networkPlayer2 = networkPlayer1;
        informPlayerSetupListeners();
    }

    public void setAiPlayer(AIPlayer aiPlayer) {
        this.aiPlayer1 = aiPlayer;
        this.aiPlayer2 = new AIPlayer(gameStateController, aiPlayer1);
        informPlayerSetupListeners();
    }

    private void informPlayerSetupListeners() {
        for (IPlayerSetupListener iPlayerSetupListener : playerSetupListenerList) {
            iPlayerSetupListener.playersUpdated();
        }

    }

}
