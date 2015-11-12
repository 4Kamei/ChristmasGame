package zsm.entity;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import zsm.logger.Logger;
import zsm.render.Renderable;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by 10akaminski on 11/11/2015 .
 */
public class BodyPart {

    public static final int NORMAL = 1, ICE = 2;

    private double size;
    private int type;
    private PImage texture;
    private double health;
    private final int maxHealth;
    private int relX, relY;

    public BodyPart(int type, int maxHealth) throws IOException {
        this.type = type;
        this.maxHealth = maxHealth;
        Logger.log(Logger.LogLevel.ALL, "Pulling image %s.png", type);
        this.texture = new PImage(ImageIO.read(new File("res/texture/bodypart/" + type + ".png")));
        this.health = maxHealth;
    }

    public double damage(int val){
        this.health -= val;
        return health;
    }

    public double getPrecentage(){
        return health/maxHealth;
    }

    public double getSize(){
        return size;
    }

    public void updateSize(double size){
        this.size = size;
    }

    public void render(PApplet main, int x, int y){
        x += relX;
        y += relY;
        Logger.log(Logger.LogLevel.DEBUG, "Rendering %s with size %.1f at %d, %d", this, size,x , y);
        main.textureMode(PConstants.NORMAL);
        main.beginShape();
            main.texture(texture);
            main.vertex((float) x,        (float) size + y, 0, 1);
            main.vertex((float) size + x, (float) size + y, 1, 1);
            main.vertex((float) size + x, (float) y, 1, 0);
            main.vertex((float) x,        (float) y, 0, 0);
        main.endShape();
    }

    public void updatePosition(int x, int y) {
        this.relX = x;
        this.relY = y;
    }
}
