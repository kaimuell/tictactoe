package foo;

import java.awt.Dimension;

import javax.swing.*;


public class ControlPanel extends JPanel implements IPlayerSettings, IWinStateListener{

    private PlayerChoice playerOne;
    private PlayerChoice playerTwo;
    private JLabel notificationLabel;

    public ControlPanel(MatchController matchController, GameStateController gameStateController, IInputDevice inputDevice) {
        
        gameStateController.addWinStateListener(this);
        this.setPreferredSize(new Dimension(450, 150));
        playerOne = new PlayerChoice(inputDevice, gameStateController);
        playerTwo = new PlayerChoice(inputDevice, gameStateController);
        JButton startButton = new StartButton(matchController, gameStateController, this);
        notificationLabel = new JLabel("");
        
        add(notificationLabel);
        add(playerOne);
        add(startButton);
        add(playerTwo);
        
        
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





