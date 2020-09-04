package controller;

import java.util.ArrayList;
import java.util.List;

import common.EFieldState;
import interfaces.IWinStateListener;

public class GameStateController {
    private List<IWinStateListener> stateListenerList;

    public GameStateController() {
        stateListenerList = new ArrayList<IWinStateListener>();
    }
    
    public void addWinStateListener(IWinStateListener stateListener) {
        stateListenerList.add(stateListener);
    }
    
    public void notifyOfGameEnd(EFieldState winner){
        for (IWinStateListener wsl : stateListenerList) {
            wsl.winNotification(winner);
        }
    }
    
    public void notifyOfNewGame() {
        for (IWinStateListener wsl : stateListenerList) {
            wsl.newGameNotification();
        }
    }

}
