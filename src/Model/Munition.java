package Model;

public  class Munition {
    private NPC npc;
    private double pos_x, pos_y, dir_x, dir_y, speed;

    public Munition(Tower tower, NPC npc, double speed){
        this.npc = npc;
        this.pos_x = tower.get_asteroid().get_pos_x();
        this.pos_y = tower.get_asteroid().get_pos_y();
        this.speed = speed;
    }

    public void update(){
        //Direction is updated because the PNJ has moved since
        dir_x = npc.get_pos_x()-pos_x;
        dir_y = npc.get_pos_y()-pos_y;
        dir_x /= Math.sqrt(dir_x*dir_x+dir_y*dir_y); //Direction normée
        dir_y /= Math.sqrt(dir_x*dir_x+dir_y*dir_y);
        dir_x *= speed/10;
        dir_y *= speed/10;

        pos_x += dir_x;
        pos_y += dir_y;
    }

    public NPC get_npc(){return npc;}
    public double get_pos_x(){return pos_x;}
    public double get_pos_y(){return pos_y;}

}

