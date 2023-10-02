package aiplayer;

public final class TikTakToeFieldRotationEvaluator {

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
    /**
     * Checks if a String resembles another when translated to an TikTakToeField. And calculates the rotations clockwise to do so. 
     * @param comparator
     * @param field
     * @return int 
     * The number of rotations clockwise needed for field to match the comparator and -1 if there is no matching rotation.
     */
    public static int fieldStatesMatchInRotationNumber (String comparator, String field) {      
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