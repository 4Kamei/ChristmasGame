package zsm.entity;

import processing.core.PApplet;
import sun.rmi.runtime.Log;
import zsm.logger.Logger;
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

    public SnowmanEntity(int x, int y, PApplet main, Map map) {
        this.renderPriority = 50;
        parts = new ArrayList<>();
        this.map = map;
        this.stackSize = parts.size();
        this.health = 1;
        this.y = y;
        this.x = x;
        this.main = main;
        maxSize = 10;
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
            bodyPart.updateSize(maxSize * --index/parts.size() + smallest);
        }

    }
    public void pop(){
        maxSize -= addSize;
        parts.remove(0);
        stackSize--;
        updatePositions();
    }

    private void updatePositions(){
        int loopy = 0;
        for (BodyPart part : parts) {
            loopy -= part.getSize()*4/5;
            part.updatePosition((int) ((maxSize - part.getSize() / 2) -maxSize), loopy);
        }
    }

    @Override
    @Deprecated
    public void setup() {
        Logger.log(Logger.LogLevel.ALL, this + " cannot setup - deprecated");
    }

    @Override
    public void render() {
        x += dx;
        y += dy;

        if (map.checkCollision(x, y+1))
            dy = 0;
        else
            dy += 0.16;

        if (map.checkCollision((int) (x + dx), y))
            dy -= 0.2;

        for (BodyPart part : parts) {
            part.render(main, x , y);
        }
    }

    @Override
    public void update() {

    }

    public void setVel(double x, double y) {
        this.dx = x;
        this.dy = y;
    }

    public void setX(double x) {
        this.dx = x;
    }
}
