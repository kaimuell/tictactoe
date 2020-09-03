package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.GameStateController;
import controller.MatchController;
import foo.*;

public class StartButton extends JButton implements IWinStateListener {
    boolean gameStarted;
    MatchController matchController;
    IPlayerSettings playerSettings;

    public StartButton(MatchController matchController, GameStateController gameStateController, IPlayerSettings playerSettings) {
        super("START");
        this.matchController = matchController;
       gameStateController.addWinStateListener(this);
        this.playerSettings = playerSettings;
        gameStarted = false;
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameStarted) {
                    matchController.resetGame();
                    matchController.setPlayerOne(playerSettings.getPlayerOne());
                    matchController.setPlayerTwo(playerSettings.getPlayerTwo());
                    Thread t = new Thread(matchController);
                    t.start();
                    gameStarted = true;
                }
            }
        });

    }

    @Override
    public void winNotification(EFieldState winner) {
        gameStarted = false;
    }

    @Override
    public void newGameNotification() {
        
    }

}
