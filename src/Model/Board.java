package Model;

import java.util.ArrayList;

public class Board {
    private ArrayList<NPC> npcs;
    private int npc_destroyed;

    public ArrayList<NPC> get_npcs(){
        return npcs;
    }

    public int get_npc_destroyed(){
        return npc_destroyed;
    }
}
