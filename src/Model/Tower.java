package Model;

public abstract class Tower {
    private Asteroid asteroid;
    private int curr_level;

    protected Tower(Asteroid asteroid){
        this.asteroid = asteroid;
        curr_level = 1;
    }

    public int get_curr_level(){
        return curr_level;
    }

    public void increment_curr_level(){
        curr_level ++;
    }

    public abstract boolean upgrade();
}
