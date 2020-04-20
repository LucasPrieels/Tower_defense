package Model;

public class Game implements Runnable{
    private static int money, npc_destroyed, score, num_asteroid, num_paths, time;
    private static Game instance;
    private static int curr_wave = -1;
    private boolean won = false, game_over = true;

    private Game(){

    }

    public Game get_instance(){
        if (Game.instance==null){Game.instance = new Game();}
        return Game.instance;
    }

    private void begin(){ //Appelé par un listener sur un bouton
        run();
        if (won){
            won();
        }
        else{
            game_over();
        }
    }

    public void won(){
        //...
    }

    public void game_over(){
        //...
    }

    public void run(){
        curr_wave++; // First wave is launched
        while (true){
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                System.out.println("Erreur dans le sleep de la classe Game");
            }
            time++;
            if (time == Level.get_time_wave(curr_wave)){
                time = 0;
                curr_wave++;
            }
            if (score<0){
                game_over = true;
                return;
            }
            if (curr_wave>Level.get_num_waves()){
                won = true;
                return;
            }
            create_npcs();
        }
    }

    private void create_npcs(){//Factory?
        for (int i=0; i<Level.get_time_small_npc(curr_wave, time); i++){
            int radius = Small_NPC.get_radius_static();
            int pos_x = Board.get_dim_x()-radius;
            int pos_y = random_pos_NPC(radius);
            Board.add_npc(new Small_NPC(pos_x, pos_y, Level.get_health_small_npc(curr_wave)));
        }
        for (int i=0; i<Level.get_time_med_npc(curr_wave, time); i++){
            int radius = Medium_NPC.get_radius_static();
            int pos_x = Board.get_dim_x()-radius;
            int pos_y = random_pos_NPC(radius);
            Board.add_npc(new Medium_NPC(pos_x, pos_y, Level.get_health_med_npc(curr_wave)));
        }
        for (int i=0; i<Level.get_time_big_npc(curr_wave, time); i++){
            int radius = Big_NPC.get_radius_static();
            int pos_x = Board.get_dim_x()-radius;
            int pos_y = random_pos_NPC(radius);
            Board.add_npc(new Big_NPC(pos_x, pos_y, Level.get_health_big_npc(curr_wave)));
        }
    }

    private int random_pos_NPC(int radius){
        int num_paths, path_chosen, width, pos_y;
        do {
            num_paths = Board.get_num_paths();
            path_chosen = (int) Math.round(Math.random() * num_paths);
            width = Board.get_width_path(path_chosen)-2*radius;
            pos_y = Board.get_ord_path(path_chosen) + (int)Math.random()*2*width - width;
        } while(!Board.empty(Board.get_dim_x(), pos_y, radius));
        return Board.get_ord_path(path_chosen);
    }

    public static boolean pay(int paid){
        if (money>=paid){
            money -= paid;
            return true;
        }
        else{
            return false;
        }
    }

    public static int get_npc_destroyed(){ return npc_destroyed;}

    public static void increment_npc_destroyed(){ npc_destroyed++;}
}
