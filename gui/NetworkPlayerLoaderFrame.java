package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.PlayerSetupController;
import player.NetworkPlayer;

public class NetworkPlayerLoaderFrame extends JFrame {

    PlayerSetupController playerSetupController;
    JFrame networkFrame;

    NetworkPlayerLoaderFrame(PlayerSetupController playerSetupController) {
        super();
        networkFrame = this;
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 200));
        this.playerSetupController = playerSetupController;
        setPreferredSize(new Dimension(200, 150));
        JLabel label = new JLabel("Pfad des Netzwerkspielers : ");
        JTextField sourceTextField = new JTextField("localhost", 15);
        JLabel notificationLabel = new JLabel("");
        JButton connectButton = new JButton("CONNECT");
        connectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    NetworkPlayer networkPlayer = new NetworkPlayer(sourceTextField.getText());
                    networkPlayer.getMove("_________\n");
                    playerSetupController.setNetworkPlayer(networkPlayer);
                    notificationLabel.setText("Verbunden mit " + sourceTextField.getText());
                } catch (Exception ex) {
                    notificationLabel.setText("Verbindung fehlgeschlagen");
                }

            }
        });

        JButton closeButton = new JButton("CLOSE");
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                networkFrame.setVisible(false);
            }
        });

        panel.add(notificationLabel, BorderLayout.NORTH);
        panel.add(label, BorderLayout.CENTER);
        panel.add(sourceTextField, BorderLayout.CENTER);
        panel.add(connectButton, BorderLayout.SOUTH);
        panel.add(closeButton, BorderLayout.SOUTH);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
        setAlwaysOnTop(true);
    }

}
