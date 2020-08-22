package de.mpaap.util.com;

import java.io.*;
import java.net.*;

/**
 * Ein Exemplar dieser Klasse repräsentiert eine Kommunikationsschnittstelle zu einem Server, der eine Anfrage in Form
 * von Text entgegennimmt, mit einer Nachricht in Form von Text antwortet und dann die Verbindung beendet.
 * 
 * @author Michael Paap
 */
public class Communicator {

    private String host;

    private int port;

    private String encoding;

    /**
     * Erstellt ein Exemplar dieser Klasse für die Kommunikation mit dem übergebenen Server, Port und Encoding.
     * 
     * @param host
     *            Server, der benutzt werden soll, als auflösbarer Servername oder IP-Adresse
     * @param port
     *            Port, der benutzt werden soll. Port muss zwischen 0 und 65535 liegen.
     * @param encoding
     *            der Name des zu verwendenden Encodings, z.B. "UTF-8"
     */
    public Communicator(String host, int port, String encoding) {
        this.host = host;
        this.port = port;
        this.encoding = encoding;
    }


    /**
     * Sendet eine Nachricht an den Server und liefert die Antwort als String.
     * 
     * @param message
     *            Nachricht die an den Server gesendet werden soll
     * @return die vom Server gelesene Antwort
     * @throws CommunicatorException
     *             wenn beim Aufbau der Verbindung, beim Lesen oder beim Schreiben eine Exception auftritt. Die
     *             ursächliche Exception wird mitgegeben und kann beim CommunicatorException-Exemplar mit getCause()
     *             abgefragt werden.
     */
    public String communicate(String message) throws CommunicatorException {
        Socket socket = null;
        try {
            socket = connectToServer();
            sendRequestToServer(message, socket);
            return readResponseFromServer(socket);
        } finally {
            closeSocket(socket);
        }
    }

    /**
     * @return
     * @throws CommunicatorException
     */
    private Socket connectToServer() throws CommunicatorException {
        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(2000); // timeout 2 seconds
            return socket;
        } catch (IOException e) {
            throw new CommunicatorException("An exception occured while trying to connect to server. ", e);
        }
    }

    private void sendRequestToServer(String message, Socket socket) throws CommunicatorException {
        try {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(message);
            bw.flush();
        } catch (IOException e) {
            throw new CommunicatorException("An exception occured while trying to write to server.", e);
        }
    }

    private String readResponseFromServer(Socket socket) throws CommunicatorException {
        try {
            InputStream in = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(in, encoding);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            int c = -1;
            while ((c = br.read()) != -1) {
                sb.append((char) c);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new CommunicatorException("An exception occured while trying to read from server.", e);
        }
    }

    private void closeSocket(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ioe) {
            // Hier gibts dann auch nichts mehr zu tun
        }
    }
}