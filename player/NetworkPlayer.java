package player;

import java.awt.Point;

import de.mpaap.util.com.Communicator;
import de.mpaap.util.com.CommunicatorException;
import interfaces.IPlayer;

public class NetworkPlayer implements IPlayer {
    private Communicator com;

    public NetworkPlayer(String host) {
        this.com = new Communicator(host, 7890,"UTF-8");
    }

    @Override
    public Point getMove(String s) throws PlayerException{
        try {
            String response = com.communicate(s);
            if (response.startsWith("Error")) {
                throw new PlayerException(response);
            }
            int i = Integer.parseInt(response.trim()) -1;
            int row = (i /3);
            int col = (i%3);
            return new Point(row, col);
        } catch (CommunicatorException | NumberFormatException e) {
               throw new PlayerException(e);
        }
    }

}
