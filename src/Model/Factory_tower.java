package Model;

public class Factory_tower extends Tower implements Runnable{
    private static int[] period = {20, 10, 5};
    private static int[] prod_money = {20, 50, 100};
    private static int max_level = 3;
    private int curr_level;
    private boolean money_produced;

    public Factory_tower(Asteroid asteroid) {
        super(asteroid);
        curr_level = super.get_curr_level();
    }

    public void run(){
        while (true){
            try{
                Thread.sleep(period[curr_level]);
                money_produced = true;
                while (money_produced){ }
            } catch(InterruptedException e){
                System.out.println("Erreur dans le sleep du thread des Factory_tower");
            }
        }
    }

    public int get_money(){
        money_produced = false;
        return prod_money[curr_level];
    }

    public boolean upgrade(){
        if (curr_level != max_level && Game.pay(get_price_upgrade(curr_level))){
            curr_level++;
            return true;
        }
        return false;
    }
}
