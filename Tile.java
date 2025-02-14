package org.cis1200.Snake;

import java.awt.*;

/**
 * A game object displayed using an image.
 *
 * Note that the image is read from the file when the object is constructed, and
 * that all objects created by this constructor share the same image data (i.e.
 * img is static). This is important for efficiency: your program will go very
 * slowly if you try to create a new BufferedImage every time the draw method is
 * invoked.
 */
public class Tile extends GameObj {

    public static final int SIZE = 20;
    private static int INIT_POS_X;
    private static int INIT_POS_Y;
    private static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private final Color color;



    public Tile(int INIT_POS_X, int INIT_POS_Y, int court, Color color) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, court, court);

        this.color = color;
        Tile.INIT_POS_X = INIT_POS_X;
        Tile.INIT_POS_Y = INIT_POS_Y;
    }


    public boolean equals(Tile other) {
        if (this.getPx() == other.getPx() && this.getPy() == other.getPy()) {
            return true;
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx() * SIZE, this.getPy() * SIZE, this.getWidth(), this.getHeight());

        g.setColor(Color.BLACK);
        g.drawRect(this.getPx() * SIZE, this.getPy() * SIZE, this.getWidth(), this.getHeight());



    }
}
