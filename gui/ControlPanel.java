package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

import controller.*;
import foo.*;

public class ControlPanel extends JPanel implements IPlayerSettings, IWinStateListener {

    private PlayerChoice playerOne;
    private PlayerChoice playerTwo;
    private JLabel notificationLabel;

    public ControlPanel(MatchController matchController, GameStateController gameStateController,
            PlayerSetupController playerSetupController) {

        gameStateController.addWinStateListener(this);
        this.setPreferredSize(new Dimension(450, 80));
        playerOne = new PlayerChoice(PlayerID.PLAYERONE, playerSetupController);
        playerTwo = new PlayerChoice(PlayerID.PLAYERTWO, playerSetupController);
        JButton startButton = new StartButton(matchController, gameStateController, this);
        notificationLabel = new JLabel("");

        add(playerOne, BorderLayout.CENTER);
        add(startButton, BorderLayout.CENTER);
        add(playerTwo, BorderLayout.CENTER);
        add(notificationLabel, BorderLayout.SOUTH);

    }

    @Override
    public IPlayer getPlayerOne() {
        return playerOne.getSelectedPlayer();
    }

    @Override
    public IPlayer getPlayerTwo() {
        return playerTwo.getSelectedPlayer();
    }

    @Override
    public void winNotification(EFieldState winner) {
        switch (winner) {
        case EMPTY:
            notificationLabel.setText("Spiel beendet");
            break;
        case CROSS:
            notificationLabel.setText("Spieler 1 hat gewonnen");
            break;
        case CIRCLE:
            notificationLabel.setText("Spieler 2 hat gewonnen");
            break;
        default:
            break;
        }
    }

    @Override
    public void newGameNotification() {
        notificationLabel.setText("");
    }

}
