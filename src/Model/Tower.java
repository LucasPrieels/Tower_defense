package Model;

public abstract class Tower {
    private int curr_level;
    private Asteroid asteroid;
    private static int[] price_upgrade = {200, 500};

    protected Tower(Asteroid asteroid){
        this.asteroid = asteroid;
        curr_level = 1;
    }

    public int get_curr_level(){
        return curr_level;
    }

    public int get_price_upgrade(int curr_level){
        return price_upgrade[curr_level];
    }
    public abstract boolean upgrade();
}
