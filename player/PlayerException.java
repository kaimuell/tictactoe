package player;

/**
 * Exception die von einem Spieler ausgelöst werden kann
 *
 */
public class PlayerException extends Exception {

    /**
     * Konstruktor
     * 
     * @param message die Fehler-Nachricht
     */
    public PlayerException(String message) {
        super(message);
    }

    /**
     * Konstruktor
     * 
     * @param cause der Auslöser der Exception
     */
    public PlayerException(Throwable cause) {
        super(cause);
    }

    /**
     * Konstruktor
     * 
     * @param message message die Fehler-Nachricht
     * @param cause   der Auslöser der Exception
     */
    public PlayerException(String message, Throwable cause) {
        super(message, cause);
    }

}
