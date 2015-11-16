package zsm.entity;

import processing.core.PApplet;
import zsm.logger.Logger;
import zsm.main.Reference;
import zsm.map.Map;
import zsm.render.Renderable;

import java.util.ArrayList;

/**
 * Created by 10akaminski on 11/11/2015 .
 */
public class SnowmanEntity extends Renderable {

    private int x, y;
    private double dx, dy;

    private double health;
    private ArrayList<BodyPart> parts;
    private int stackSize;
    private PApplet main;
    private int maxSize;
    private int addSize = 5;
    private int smallest = 30;
    private int width;
    private int height;
    private Map map;
    private boolean inAir;

    public SnowmanEntity(int x, int y, PApplet main, Map map) {
        this.renderPriority = Reference.snowmanPriority;
        parts = new ArrayList<>();
        this.map = map;
        this.stackSize = parts.size();
        this.health = 1;
        this.y = y;
        this.x = x;
        this.main = main;
        maxSize = 10;
        this.inAir = false;
    }

    public BodyPart getBottom(){
        return parts.get(stackSize);
    }

    public void add(BodyPart part){
        maxSize += addSize;
        parts.add(0, part);

        updateSize();
        Logger.log(Logger.LogLevel.ALL, "Snowman stack size = %d", parts.size());
        updatePositions();
    }
    public void addTop(BodyPart part){
        maxSize += addSize;
        parts.add(part);

        updateSize();
        updatePositions();
    }

    private void updateSize(){

        int index = parts.size();
        for (BodyPart bodyPart : parts) {
            bodyPart.updateSize(maxSize * Math.log(maxSize) * --index/(2 * parts.size()) + smallest);
        }

    }
    public void pop(){
        maxSize -= addSize;
        parts.remove(0);
        stackSize--;
        updatePositions();
    }

    public double getMaxSpeed(){
        return Math.sqrt(parts.size());
    }

    private void updatePositions(){
        int loopy = 0;
        for (BodyPart part : parts) {
            loopy -= part.getSize()*4/5;
            part.updatePosition((int) ((maxSize - part.getSize() / 2) -maxSize), loopy);
        }
    }


    @Override
    public void setup() {

    }

    @Override
    public void render() {

        if (!map.checkCollision(x, y+2))
            inAir = true;
        else {
            if (!inAir)
                dy = 0;
            inAir = false;
        }

        if (inAir)
            dy += 0.16;

        if (!inAir)
            if (map.checkCollision((int) (x + dx), y))
                dy = -map.getG(x+dx)*dx;

        x += dx;
        y += dy;

        for (BodyPart part : parts) {
            part.render(main, x , y);
        }

    }


    public void setVel(double x, double y) {
        this.dx = x;
        this.dy = y;
    }

    public void setX(double x) {
        this.dx = x;
    }
}
