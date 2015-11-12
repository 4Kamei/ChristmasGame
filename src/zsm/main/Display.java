package zsm.main;

import processing.core.PApplet;
import processing.event.MouseEvent;
import zsm.entity.BodyPart;
import zsm.entity.SnowmanEntity;
import zsm.render.RenderQueue;

import java.util.ArrayList;

/**
 * Created by 10akaminski on 11/11/2015 .
 */
public class Display extends PApplet {

    private RenderQueue queue;
    private SnowmanEntity snowman1;

    static public void main(String[] passedArgs) {
        PApplet.main(new String[]{"zsm.main.Display"});
    }

    public void settings(){
        size(800, 600, P2D);
    }

    public void setup(){
        background(0);
        noSmooth();
        queue = new RenderQueue();
        snowman1 = new SnowmanEntity(20, 20, this);
        try {
            snowman1.add(new BodyPart(1, 100));
            snowman1.add(new BodyPart(1, 100));
            snowman1.add(new BodyPart(1, 100));
            snowman1.add(new BodyPart(1, 100));
        } catch (Exception e){ e.printStackTrace(); }

        queue.add(snowman1);
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
}
