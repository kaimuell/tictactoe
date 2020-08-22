package foo;

public enum EFieldState {
    CROSS("x"), CIRCLE("o"), EMPTY("_");
    
    private final String ZEICHEN;
    
    private EFieldState(String zeichen) {
        this.ZEICHEN = zeichen;
    }

    public String getZEICHEN() {
        return ZEICHEN;
    }

}
