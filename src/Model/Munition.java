package Model;

public  class Munition {

    private Tower tower;
    private NPC npc;
    private double pos_x, pos_y;
    private boolean hit = false;
    private int endpos_x,endpos_y;
// faut amener le projectile sur le npc
//de facon à que  les coordonées finals du projectile soit  endpos_x et endpos_y


    public Munition(Tower tower, NPC npc){
        this.tower = tower;
        this.npc = npc;
        this.pos_x = tower.get_asteroid().get_pos_x();
        this.pos_y = tower.get_asteroid().get_pos_y();
    }

    public boolean Hit_npc(NPC npc){
        if (endpos_x == npc.get_pos_x() && endpos_y == npc.get_pos_y()  ){
            hit = true;
        }
        return hit;

    }

    public NPC get_NPC(){return npc;}

    public double get_pos_x(){return pos_x;}
    public double get_pos_y(){return pos_y;}

}

