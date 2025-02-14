package org.cis1200.Snake;

public interface Consumable {
    public void onConsume(); // food regenerates to a different position and increases the score
    public Tile getPosition(); // returns the xy position of the tile
    public int getScore(); // return the score of the food

}
