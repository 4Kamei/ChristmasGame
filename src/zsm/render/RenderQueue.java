package zsm.render;

import zsm.logger.Logger;

import java.util.ArrayList;

import static zsm.logger.Logger.LogLevel.*;

/**
 * Created by 10akaminski on 11/11/2015 .
 */
public class RenderQueue {
    private ArrayList<Renderable> queue;
    private int index;

    public void add(Renderable renderable){
        queue.add(renderable);
    }

    public void addAndSort(Renderable renderable){
        Logger.log(DEBUG, renderable + " has been added to the RenderQueue");
        queue.add(renderable);
        //Sort in decending order (large numbers rendered first)
        queue.sort(
                (r2, r1) -> Integer.compare(r1.getRenderPriority(), r2.getRenderPriority()));
        Logger.log(DEBUG, "renderQueue now contains " + queue.size() + " items");
    }

    public void remove(Renderable r){
        queue.remove(queue.indexOf(r));
        queue.sort((r1, r2) -> -1);
    }

    public RenderQueue(){
        queue = new ArrayList<>();
        index = 0;
    }

    public Renderable next(){
        Renderable r = queue.get(index++);
        //Logger.log(Logger.LogLevel.DEBUG, r.getRenderPriority() + " : " + r);
        return r;
    }

    public boolean hasNext(){
        return index < queue.size();
    }

    public void reset(){
        index = 0;
    }
}