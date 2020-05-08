package Tests;

import static org.junit.Assert.*;

import Model.*;
import View.Map;
import javafx.application.Platform;
import kuusisto.tinysound.TinySound;
import org.junit.Test;

import java.util.ArrayList;

public class TestBoard {
    // We assume all the methods in Game have already been tested and work properly

    public ArrayList<Path2> create_random_path(){
        ArrayList<Path2> paths = new ArrayList<>();
        double[] path = new double[500];
        for (int i=0; i<500; i++){
            path[i] = Math.random()*200;
        }
        paths.add(new Path2(path, 15));
        return paths;
    }

    public void init_board(int dim_x, int dim_y, int margin_x, int margin_y, int width_path, int size_asteroid, int max_offset, double proba){
        Board.set_instance(null);
        ArrayList<Path2> paths = new ArrayList<>();
        //paths.add(new Path2(Game.construct_path(dim_x, dim_y, dim_y/2,  width_path, 0), width_path));
        Game.get_instance().construct_path(dim_x,Map.get_level());
        Board.init(dim_x, dim_y, margin_x, margin_y, width_path, proba, size_asteroid, max_offset, paths);
    }

    public void init(){
        Board.set_instance(null);
        Level.set_instance(null);
        Game.set_instance(null);
        Game.get_instance();
    }

    @Test
    public void testInit() { // Checks the init() function normally
        ArrayList<Path2> paths = create_random_path();
        Board.set_instance(null);
        Board.init(500, 200, 20, 20, 15, 10, 1, 10, paths);
        assertEquals(Board.get_instance().get_paths(), paths);
    }

    @Test(expected=AssertionError.class)
    public void testInvalidInit(){ // Checks that there is an AssertionError if we're trying to initialize twice the Board
        Board.set_instance(null);
        Board.init(10, 200, 20, 20, 15, 10, 1, 10, create_random_path());
        Board.init(10, 200, 20, 20, 15, 10, 1, 10, create_random_path());
        // We can't initialize the board twice
    }

    @Test(expected=AssertionError.class)
    public void testInvalidGetInstance(){ // Checks that we can't access an instance of Board before initializing it
        Board.set_instance(null);
        Board.get_instance();
    }

    @Test
    public void testCreateAsteroidsNotTooCloseBorders(){ // Checks that none of the asteroids are too close of the borders
        int dim_x = 500, dim_y = 200, margin_x = 20, margin_y = 20, width_path = 15, size_asteroid = 10, max_offset = 10;
        double proba = 1;
        init_board(dim_x, dim_y, margin_x, margin_y, width_path, size_asteroid, max_offset, proba);
        Board.get_instance().create_asteroids_random();

        for (Asteroid asteroid: Board.get_instance().get_asteroids()){
            assertTrue(asteroid.get_pos_x() > margin_x);
            assertTrue(asteroid.get_pos_x() < dim_x - margin_x - Map.get_size_asteroid()/2);
            assertTrue(asteroid.get_pos_y() > margin_y);
            assertTrue(asteroid.get_pos_y() < dim_y -  margin_y - Map.get_size_asteroid()/2);
        }
    }

    @Test
    public void testCreateAsteroidsDistancePaths(){ // Checks that every asteroid is close enough from a path, but not too close from any
        init();
        int max_offset = Board.get_instance().get_max_offset();

        for (Asteroid asteroid: Board.get_instance().get_asteroids()){
            boolean cond = false;
            for (int i=0; i<Board.get_instance().get_paths().size(); i++) {
                Path2 path = Board.get_instance().get_paths().get(i);
                cond = cond | (Math.abs(asteroid.get_pos_y() - path.get_ord((int) Math.round(asteroid.get_pos_x()))) <= (((double) Board.get_instance().get_width_path(i) / 2) + (Map.get_size_asteroid() / 2) + max_offset));
            }
            assertTrue(cond);

            // But not too close
            cond = true;
            for (int i=0; i<Board.get_instance().get_paths().size(); i++) {
                Path2 path = Board.get_instance().get_paths().get(i);
                cond = cond & (Math.abs(asteroid.get_pos_y() - path.get_ord((int) Math.round(asteroid.get_pos_x()))) >= (((double) Board.get_instance().get_width_path(i) / 2) + (Map.get_size_asteroid() / 2)));
            }
            assertTrue(cond);
        }
    }

    @Test
    public void testCreateAsteroidsNotTooClose(){ // Checks that distance between each pair of asteroids is big enough so that their images don't overlap
        init();

        for (int i=0; i<Board.get_instance().get_asteroids().size(); i++){
            for (int j=0; j<Board.get_instance().get_asteroids().size(); j++){
                if (i == j) continue;
                Asteroid asteroid1 = Board.get_instance().get_asteroids().get(i);
                Asteroid asteroid2 = Board.get_instance().get_asteroids().get(j);
                assertTrue(Math.sqrt((Math.pow(asteroid1.get_pos_x() - asteroid2.get_pos_x(), 2) + Math.pow(asteroid1.get_pos_y() - asteroid2.get_pos_y(), 2))) > Map.get_size_asteroid());
            }
        }
    }

