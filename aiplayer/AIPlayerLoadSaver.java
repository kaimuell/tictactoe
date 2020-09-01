package aiplayer;

import java.io.*;

public class AIPlayerLoadSaver {

    public AIPlayerLoadSaver() {
    }

    public AIPlayer loadAIPlayer(String filename) throws IOException, ClassNotFoundException{
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            AIPlayer aip = (AIPlayer) ois.readObject();
            ois.close();
            System.out.println(filename + " geladen");
            return aip;
    }
    
    public void saveAIPlayer(AIPlayer aiPlayer, String filename) {
        try {
            aiPlayer.resetDecisionTree();
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(aiPlayer);
            oos.close();
            System.out.println(filename + " gespeichert");
        } catch (Exception e) {
            System.out.println(filename + " konnte nicht erstellt werden");
            e.getStackTrace();
        }
    }
}
