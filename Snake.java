package org.cis1200.Snake;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * A basic game object starting in the upper left corner of the game court. It
 * is displayed as a square of a specified color.
 */
public class Snake extends GameObj implements Iterable<Tile> {
    public static  int SIZE = 30;
    public static  int INIT_POS_X = 0;
    public static  int INIT_POS_Y = 0;
    public static  int INIT_VEL_X = 0;
    public static  int INIT_VEL_Y = SIZE;
    private Direction currentDirection;
    public Direction stateOfDirection;
    private Deque<Tile> Snake;
    private int court;
    private boolean isMoving = true;



    /**
     * Note that, because we don't need to do anything special when constructing
     * a Square, we simply use the superclass constructor called with the
     * correct parameters.
     */
    public Snake(int court, int[] coordinates, Direction going) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, court, court);
        Deque<Tile> Snake = new ArrayDeque<>();
        for (int i = 0; i < coordinates.length - 1; i += 2) {
            Tile tile = new Tile(coordinates[i], coordinates[i + 1], court, Color.red);
            Snake.add(tile);
        }

        this.currentDirection = going;
        this.stateOfDirection = going;
        this.Snake = Snake;
        this.court = court;

    }
    public Tile getHead(){
        return Snake.getFirst();
    }
    public int getSize(){
        return Snake.size();
    }

    @Override
    public void draw(Graphics g) {
        for (Tile tile : Snake) {
            tile.draw(g);
        }
    }

    public void changeMove() {
        isMoving = false;
    }

    public boolean getMove() {
        return isMoving;
    }

    public boolean hasDirection() {
        if (stateOfDirection == null) {
            return false;
        }
        return true;
    }

    public Direction getDirection() {
        return stateOfDirection;
    }

    public void growByOne() {
        if (Snake.size() < 20) {
            int lastX = Snake.getLast().getPx();
            int lastY = Snake.getLast().getPy();
            if (stateOfDirection.equals(Direction.UP)) {
                Tile add = new Tile(lastX,lastY + 1 , court, Color.red);
                Snake.add(add);
            }
            if (stateOfDirection.equals(Direction.DOWN)) {
                Tile add = new Tile(lastX,lastY - 1 , court, Color.red);
                Snake.add(add);
            }
            if (stateOfDirection.equals(Direction.LEFT)) {
                Tile add = new Tile(lastX + 1,lastY , court, Color.red);
                Snake.add(add);
            }
            if (stateOfDirection.equals(Direction.RIGHT)) {
                Tile add = new Tile(lastX - 1,lastY , court, Color.red);
                Snake.add(add);
            }
        }
    }

    public void directionLeft() {
        if (!stateOfDirection.equals(Direction.RIGHT)) {
            this.currentDirection = Direction.LEFT;
            this.stateOfDirection = Direction.LEFT;
            setVx(SIZE);
            setVy(0);
        }

    }

    public void directionRight() {
        if (!stateOfDirection.equals(Direction.LEFT)) {
            this.currentDirection = Direction.RIGHT;
            this.stateOfDirection = Direction.RIGHT;
            setVx(SIZE);
            setVy(0);
        }
    }
    public void directionUp() {
        if (!stateOfDirection.equals(Direction.DOWN)) {
            this.currentDirection = Direction.UP;
            this.stateOfDirection = Direction.UP;
            setVx(0);
            setVy(SIZE);
        }
    }
    public void directionDown() {
        if (!stateOfDirection.equals(Direction.UP)) {
            this.currentDirection = Direction.DOWN;
            this.stateOfDirection = Direction.DOWN;
            setVx(0);
            setVy(SIZE);
        }
    }

    public void movement() {
        if (currentDirection.equals(Direction.UP)) {
            if (Snake.getFirst().getPy() > 0) {
                Tile newHead = new Tile(Snake.getFirst().getPx(), Snake.getFirst().getPy() - 1,
                        this.court, Color.red);
                Snake.addFirst(newHead);
                Snake.removeLast();

            } else {
                changeMove();
            }
        }

        if (currentDirection.equals(Direction.LEFT)) {
            if (Snake.getFirst().getPx() > 0) {
                Tile newHead = new Tile(Snake.getFirst().getPx()  - 1, Snake.getFirst().getPy(),
                        this.court, Color.red);
                Snake.addFirst(newHead);
                Snake.removeLast();
            } else {
                changeMove();
            }
        }

        if (currentDirection.equals(Direction.RIGHT)) {
            if (Snake.getFirst().getPx() < 29) {
                Tile newHead = new Tile(Snake.getFirst().getPx() + 1, Snake.getFirst().getPy() ,
                        this.court, Color.red);
                Snake.addFirst(newHead);
                Snake.removeLast();
            } else {
                changeMove();
            }
        }
        if (currentDirection.equals(Direction.DOWN)) {
            if ( Snake.getFirst().getPy() < 20) {
                Tile newHead = new Tile(Snake.getFirst().getPx(), Snake.getFirst().getPy() + 1,
                        this.court, Color.red);
                Snake.addFirst(newHead);
                Snake.removeLast();
            } else {
                changeMove();
            }
        }
    }

    @Override
    public void move() {
        if (isMoving) {
            this.movement();

            int x = Snake.getFirst().getPx();
            int y = Snake.getFirst().getPy();
            int xVel = Snake.getFirst().getVx();
            int yVel = Snake.getFirst().getVy();

            Snake.getFirst().setPx(x + xVel);
            Snake.getFirst().setPy(y + yVel);
        }
    }

    @Override
    public Iterator<Tile> iterator() {
        return Snake.iterator();
    }

}