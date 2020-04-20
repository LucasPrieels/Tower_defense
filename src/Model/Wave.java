package Model;

import java.util.ArrayList;

public class Wave {
    public Wave(int health_small_npc, int speed_small_npc, int health_med_npc, int speed_med_npc, int health_big_npc, int speed_big_npc, ArrayList<Integer> time_small_npc, ArrayList<Integer>  time_med_npc, ArrayList<Integer> time_big_npc){
        //...
        //health_npc et speed_npc sont les santés et vitesses initiales des PNJ de la vague
        //Les trois ArrayList contiennent une liste d'entiers représentant les temps d'apparition du type respectif de PNJ
        //Par exemple si time_small_npc = {1, 3, 7} ça veut dire qu'il faut faire apparaitre un petit PNJ après 1, 3, et 7 secondes (faire une classe Time ou des threads?)

        //A mon avis le mieux est de faire un ArrayList<ArrayList<NPC>> où chaque élément i de la liste est une liste des PNJ en le temps i
        //Mais attention parce que les NPC doivent être des Small/Medium/Big_NPC et pas simplement des NPC, donc il faut faire du polymorphisme pour les mettre dans la même liste
        //Pour créer un NPC différent en fonction de ce qui est demandé, se renseigner sur les Factories (voir TP 5 sur les design patterns)
    }
}
