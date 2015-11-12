package zsm.entity;

import processing.core.PApplet;
import sun.rmi.runtime.Log;
import zsm.logger.Logger;
import zsm.render.Renderable;

import java.util.ArrayList;

/**
 * Created by 10akaminski on 11/11/2015 .
 */
public class SnowmanEntity extends Renderable {

    private int x, y;
    private double health;
    private ArrayList<BodyPart> parts;
    private int stackSize;
    private PApplet main;
    private int maxSize;
    public SnowmanEntity(int y, int x, PApplet main) {
        parts = new ArrayList<>();
        this.stackSize = parts.size();
        this.health = 1;
        this.y = y;
        this.x = x;
        this.main = main;
        maxSize = 0;
    }

    public BodyPart getBottom(){
        return parts.get(stackSize);
    }

    public void add(BodyPart part){
        maxSize += 10;
        parts.add(0, part);
        int index = 1;
        for (BodyPart bodyPart : parts) {
            bodyPart.updateSize(maxSize/Math.log(++index));
        }
        Logger.log(Logger.LogLevel.ALL, "Snowman stack size = %d", parts.size());
        updatePositions();
    }
    public void addTop(BodyPart part){
        parts.add(part);
        updatePositions();
    }

    public void pop(){
        maxSize -= 10;
        parts.remove(1);
        stackSize--;
        updatePositions();
    }

    private void updatePositions(){
        int loopy = 0;
        for (BodyPart part : parts) {
            loopy -= part.getSize();
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
        for (BodyPart part : parts) {
            part.render(main, x , y);
        }
    }

    @Override
    public void update() {

    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
