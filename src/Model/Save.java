package Model;

import java.io.Serializable;

public class Save implements Serializable {
    private static final long serialVersionUID = 8L;
    Game game;
    Level level;
    Board board;
    int num_level;

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

    public int get_level(){return num_level;}
}
