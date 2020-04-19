package Model;

public class Game {
    private static int money, npc_destroyed, score, num_asteroid, num_paths;
    private static Game instance;
    private static Wave curr_wave;


    private Game(){

    }

    public Game get_instance(){
        if (Game.instance==null){Game.instance = new Game();}
        return Game.instance;
    }

    public static boolean pay(int paid){
        if (money>=paid){
            money -= paid;
            return true;
        }
        else{
            return false;
        }
    }

    public static int get_npc_destroyed(){ return npc_destroyed;}
}
