package interfaces;

import java.awt.Point;
import java.util.List;

import common.EFieldState;

public interface IModel {

    EFieldState getFieldState(int row, int column);
    void setFieldState(int row, int column, EFieldState state);
    String toServerString();
    List<Point> getWinningFields();
    void setWinningFields(int row, int col);
    void resetModel();
}
