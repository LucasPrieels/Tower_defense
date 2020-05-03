package Model;

import View.Map;

import java.io.Serializable;

public class Save implements Serializable {
    private static final long serialVersionUID = 7L;
    Game game;
    Level level;
    Board board;
    int num_level;
    //Map map;

    public Save(){
        game = Game.get_instance();
        level = Level.get_instance();
        board = Board.get_instance();
        num_level = Map.get_instance().get_level();
        //map = Map.get_instance();
    }

    public void init(){
        Game.set_instance(game);
        Level.set_instance(level);
        Board.set_instance(board);
        //Map.set_instance(map);
    }

    public int get_level(){return num_level;}
}
