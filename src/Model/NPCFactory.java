package Model;

import java.util.ArrayList;

public class NPCFactory {
    public NPCFactory(){}

    public static void add_npcs(ArrayList<ArrayList<Integer>> time_npc, ArrayList<Double> speed_npc, ArrayList<Integer> health_npc, int time){
        for (int i = 0; i < time_npc.size(); i++){
            if (time < time_npc.get(i).size()) {
                for (int j=0; j < time_npc.get(i).get(time); j++){ // Number of NPC of type i to create at time "time"
                    int radius = 0;
                    switch (i) {
                        case 0:
                            radius = Small_NPC.get_radius_static();
                            break;
                        case 1:
                            radius = Medium_NPC.get_radius_static();
                            break;
                        case 2:
                            radius = Big_NPC.get_radius_static();
                            break;
                    }
                    double pos_x = Board.get_instance().get_dim_x() - radius;
                    double[] res = random_pos_npc(radius);
                    Path_custom path = Board.get_instance().get_paths().get((int) res[0]);
                    double pos_y = res[1];
                    NPC npc = null;
                    switch (i) {
                        case 0:
                            npc = new Small_NPC(pos_x, pos_y, speed_npc.get(i), health_npc.get(i), path);
                            break;
                        case 1:
                            npc = new Medium_NPC(pos_x, pos_y, speed_npc.get(i), health_npc.get(i), path);
                            break;
                        case 2:
                            npc = new Big_NPC(pos_x, pos_y, speed_npc.get(i), health_npc.get(i), path);
                            break;
                    }
                    System.out.println("Added NPC");
                    Board.get_instance().add_npc(npc);
                }
            }
        }
    }

    private static double[] random_pos_npc(int radius){
        int num_paths, path_chosen, width;
        double pos_y;
        do {
            num_paths = Board.get_instance().get_num_paths();
            path_chosen = (int) Math.floor(Math.random() * num_paths);
            width = Board.get_instance().get_width_path(path_chosen)-2*radius;
            pos_y = Board.get_instance().get_ord_path(path_chosen) + Math.random()*2*width - width;
            //System.out.println("pos_y " + pos_y + " " + Board.get_instance().get_ord_path(path_chosen));
        } while(!Board.get_instance().empty(Board.get_instance().get_dim_x(), pos_y, radius));
        return new double[]{path_chosen, pos_y};
    }
}