    @Test
    public void testAddMunition(){ // Checks the adding of a munition normally
        init();
        TinySound.init();
        Munition munition = new Classic_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), new Small_NPC(10, 5, 5, 10, Board.get_instance().get_paths().get(0)));
        Board.get_instance().add_munition(munition);
        Munition munition2 = new Freezing_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), new Small_NPC(10, 5, 5, 10, Board.get_instance().get_paths().get(0)), 10);
        Board.get_instance().add_munition(munition2);
        assertEquals(Board.get_instance().get_munitions().size(), 2);
        assertEquals(munition, Board.get_instance().get_munitions().get(0));
        assertEquals(munition2, Board.get_instance().get_munitions().get(1));
        TinySound.shutdown();
    }

    @Test
    public void testRemoveMunitionPresent(){ // Checks the removing of a munition that is in the munitions
        init();
        Board.get_instance().create_asteroids_random();
        TinySound.init();
        Munition munition = new Classic_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), new Small_NPC(10, 5, 5, 10, Board.get_instance().get_paths().get(0)));
        Board.get_instance().add_munition(munition);
        Munition munition2 = new Freezing_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), new Small_NPC(10, 5, 5, 10, Board.get_instance().get_paths().get(0)), 10);
        Board.get_instance().add_munition(munition2);

        Board.get_instance().remove_munition(munition);
        assertEquals(Board.get_instance().get_munitions().size(), 1);
        assertEquals(Board.get_instance().get_munitions().get(0), munition2);
        TinySound.shutdown();
    }

    @Test
    public void testRemoveMunitionAbsent(){ // Checks the removing of a munition that isn't in the munitions
        init();
        TinySound.init();
        Board.get_instance().create_asteroids_random();

        Munition munition = new Freezing_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), new Small_NPC(10, 5, 5, 10, Board.get_instance().get_paths().get(0)), 10);
        Board.get_instance().add_munition(munition);

        Munition munition2 = new Classic_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), new Small_NPC(10, 5, 5, 10, Board.get_instance().get_paths().get(0)));
        Board.get_instance().remove_munition(munition2);

        assertEquals(Board.get_instance().get_munitions().size(), 1);
        TinySound.shutdown();
    }

    @Test
    public void testAddNPC(){ // Checks the adding of a NPC normally
        init();
        NPC npc = new Small_NPC(5, 10, 10, 10, Board.get_instance().get_paths().get(0));
        Board.get_instance().add_npc(npc);

        assertEquals(Board.get_instance().get_npcs().size(), 1);
        assertEquals(Board.get_instance().get_npcs().get(0), npc);
    }

    @Test
    public void testRemoveNPCPresent(){ // Checks the removing of a NPC that is in npcs
        init();
        NPC npc1 = new Small_NPC(5, 10, 10, 10, Board.get_instance().get_paths().get(0));
        NPC npc2 = new Big_NPC(10, 10, 15, 10, Board.get_instance().get_paths().get(0));
        Board.get_instance().add_npc(npc1);
        Board.get_instance().add_npc(npc2);

        assertTrue(Board.get_instance().remove_npc(npc1));

        assertEquals(Board.get_instance().get_npcs().size(), 1);
        assertEquals(Board.get_instance().get_npcs().get(0), npc2);
    }

    @Test
    public void testRemoveNPCAbsent(){ // Checks the removing of a NPC that isn't in the npcs
        init();
        NPC npc1 = new Small_NPC(5, 10, 10, 10, Board.get_instance().get_paths().get(0));
        NPC npc2 = new Big_NPC(10, 10, 15, 10, Board.get_instance().get_paths().get(0));
        Board.get_instance().add_npc(npc1);
        Board.get_instance().add_npc(npc2);

        assertFalse(Board.get_instance().remove_npc(new Medium_NPC(10, 5, 5, 10, Board.get_instance().get_paths().get(0))));

        assertEquals(Board.get_instance().get_npcs().size(), 2);
    }

    @Test
    public void testRemoveNPCRemovesMunitions(){ // Checks that if we remove a NPC, all the munitions going towards it are destroyed
        init();
        TinySound.init();
        NPC npc1 = new Small_NPC(5, 10, 10, 10, Board.get_instance().get_paths().get(0));
        NPC npc2 = new Big_NPC(10, 10, 15, 10, Board.get_instance().get_paths().get(0));
        Board.get_instance().add_npc(npc1);
        Board.get_instance().add_npc(npc2);

        Munition munition = new Classic_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), npc1);
        Board.get_instance().add_munition(munition);

        Platform.startup(() -> {assertTrue(Board.get_instance().remove_npc(npc1));}); // This launches a Platform.runLater() then it needs to be launched from a JavaFX thread
        try{
            Thread.sleep(100); // We have to wait a bit for checking because the destruction of munitions is in a Platform.runLater()
        } catch (InterruptedException e){}

        assertEquals(Board.get_instance().get_munitions().size(), 0);
        TinySound.shutdown();
    }

    @Test
    public void testEmptyTrue(){ // Checks when the empty function should return true
        init();
        NPC npc1 = new Small_NPC(5, 10, 10, 10, Board.get_instance().get_paths().get(0));
        Board.get_instance().add_npc(npc1);

        assertTrue(Board.get_instance().empty(10, 5, 5));
    }

    @Test
    public void testEmptyFalse(){ // Checks when the empty function should return false
        init();
        NPC npc1 = new Small_NPC(5, 10, 10, 10, Board.get_instance().get_paths().get(0));
        Board.get_instance().add_npc(npc1);

        assertFalse(Board.get_instance().empty(10, 10, 5));
    }
}
