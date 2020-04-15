package Model;

import java.util.ArrayList;

public abstract class Tower {
    private Asteroid asteroid;
    private int curr_level, max_level;
    private int[] period, price_upgrade;
    private ArrayList<Munition> munitions;

    protected Tower(Asteroid asteroid, int[] period, int[] price_upgrade, int max_level){
        this.asteroid = asteroid;
        this.period = period;
        this.price_upgrade = price_upgrade;
        this.max_level = max_level;
        curr_level = 1;
    }

    public int get_curr_level(){
        return curr_level;
    }

    public int get_max_level(){
        return max_level;
    }

    public int get_price_upgrade(){
        return price_upgrade[curr_level];
    }

    public int get_period(){
        return period[curr_level];
    }

    public void increment_curr_level(){
        curr_level ++;
    }

    public Asteroid get_asteroid(){
        return asteroid;
    }

    public void add_munition(Munition munition){
        munitions.add(munition);
    }

    public abstract boolean upgrade();
}
