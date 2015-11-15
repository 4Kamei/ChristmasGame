package zsm.main;

import processing.core.PApplet;
import processing.event.MouseEvent;
import zsm.entity.BodyPart;
import zsm.entity.Santa;
import zsm.entity.SnowmanEntity;
import zsm.logger.Logger;
import zsm.map.Map;
import zsm.render.RenderQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static zsm.logger.Logger.LogLevel.*;

/**
 * Created by 10akaminski on 11/11/2015 .
 */
public class Display extends PApplet {

    static public void main(String[] passedArgs) {
        PApplet.main(new String[]{"zsm.main.Display"});
    }

    public void settings(){
        size(800, 600, P2D);
        noSmooth();
    }

    private SnowmanEntity snowman1;
    private RenderQueue queue;
    private Map map;
    private Santa player;

    public void setup() {

        background(0);
        noStroke();
        queue = new RenderQueue();
        try {
            map = new Map(new File("res/map/map.txt"), this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        snowman1 = new SnowmanEntity(20, 20, this);
        player = new Santa(0, 0, this);

        try {
            snowman1.add(new BodyPart(1, 100));
        } catch (Exception e){ e.printStackTrace(); }

        queue.add(snowman1);
        queue.add(map);

        queue.sort();
    }

    public void draw(){
        background(0);
        while (queue.hasNext())
            queue.next().render();
        queue.reset();
    }

    @Override
    public void mouseMoved(MouseEvent event){
        snowman1.setPos(event.getX(), event.getY());
    }

    public void mousePressed(MouseEvent event){
        Logger.log(DEBUG, "Mouse clicked at %d, %d, with button %d",mouseX, mouseY, event.getAction());
        if (event.getButton() == 37){
            try {
                //Values of random between 1.5 and 3.5, flooring gives 1,2 and 3 as possible values.
                snowman1.add(new BodyPart((int) ((Math.random()*2) + 1.5), 100));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            snowman1.pop();
        }
    }
}
