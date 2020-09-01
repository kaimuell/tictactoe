package foo;

import java.awt.Point;
import java.util.List;

public interface IModel {

    EFieldState getFeldZustand(int row, int column);
    void setFeldZustand(int row, int column, EFieldState state);
    String toServerString();
    List<Point> getWinningFields();
    void setWinningFields(int row, int col);
}
