package Model;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class Save implements Serializable {
    private static final long serialVersionUID = 8L;
    private Game game;
    private Level level;
    private Board board;
    private int num_level;

    public Save(){
        game = Game.get_instance();
        level = Level.get_instance();
        board = Board.get_instance();
        num_level = Game.get_instance().get_num_level();
    }

    public void init(){
        Game.set_instance(game);
        Level.set_instance(level);
        Board.set_instance(board);
    }

    public int get_level(){ return num_level;}
}
