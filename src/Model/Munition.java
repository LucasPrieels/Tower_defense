package Model;

public abstract class Munition {
    private Tower tower;
    private NPC npc;
    private int pos_x, pos_y;

    public Munition(Tower tower, NPC npc){
        this.tower = tower;
        this.npc = npc;
        this.pos_x = tower.get_asteroid().get_pos_x();
        this.pos_y = tower.get_asteroid().get_pos_y();
    }

    public int get_pos_x(){return pos_x;}
    public int get_pos_y(){return pos_y;}
}
