package aiplayer;

public final class FieldRotationEvaluator {

    public static String rotateFieldClockwise(String field) {
        StringBuilder builder = new StringBuilder();
        builder.append(field.charAt(6));
        builder.append(field.charAt(3));
        builder.append(field.charAt(0));
        builder.append(field.charAt(7));
        builder.append(field.charAt(4));
        builder.append(field.charAt(1));
        builder.append(field.charAt(8));
        builder.append(field.charAt(5));
        builder.append(field.charAt(2));
        return builder.toString();
    }
    
    public static int fieldStatesMatchInARotationNo (String comparator, String field) {      
        if (comparator.equals(field)) {
          return 0;  
        }
        for (int i = 0; i < 3; i++) {
            field = rotateFieldClockwise(field);
            if (comparator.equals(field)) {
                return i+1;
            }
        }
        
        return -1;        
    }
    
    public static String rotateFieldToRotation (String field, int rotation) {
        for (int i = 0; i < rotation; i++) {
            field = rotateFieldClockwise(field);
        }
        return field;
    }
}
