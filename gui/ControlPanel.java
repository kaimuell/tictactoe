package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

import common.*;
import controller.*;
import interfaces.*;

public class ControlPanel extends JPanel implements IPlayerSettings, IWinStateListener, IPlayerSetupListener {

    private PlayerChoice playerOne;
    private PlayerChoice playerTwo;
    private JTextField notificationLabel;
    private  AILoadingFrame aiLoadingFrame;

    public ControlPanel(MatchController matchController, GameStateController gameStateController,
        PlayerSetupController playerSetupController) {
        gameStateController.addWinStateListener(this);
        playerSetupController.addPlayerSetupListener(this);
        this.setPreferredSize(new Dimension(450, 100));
        playerOne = new PlayerChoice(PlayerID.PLAYERONE, playerSetupController);
        playerTwo = new PlayerChoice(PlayerID.PLAYERTWO, playerSetupController);
        JButton startButton = new StartButton(matchController, gameStateController, this);
        notificationLabel = new JTextField("", 30);
        notificationLabel.setEditable(false);
        aiLoadingFrame = new AILoadingFrame(gameStateController, playerSetupController);
        NetworkPlayerLoaderFrame networkPlayerLoaderFrame= new NetworkPlayerLoaderFrame(playerSetupController); 
        JButton aiButton = new JButton("LOAD AI");
        aiButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                aiLoadingFrame.setVisible(true);                
            }
        });
        
        JButton networkButton = new JButton("CONNECT TO NETWORK");
        networkButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                networkPlayerLoaderFrame.setVisible(true);
            }
        });
        
        add(notificationLabel, BorderLayout.NORTH);
        add(playerOne, BorderLayout.CENTER);
        add(startButton, BorderLayout.CENTER);
        add(playerTwo, BorderLayout.CENTER);
        add(aiButton, BorderLayout.SOUTH);
        add(networkButton, BorderLayout.SOUTH);
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

    @Override
    public void playersUpdated() {
        playerOne.checkForNetworkPlayer();
        playerOne.checkForAIPlayer();
        playerTwo.checkForNetworkPlayer();
        playerTwo.checkForAIPlayer();
    }

}
