package zsm.render;

/**
 * Created by 10akaminski on 11/11/2015 .
 */
public abstract class Renderable {
    public abstract void setup();

    public abstract void render();

    public abstract void update();

    protected int renderPriority;

    public int getRenderPriority(){
        return renderPriority;
    }
}
