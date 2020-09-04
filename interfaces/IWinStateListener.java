package interfaces;

import common.EFieldState;

public interface IWinStateListener {
    void newGameNotification();
    void winNotification(EFieldState winner);
}
