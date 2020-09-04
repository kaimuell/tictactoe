package aiplayer;

import java.io.Serializable;
import java.util.*;

public class AITreeNode implements Serializable {

    private String field;
    private int weight;
    private List<AITreeNode> possibleMoves;
    private char aktPlayer;

    AITreeNode(String field) {
        this.field = field;
        weight = 0;
        possibleMoves = new ArrayList<AITreeNode>(9);
        evaluateAktualPlayer(field);
        generatePossibleMoves(field);
    }
    
    private void generatePossibleMoves(String f) {
        for (int i = 0; i < f.length(); i++) {
            if (f.charAt(i) == '_') {
                StringBuilder tempField = new StringBuilder();
                for (int j = 0; j < f.length(); j++) {
                    char aktChar = j == i ? aktPlayer : f.charAt(j);
                    tempField.append(aktChar);
                }
                possibleMoves.add(new AITreeNode(tempField.toString()));
            }
        }
    }

    private void evaluateAktualPlayer(String f) {
        int x = 0;
        int o = 0;
        for (int i = 0; i < f.length(); i++) {
            if (f.charAt(i) == 'x') {
                x++;
            } else if (f.charAt(i) == 'o') {
                o++;
            }
        }
        aktPlayer = x > o ? 'o' : 'x';
    }

    public List<AITreeNode> getPossibleMoves() {
        return possibleMoves;
    }

    public String getField() {
        return field.toString();
    }

    protected void decreaseWeight() {
        weight--;
    }
    
    protected void increaseWeight() {
        weight++;
    }
    
    protected void clearListOfPossibleMoves() {
        possibleMoves.clear();
    }
    
    public int getWeight() {
        return weight;
    }

    public void sortPossibleMoves() {
        possibleMoves.sort(Comparator.comparing(AITreeNode::getWeight));
    }

    @Override
    public String toString() {
        return field;
    }

}