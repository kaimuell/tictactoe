package foo;

public interface IWinStateListener {
    void newGameNotification();
    void winNotification(EFieldState winner);
}
