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
public class TileGrid {
    private Tile[][] grid;

    public TileGrid(int court) {
        super();
        grid = new Tile[30][21];

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 21; j++) {
                Tile a  = new Tile(i, j, court, Color.YELLOW);
                grid[i][j] = a;
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 21; j++) {
                grid[i][j].draw(g);
            }
        }
    }
}


