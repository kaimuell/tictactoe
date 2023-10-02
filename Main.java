import common.*;
import controller.GameStateController;
import controller.MatchController;
import controller.PlayerSetupController;
import gui.ControlPanel;
import interfaces.IInputDevice;
import interfaces.IModel;
import interfaces.IPlayer;
import interfaces.IView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        IModel model = new Model();
        List<IView> viewList = new ArrayList<>(2);
        viewList.add(new ConsoleView(model));
        TicTacToePanel panel = new TicTacToePanel(model);

        IView view = panel;
        IInputDevice input = panel;
        JComponent compo = panel;

        TicTacToeFrame jctf = new TicTacToeFrame(compo);
        viewList.add(view);
        GameStateController gsc = new GameStateController();
        PlayerSetupController psc = new PlayerSetupController(input, gsc);
        IPlayer p1 = psc.getHumanPlayer(PlayerID.PLAYERONE);
        IPlayer p2 = psc.getHumanPlayer(PlayerID.PLAYERTWO);
        MatchController mc = new MatchController(p1, p2, model, viewList, gsc);
        ControlPanel controlPanel = new ControlPanel(mc, gsc, psc);
        jctf.add(controlPanel, BorderLayout.SOUTH);
        jctf.revalidate();
        jctf.pack();
    }
}
