package aiplayer;

import java.io.*;

public class AIPlayerLoadSaver {

    public AIPlayerLoadSaver() {
    }

    public AITreeNode loadAiPlayerDecisionTree(String filename) throws IOException, ClassNotFoundException{
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            AITreeNode aitn = (AITreeNode) ois.readObject();
            ois.close();
            System.out.println(filename + " geladen");
            return aitn;
    }
    
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
