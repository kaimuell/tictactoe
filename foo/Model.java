package foo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Model implements IModel {
    EFieldState[][] fieldStates;
    List<Point> winningFields;

    public Model() {
        this.fieldStates = new EFieldState[3][3];
        for (int i = 0; i < fieldStates.length; i++) {
            for (int j = 0; j < fieldStates[i].length; j++) {
                fieldStates[i][j] = EFieldState.EMPTY;
            }
        }
        this.winningFields = new ArrayList<Point>(3);
    }

    @Override
    public EFieldState getFeldZustand(int row, int column) {
        return fieldStates[row][column];
    }

    @Override
    public void setFeldZustand(int row, int column, EFieldState state) {
        this.fieldStates[row][column] = state;
    }

    @Override
    public String toServerString() {
        StringBuilder stringbuilder = new StringBuilder(10);
        for (int i = 0; i < fieldStates.length; i++) {
            for (int j = 0; j < fieldStates[i].length; j++) {
                stringbuilder.append(fieldStates[i][j].getZEICHEN());
            }
        }
        stringbuilder.append("\n");
        return stringbuilder.toString();

    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder(12);
        for (int i = 0; i < fieldStates.length; i++) {
            for (int j = 0; j < fieldStates[i].length; j++) {
                stringbuilder.append(fieldStates[i][j].getZEICHEN());
            }
            stringbuilder.append("\n");
        }

        return stringbuilder.toString();

    }

    @Override
    public void setWinningFields(int row, int col) {
        winningFields.add(new Point(row, col));
    }

    @Override
    public List<Point> getWinningFields() {
        return winningFields;
    }
}
