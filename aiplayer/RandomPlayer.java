package aiplayer;

import java.awt.Point;
import java.util.Random;

import foo.IPlayer;
import foo.PlayerException;

public class RandomPlayer implements IPlayer{
    Random random;
    public RandomPlayer() {
        random = new Random();
}

    @Override
    public Point getZug(String s) throws PlayerException {
        s = s.trim();
        while (true) {
            int i = Math.abs(random.nextInt() %9);
                if (s.charAt(i) == '_');
                return new Point(((i )/ 3), ((i) % 3));
        }
    }
    

}
