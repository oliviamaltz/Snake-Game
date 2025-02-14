package org.cis1200.Snake;

// imports necessary libraries for Java swing

import javax.swing.*;
import java.awt.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunSnake implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Snake");
        frame.setLocation(400, 400);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Score panel
        final JPanel score_panel = new JPanel();
        score_panel.setLayout(new GridLayout(2, 0));
        //score_panel.setLayout(new BoxLayout());
        frame.add(score_panel, BorderLayout.EAST);
        final JLabel speedScore = new JLabel("Speed: ");
        score_panel.add(speedScore);
        final JLabel growScore = new JLabel("Length: ");
        score_panel.add(growScore);


        // Main playing area
        final GameCourt court = new GameCourt(status, growScore, speedScore);
        frame.add(court, BorderLayout.CENTER);

        // The control panel
        final JPanel control_panel = new JPanel();
        control_panel.setLayout(new GridLayout(0, 3));
        frame.add(control_panel, BorderLayout.NORTH);
        // the reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> court.reset());
        control_panel.add(reset);
        //the save button
        final JButton save = new JButton("Save");
        save.addActionListener(e -> court.save());
        control_panel.add(save);
        // the previous button
        final JButton prev = new JButton("Previous");
        prev.addActionListener(e -> court.previous());
        control_panel.add(prev);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();

        //Instructions Tab
        final JFrame instructions = new JFrame("Instructions");
        instructions.setLocation(400, 400);
        final JTextArea writeup = new JTextArea();
        instructions.add(writeup);
        writeup.setText(" Snake Instructions: \n");
        writeup.append(" move the snake with the arrow keys \n");
        writeup.append(" touching a BLUE square will speed you up! \n");
        writeup.append(" touching a GREEN square will make you grow! \n");
        writeup.append(" but DO NOT run into the sides or you'll lose! \n");
        writeup.append(" press the RESET button to begin a new game \n");
        writeup.append(" press the SAVE button to save your current game and begin a new one \n");
        writeup.append(" press the PREVIOUS button to return to your saved game\n");



        instructions.pack();
        instructions.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        instructions.setVisible(true);
    }
}