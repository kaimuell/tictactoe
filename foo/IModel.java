package foo;

public interface IModel {

    EFieldState getFeldZustand(int row, int column);
    void setFeldZustand(int row, int column, EFieldState state);
    String toServerString(); 
    
}
