package interfaces;

import java.awt.*;

import player.PlayerException;

public interface IPlayer {
    
    public Point getMove (String s) throws PlayerException;
                   
    
}
