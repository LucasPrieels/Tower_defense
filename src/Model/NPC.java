package Model;

public abstract class NPC{
    private double pos_x, pos_y;
    private int health;
    private double speed, freezed = 0.0;
    private Path2 path;

    protected NPC(double pos_x, double pos_y, double speed, int health, Path2 path){ //Protected pour empêcher de créer un PNJ sans préciser si il est petit, moyen ou grand
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.speed = speed;
        this.health = health;
        this.path = path;
    }

    public double get_pos_x(){return pos_x;}
    public double get_pos_y(){return pos_y;}

    public void set_pos_x(double pos_x){this.pos_x = pos_x;}
    public void set_pos_y(double pos_y){this.pos_y = pos_y;}

    public boolean is_shot(Classic_munition munition){
        boolean res = check_shot(munition);
        if (res){
            health -= munition.get_tower().get_power();
            if (health <= 0) Board.remove_npc(this);
        }
        return res;
    }

    public boolean is_shot(Freezing_munition munition){
        boolean res = check_shot(munition);
        if (res) set_freezed(munition.get_tower().get_power());
        return res;
    }

    private void set_freezed(double time){
        freezed = time;
    }

    public void decrease_freezed(double time){
        if (freezed>0){
            freezed -= time;
            freezed = Math.max(freezed, 0);
        }
    }

    private boolean check_shot(Munition munition){
        double munition_pos_x = munition.get_pos_x();
        double munition_pos_y = munition.get_pos_y();
        if(Math.sqrt(Math.pow(munition_pos_x - pos_x, 2)+ Math.pow(munition_pos_y - pos_y, 2))<10) return true;
        return false;
    }

    public Path2 get_path(){ return path;}
    public double get_speed(){ return speed;}
    public double is_frozen(){ return freezed;}

    public abstract int get_radius();
}
