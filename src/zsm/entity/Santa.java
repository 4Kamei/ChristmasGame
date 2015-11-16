package zsm.entity;

import processing.core.PApplet;
import processing.core.PImage;
import zsm.logger.Logger;
import zsm.main.Reference;
import zsm.map.Map;
import zsm.render.Renderable;

/**
 * Created by Aleksander on 14/11/2015.
 */
public class Santa extends Renderable{

    private int x,y;
    private double dx, dy;
    private PApplet main;
    private Map map;
    private boolean inAir = false;
    private boolean goingLeft = false, goingRight = false;
    private PImage texture;

    public Santa(int x, int y, PApplet main, Map map){
        this.renderPriority = Reference.playerPriority;
        this.map = map;
        this.x = x;
        this.y = y;
        this.main = main;
        this.dx = 0;
        this.dy = 0;
    }

    @Override
    public void setup() {
        texture = main.loadImage("res/texture/santa/body.png");
    }

    @Override
    public void render() {
        if (goingRight)
            right();
        if (goingLeft)
            left();
        if (!goingLeft && !goingRight && dx != 0){
            dx *= 0.6;
            if ((dx < 0.01 && dx > 0) || (dx > -0.01 & dx < 0)) {
                dx = 0;
            }
        }

        inAir = !map.checkCollision(x, y);
        if (inAir)
            dy += 0.16;
        else
            dy = 0;
        if (dx != 0){
            if (map.checkCollision((int) (x + dx), y)){
                dy = -map.getG(x + dx)*dx;
            }
        }

        x += dx;
        y += dy;

        int flip;
        if (dx > 0)
            flip = 0;
        else
            flip = 1;
        main.fill(129);
        main.beginShape();
            main.texture(texture);
            main.vertex(x + -15, y -50, flip, 0);
            main.vertex(x + 15, y -50,  1 - flip, 0);
            main.vertex(x + 15, y,  1 - flip,  1);
            main.vertex(x + -15, y, flip, 1);
        main.endShape();
        main.noFill();
    }


    private void right() {
        if (dx < Reference.santaMaxSpeed)
            dx += 0.1;
        else
            dx = Reference.santaMaxSpeed;
    }

    private void left(){
        if (dx > -Reference.santaMaxSpeed)
            dx -= 0.1;
        else
            dx = -Reference.santaMaxSpeed;
    }

    public void jump(){
        if (!inAir)
            dy = -3;
        y += dy;
        Logger.log(Logger.LogLevel.ALL, "Santa jumping at %d, %.2f with with power %.1f and y %d",x,dx,dy,y);
    }

    public void startRight() {
        Logger.log(Logger.LogLevel.ALL, "Start Right");
        goingRight = true;
        goingLeft = false;
    }

    public void startLeft() {
        Logger.log(Logger.LogLevel.ALL, "Start Left");
        goingRight = false;
        goingLeft = true;
    }

    public void stopRight() {
        Logger.log(Logger.LogLevel.ALL, "Stop Right");
        goingRight = false;
    }

    public void stopLeft() {
        Logger.log(Logger.LogLevel.ALL, "Stop Left");
        goingLeft = false;
    }
}
