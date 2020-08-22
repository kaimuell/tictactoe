package foo;

import java.awt.Point;

import de.mpaap.util.com.Communicator;
import de.mpaap.util.com.CommunicatorException;

public class NetworkPlayer implements IPlayer {
    private Communicator com;

    public NetworkPlayer(String host) {
        this.com = new Communicator(host, 7890,"UTF-8");
    }

    @Override
    public Point getZug(String s){
        try {
            String response = com.communicate(s);
            int i = Integer.parseInt(response.trim()) -1;
            int col = (i%3);
            int row = (i /3);
            return new Point(row, col);
        } catch (CommunicatorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
