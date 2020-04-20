package Model;

public abstract class NPC implements Runnable {
    protected double pos_x, pos_y;
    protected int health;
    protected Thread t;
    protected int time;

    protected NPC(double pos_x, double pos_y, int health){ //Protected pour empêcher de créer un PNJ sans préciser si il est petit, moyen ou grand
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.health = health;
        t = new Thread(this);
        t.start();
    }

    public double get_pos_x(){ return pos_x; }
    public double get_pos_y(){ return pos_y; }

    public void run() {
        try {
            time = 0;
            while (true) {
                time++;
                Thread.sleep(1000);
            }
        } catch (Exception e) {};
    }

    private boolean is_shot(Munition munition){
        boolean res = false;
        double munition_pos_x = munition.get_pos_x();
        double munition_pos_y = munition.get_pos_y();
        if(munition_pos_x == pos_x && munition_pos_y == pos_y) {
            res = true;
        }
        return res;
    }

    private void is_damaged(int damage){
        if(health-damage > 0){
            health -= damage;
        }
    }

    private void is_destroyed(){
        if(health<=0){
            Board.remove_npc(this);
            Game.increment_npc_destroyed();
        }
    }

    private void is_frozen(int time){
        //trouver moyen de gérer le temps
    }

    private void npc_evolution(NPC npc, Munition munition, int damage){
        if(npc.is_shot(munition)){
            if(health - damage > 0){
                npc.is_damaged(damage);
            }
            else{
                npc.is_destroyed();
            }
        }
    }

    public abstract int get_radius();
}
