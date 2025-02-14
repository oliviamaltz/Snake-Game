package org.cis1200.Snake;

import java.awt.*;


public class SpeedFood extends GameObj implements Consumable {

    public static final int SIZE = 20;

    private static int INIT_POS_X = (int) Math.round(Math.random() * 29);
    private static int INIT_POS_Y =  (int) Math.round(Math.random() * 20);

    private Tile position;

    private int score;


    public SpeedFood(int court, int INIT_POS_X, int INIT_POS_Y, int score) {
        super(0, 0, INIT_POS_X, INIT_POS_Y ,
                SIZE, SIZE, court, court);
        this.INIT_POS_X = INIT_POS_X;
        this.INIT_POS_Y = INIT_POS_Y;
        this.score = score;
        position = new Tile(INIT_POS_X, INIT_POS_Y, court, Color.BLUE);
    }

    @Override
    public void onConsume() {
        this.position.setPx((int) Math.round(Math.random() * 29));
        this.position.setPy((int) Math.round(Math.random() * 20));
        score += 1;
    }

    public Tile getPosition() {
        return position;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void draw(Graphics g) {
        position.draw(g);
    }
}
