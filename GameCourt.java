package org.cis1200.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
public class GameCourt extends JPanel {

    // the state of the game logic
    private TileGrid grid; //the 10x10 grid of yellow tiles, doesnt move
    private static Snake snake; //the Array Deque of red tiles, keyboard control
    private GrowFood growFood;
    private SpeedFood speedFood;

    private boolean playing = false; // whether the game is running
    private final JLabel status; // Current status text, i.e. "Running..."
    private final JLabel growScore; // displays the snakes growFood score
    private final JLabel speedScore; // displays the speedFood score

    // Game constants
    public static final int COURT_WIDTH = 610;
    public static final int COURT_HEIGHT = 420;
    public static final int SQUARE_VELOCITY = 0;

    // Update interval for timer, in milliseconds
    private static int INTERVAL = 100;

    public GameCourt(JLabel status, JLabel growScore, JLabel speedScore) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single time step.
        Timer timer = new Timer(INTERVAL, e -> tick());
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    snake.directionLeft();
                    //snake.setVx(-SQUARE_VELOCITY);
                    snake.movement();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    snake.directionRight();
                    //snake.setVx(SQUARE_VELOCITY);
                    snake.movement();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    snake.directionDown();
                    //snake.setVy(SQUARE_VELOCITY);
                    snake.movement();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    snake.directionUp();
                    //snake.setVy(-SQUARE_VELOCITY);
                    snake.movement();
                }
            }

            public void keyReleased(KeyEvent e) {
//                snake.setVx(0);
//                snake.setVy(0);
            }
        });

        this.status = status;
        this.speedScore = speedScore;
        this.growScore = growScore;

    }

    /**
     * A FileInput function that records all the information about
     * a saved game and writes it into a file.
     **/

    public void writeIntsToFile(
            java.util.List<Integer> intsToWrite, String filePath) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(file, false));
            for (int i : intsToWrite) {
                System.out.println(i);
                bw.write(i + "\n");
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            System.out.print("Stop writing due to an IOException");
        }
    }

    /**
     * A FileOutput function that reads all the information about a previous game from a file.
     **/
    public static List<Integer> readFileToIntList(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException();
        }
        List<Integer> info = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String current;
            while ((current = br.readLine()) != null) {
                int currNum = Integer.parseInt(current);
                info.add(currNum);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        return info;
    }
    /**
     * Re-implement a previous game that has been saved
     **/
    public void previous() {
        List<Integer> prev = readFileToIntList("./files/PreviousGame.txt");
        if (prev.isEmpty()) {
            return;
        }
        int[] oldSnake = new int[prev.size() - 7];
        for (int i = 0; i < (prev.size() - 7); i++) {
            oldSnake[i] = prev.get(i + 6);
        }
        Direction dir = Direction.UP;
        int dirInt = prev.get(prev.size() - 1);
        if (dirInt == 4) {
            dir = Direction.DOWN;
        }
        if (dirInt == 3) {
            dir = Direction.UP;
        }
        if (dirInt == 2) {
            dir = Direction.LEFT;
        }
        if (dirInt == 1) {
            dir = Direction.RIGHT;
        }
        snake = new Snake(COURT_WIDTH, oldSnake, dir);
        grid = new TileGrid(COURT_WIDTH);
        speedFood = new SpeedFood(COURT_WIDTH, prev.get(1),
                prev.get(2), prev.get(0));
        System.out.println("Speed prev: " + prev.get(0));
        growFood = new GrowFood(COURT_WIDTH, prev.get(4),
                prev.get(5), prev.get(3));
        System.out.println("Len prev: " + prev.get(3));

        playing = true;
        status.setText("Running...");
        speedScore.setText("Speed: " + speedFood.getScore());
        growScore.setText("Length: " + growFood.getScore());

        System.out.println("Speed: " + speedFood.getScore());
        System.out.println("Len: " + growFood.getScore());

        requestFocusInWindow();
    }
    /**
     * Saves the current game and begins a new game
     **/
    public void save() {
        List<Integer> info = new ArrayList<>();
        info.add(speedFood.getScore());
        info.add(speedFood.getPx());
        info.add(speedFood.getPy());
        info.add(growFood.getScore());
        info.add(growFood.getPx());
        info.add(growFood.getPy());
        for (Tile tile : snake) {
            info.add(tile.getPx());
            info.add(tile.getPy());
        }
        if (snake.hasDirection()) {
            if (snake.getDirection().equals(Direction.RIGHT)) {
                info.add(1);
            }
            if (snake.getDirection().equals(Direction.LEFT)) {
                info.add(2);
            }
            if (snake.getDirection().equals(Direction.UP)) {
                info.add(3);
            }
            if (snake.getDirection().equals(Direction.DOWN)) {
                info.add(4);
            }
        }
        writeIntsToFile(info, "./files/PreviousGame.txt");

        reset();
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        grid = new TileGrid(COURT_WIDTH);
        int[] resetSnake = {4, 11, 3, 11, 2, 11};
        snake = new Snake(COURT_WIDTH, resetSnake, Direction.RIGHT);
        growFood = new GrowFood(COURT_WIDTH, (int) Math.round(Math.random() * 29),
                (int) Math.round(Math.random() * 20), 0);
        speedFood = new SpeedFood(COURT_WIDTH, (int) Math.round(Math.random() * 29),
                (int) Math.round(Math.random() * 20), 0);


        playing = true;
        status.setText("Running...");
        speedScore.setText("Speed: " + speedFood.getScore());
        growScore.setText("Length: " + growFood.getScore());


        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    /**
     * This function returns a boolean regarding whether the
     * growFood has made contact with the Snake
     */
    public boolean checkGrowPosition(GrowFood food) {
        for (Tile tile : this.snake) {
            if (tile.equals(food.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSpeedPosition(SpeedFood food) {
        for (Tile tile : this.snake) {
            if (tile.equals(food.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public void increaseTime() {
        if (INTERVAL > 35) {
            INTERVAL = INTERVAL - 10;
        }
    }

    public static Snake getSnake() {
        return snake;
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            //advance the snake in its default direction
            snake.move();
            //
            if (checkGrowPosition(growFood)) {
                growFood.onConsume();
                snake.growByOne();
                growScore.setText("Length: " + growFood.getScore());
            }

            if (checkSpeedPosition(speedFood)) {
                speedFood.onConsume();
                increaseTime();
                speedScore.setText("Speed: " + speedFood.getScore());
            }


            // check for the game end conditions
            if (!snake.getMove()) {
                playing = false;
                status.setText("You lose!");

            }

            // update the display
            repaint();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        grid.draw(g);
        snake.draw(g);
        growFood.draw(g);
        speedFood.draw(g);

//        snitch.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}