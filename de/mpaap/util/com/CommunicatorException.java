package de.mpaap.util.com;

/**
 * Wird von einem Communicator ausgelöst, wenn beim Aufbau einer Verbindung, beim Schreiben der Anfrage an den Server
 * oder beim Lesen der Antwort vom Server eine Exception auftitt. Die ursächliche Exception wird grundsätzlich
 * mitgeliefert und kann durch Aufruf von getCause() erhalten werden.
 * 
 * @author Michael Paap
 */
public class CommunicatorException extends Exception {

    /**
     * Erzeugt ein Exemplar dieser Klasse mit der übergebenen Nachricht und der übergebenen ursächlichen Exception.
     * 
     * @param message
     *            die Nachricht
     * @param cause
     *            die ursächliche Exception
     */
    public CommunicatorException(String message, Exception cause) {
        super(message, cause);
    }
}
