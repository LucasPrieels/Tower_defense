package Tests;

import static org.junit.Assert.*;
import Model.*;
import javafx.application.Platform;
import javafx.util.Pair;
import kuusisto.tinysound.TinySound;
import org.junit.Test;

public class TestBoard {
    // We assume all the methods in Game have already been tested and work properly
    private int level = 1; // Modify to test the unit tests with the different level

    private void init(){
        Board.set_instance(null);
        Level.set_instance(null);
        Game.set_instance(null);
        Game.init(level);
    }

    @Test
    public void testInit() { // Checks the init() function normally
        Board.set_instance(null);
        Board.init(level, 15);
    }

    @Test(expected=AssertionError.class)
    public void testInvalidInit(){ // Checks that there is an AssertionError if we're trying to initialize twice the Board
        Board.set_instance(null);
        Board.init(level, 15);
        Board.init(level, 15);
    }

    @Test(expected=AssertionError.class)
    public void testInvalidGetInstance(){ // Checks that we can't access an instance of Board before initializing it
        Board.set_instance(null);
        Board.get_instance();
    }

    @Test
    public void testCreateAsteroidsTwice(){ // Checks that if we try to call create_asteroids on a board that already contains asteroids, it doesn't add otheers
        init();
        Board.get_instance().create_asteroids_random();
        int size = Board.get_instance().get_paths().size();
        Board.get_instance().create_asteroids_random();
        assertEquals(size, Board.get_instance().get_paths().size());
    }

    @Test
    public void testCreateAsteroidsEnough(){ // Checks that there is enough asteroids placed (despite the randomized aspect) in a vast majority of cases
        int not_enough = 0;
        for (int i = 0; i < 500; i++){ // We try it 500 times to have a better sample
            init();
            Board.get_instance().create_asteroids_random();
            if (Board.get_instance().get_asteroids().size() < 5){ // 5 asteroids is the minimum
                not_enough++;
            }
        }
        assertTrue(not_enough <= 5); // Such as we have at least 5 asteroids in more than 99% of the cases
    }

    @Test
    public void testCreateAsteroidsNotTooCloseBorders(){ // Checks that none of the asteroids are too close of the borders
        init();
        Board.get_instance().create_asteroids_random();
        int dim_x = Board.get_instance().get_dim_x(), dim_y = Board.get_instance().get_dim_y();
        int margin_x = Board.get_instance().get_margin_x(), margin_y = Board.get_instance().get_margin_y();
        double size_asteroid = Asteroid.get_size();
        for (Asteroid asteroid: Board.get_instance().get_asteroids()){
            assertTrue(asteroid.get_pos_x() >= margin_x + size_asteroid/2);
            assertTrue(asteroid.get_pos_x() <= dim_x - margin_x - size_asteroid/2);
            assertTrue(asteroid.get_pos_y() >= margin_y + size_asteroid/2);
            assertTrue(asteroid.get_pos_y() <= dim_y - margin_y - size_asteroid/2);
        }
    }

    @Test
    public void testCreateAsteroidsDistancePaths(){ // Checks that every asteroid is close enough to a path, but not too close from any
        init();
        int max_distance = Board.get_instance().get_max_distance();

        for (Asteroid asteroid: Board.get_instance().get_asteroids()){
            boolean cond = false;
            for (int i=0; i<Board.get_instance().get_paths().size(); i++) {
                Path_custom path = Board.get_instance().get_paths().get(i);
                for (Pair<Double, Double> pair: path.get_pos()){
                    double x1 = pair.getKey(), x2 = asteroid.get_pos_x();
                    double y1 = pair.getValue(), y2 = asteroid.get_pos_y();
                    cond = cond | Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)) < max_distance;
                }
            }
            assertTrue(cond);

            // But not too close
            cond = true;
            for (int i=0; i<Board.get_instance().get_paths().size(); i++) {
                Path_custom path = Board.get_instance().get_paths().get(i);
                for (Pair<Double, Double> pair: path.get_pos()){
                    double x1 = pair.getKey(), x2 = asteroid.get_pos_x();
                    double y1 = pair.getValue(), y2 = asteroid.get_pos_y();
                    cond = cond & Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)) > Asteroid.get_size();
                }
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
                assertTrue(Math.sqrt((Math.pow(asteroid1.get_pos_x() - asteroid2.get_pos_x(), 2) + Math.pow(asteroid1.get_pos_y() - asteroid2.get_pos_y(), 2))) > Asteroid.get_size());
            }
        }
    }

    // Note: That would have no sense to check if no asteroid lies behind a button in unit tests because wa heva to construct a GUI for that

    @Test
    public void testAddMunition(){ // Checks the adding of a munition normally
        init();
        Board.get_instance().create_asteroids_random();
        TinySound.init();
        Munition munition = new Classic_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), new Small_NPC(10, 5, 5, 10, Board.get_instance().get_paths().get(0)));
        Board.get_instance().add_munition(munition);
        Munition munition2 = new Freezing_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), new Small_NPC(10, 5, 5, 10, Board.get_instance().get_paths().get(0)));
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
        Munition munition2 = new Freezing_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), new Small_NPC(10, 5, 5, 10, Board.get_instance().get_paths().get(0)));
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

        Munition munition = new Freezing_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), new Small_NPC(10, 5, 5, 10, Board.get_instance().get_paths().get(0)));
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
        Board.get_instance().create_asteroids_random();
        NPC npc1 = new Small_NPC(5, 10, 10, 10, Board.get_instance().get_paths().get(0));
        NPC npc2 = new Big_NPC(10, 10, 15, 10, Board.get_instance().get_paths().get(0));
        Board.get_instance().add_npc(npc1);
        Board.get_instance().add_npc(npc2);

        Munition munition = new Classic_munition(new Classic_tower(Board.get_instance().get_asteroids().get(0)), npc1);
        Board.get_instance().add_munition(munition);

        Platform.startup(() -> {assertTrue(Board.get_instance().remove_npc(npc1));}); // This launches a Platform.runLater() then it needs to be launched from a JavaFX thread
        try{
            Thread.sleep(500); // We have to wait a bit for checking because the destruction of munitions is in a Platform.runLater()
        } catch (InterruptedException ignore){}

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
