package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.file.FileSystems;

import javax.swing.*;

import aiplayer.AIPlayer;
import controller.GameStateController;
import controller.PlayerSetupController;

/**
 * Frame to load an AI Model
 */

public class AILoadingFrame extends JFrame{
    
    private final JLabel notificationLabel;
    private final JFrame aiLoadingFrame;

    
    AILoadingFrame(GameStateController gameStateController, PlayerSetupController playerSetupController) {
        super();
        aiLoadingFrame = this;
        String path = FileSystems.getDefault().getPath("src", "ai", "tiktaktoe.ai").toString();
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200,140));
        JTextField aiSource = new JTextField(path, 15);
        notificationLabel = new JLabel("");
        JLabel label = new JLabel ("Pfad der KI :");
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            try {
                String aipath = aiSource.getText();
                AIPlayer aiPlayer1 = new AIPlayer(gameStateController, aipath);
                playerSetupController.setAiPlayer(aiPlayer1);
                notificationLabel.setText("KI geladen");
            } catch (Exception ex) {
                notificationLabel.setText("Laden fehlgeschlagen");
            }
        });
        JButton closeButton = new JButton("CLOSE");
        closeButton.addActionListener(e -> aiLoadingFrame.setVisible(false));
        JLabel warningLabel = new JLabel("Laden dauert einige Sekunden");
        
        
        panel.add(label);
        panel.add(aiSource);
        panel.add(okButton, BorderLayout.SOUTH);
        panel.add(closeButton, BorderLayout.SOUTH);
        panel.add(warningLabel, BorderLayout.SOUTH);
        panel.add(notificationLabel, BorderLayout.SOUTH);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
        setAlwaysOnTop(true);
    }

    
    
    
}

