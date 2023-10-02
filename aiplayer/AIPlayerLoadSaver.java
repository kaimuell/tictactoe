package aiplayer;

import java.io.*;

/**
 * Klasse um KI-Dateien zu laden und zu speichern
 * 
 */
public class AIPlayerLoadSaver {

    /**
     * Konstruktor
     */
    public AIPlayerLoadSaver() {
    }

    /**
     * Methode um einen KI Entscheidungsbaum aus einer Datei zu laden
     * 
     * @param filename der Pfad der Datei
     * @return den Entscheidungsbaum
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public AITreeNode loadAiPlayerDecisionTree(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        AITreeNode aitn = (AITreeNode) ois.readObject();
        ois.close();
        System.out.println(filename + " geladen");
        return aitn;
    }

    /**
     * Methode um den Entscheidungsbaum eines KI-Spielers in eine Datei zu speichern
     * 
     * @param aiPlayer der KI-Spieler
     * @param filename der Pfad der zu speichernden Datei.
     */
    public void saveAiPlayerDecisionTree(AIPlayer aiPlayer, String filename) {
        try {
            aiPlayer.resetDecisionTree();
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(aiPlayer.getTreeNodeHeader());
            oos.close();
            System.out.println(filename + " gespeichert");
        } catch (Exception e) {
            System.out.println(filename + " konnte nicht erstellt werden");
            e.getStackTrace();
        }
    }
}
