package Model;

public abstract class Tower {
    private int upgrade_level;
    private Asteroid asteroid;
    public Tower(Asteroid asteroid){
        this.asteroid = asteroid;
        upgrade_level = 1;
    }
    public abstract void upgrade();

    public abstract void delete();
}
