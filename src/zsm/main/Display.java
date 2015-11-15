package zsm.main;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import sun.rmi.runtime.Log;
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

        snowman1 = new SnowmanEntity(20, 20, this, map);
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

    public void keyPressed(KeyEvent event){
        Logger.log(DEBUG, "Keyboard key clicked ID = %s", event.getKeyCode());
        switch (event.getKeyCode()) {
            case 65:
                SnowmanEntity s = new SnowmanEntity(mouseX, mouseY, this, map);
            try{
                s.add(new BodyPart(3, 100));
                while (Math.random() < 0.6)
                    s.add(new BodyPart(2, 100));
            }catch(Exception ex){
                Logger.log(ERROR, ex.getMessage());
            }
                s.setVel(3*Math.random() + 1, Math.random() - 2);
                queue.addAndSort(s);
        }

    }

    public void mousePressed(MouseEvent event){
        Logger.log(DEBUG, "Mouse clicked at %d, %d, with button %d",mouseX, mouseY, event.getAction());
    }
}
