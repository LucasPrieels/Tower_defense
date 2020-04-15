package Model;

public class Factory_tower extends Tower implements Runnable{
    private static int[] period = {20, 10, 5};
    private static int[] prod_money = {20, 50, 100};
    private static int[] price_upgrade = {200, 500};
    private static int[] npc_destroyed_needed = {10, 30};
    private static int max_level = 3;
    private boolean money_produced = false;

    public Factory_tower(Asteroid asteroid) {
        super(asteroid);
    }

    public void run(){
        while (true){
            try{
                Thread.sleep(period[super.get_curr_level()]);
                money_produced = true;
                while (money_produced){ }
            } catch(InterruptedException e){
                System.out.println("Erreur dans le sleep du thread des Factory_tower");
            }
        }
    }

    public int get_money() {
        money_produced = false;
        return prod_money[super.get_curr_level()];
    }

    public boolean upgrade() {
        if (super.get_curr_level() != max_level && Game.pay(price_upgrade[super.get_curr_level()])) {
            super.increment_curr_level();
            return true;
        }
        return false;
    }
}
