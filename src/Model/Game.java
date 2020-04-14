package Model;

public class Game {
    private static int money;

    public static boolean pay(int paid){
        if (money>=paid){
            money -= paid;
            return true;
        }
        else{
            return false;
        }
    }
}
