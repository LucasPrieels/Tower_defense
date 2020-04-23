package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JButton start_game;
    private JLabel title;

    public Menu(){
        frame = new JFrame("Menu");
        frame.setTitle("Nom du jeu");
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Background());


        panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.setOpaque(false);

        start_game = new JButton("Start Game");
        //start_game.setForeground(Color.RED);
        //start_game.setBackground(Color.BLACK);
        //start_game.getColorModel();
        panel.add(start_game);

        start_game.addActionListener(this);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event){
        Object source = event.getSource();

        if(source == start_game){
            Window window = new Window();
        }
    }
}