package zsm.entity;

import processing.core.PApplet;
import sun.security.krb5.internal.APOptions;
import zsm.render.Renderable;

/**
 * Created by Aleksander on 14/11/2015.
 */
public class Santa extends Renderable{

    private int x,y;
    private double dx, dy;
    private PApplet main;

    public Santa(int x, int y, PApplet main){
        this.x = x;
        this.y = y;
        this.main = main;
    }

    @Override
    public void setup() {
        main.fill(129);
        main.beginShape();
        main.vertex(x + -25, y +50, 0, 1);
        main.vertex(x + 25, y +50, 1, 1);
        main.vertex(x + 25, y, 0, 0);
        main.vertex(x + -25, y, 1, 0);
        main.endShape();
        main.noFill();
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {
        x += dx;
        y += dy;
    }
}
