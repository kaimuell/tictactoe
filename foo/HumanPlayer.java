package foo;

import java.awt.Point;

public class HumanPlayer implements IPlayer, IMoveListener {

    private volatile Point move; //volatile - verhindert das Cachen der variable move 
    private IInputDevice device;

    public HumanPlayer(IInputDevice device) {
        this.device = device;
    }

    @Override
    public void moveOccured(int row, int col) {
        this.move = new Point(row, col);
    }

    @Override
    public Point getZug(String s) throws PlayerException {
        move = null;
        device.setMoveListener(this);
        while (move==null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return move;
    }
 
}
