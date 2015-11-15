package zsm.map;

import processing.core.PApplet;
import zsm.logger.Logger;
import zsm.render.Renderable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

/**
 * Created by Aleksander on 14/11/2015.
 */
public class Map extends Renderable {

    private ArrayList<Vertex> vertices;

    private PApplet main;

    public Map(File file, PApplet main) throws FileNotFoundException {
        this.renderPriority = 100;
        this.main = main;
        vertices = new ArrayList<>();
        Scanner s = new Scanner(file);
        String line;
        while (s.hasNextLine()){
            line = s.nextLine();
            line = line.replace("<", "").replace(">","");
            String[] coords = line.split(",");
            try {
                vertices.add(new Vertex(Float.parseFloat(coords[0]), Float.parseFloat(coords[1])));
            }catch (Exception e){Logger.log(Logger.LogLevel.ERROR, "Could not add vertex <%s, %s>", coords[0], coords[1]);}
        }
    }

    public boolean checkCollision(int x1, int y1) {

        float x = (float) x1/main.width;
        float y = (float) y1/main.height;

        Vertex left = null, right = null;
        for (Vertex vertex : vertices) {
            if (vertex.x < x) {
                left = vertex;
            } else {
                right = vertex;
                break;
            }
        }
        if (left != null && right != null){
            float dy = right.y - left.y;
            float dx = right.x - left.x;
            double g = (dy) / (dx) * -1;
            double actualX = (right.x - x);
            double newY = actualX * g + right.y;
            return y > newY;
        }
        //Logger.log(Logger.LogLevel.DEBUG, "Left is : %s, right is %s  New Y = %.1f", left, right, newY);

        //Y goes down when object goes up. Nice.
        return false;
    }
    @Override
    public void setup() {

    }

    @Override
    public void render() {
        main.fill(158);
        main.beginShape();
            vertices.forEach(vertex -> vertex.render(main));
            main.vertex(main.width, main.height);
            main.vertex(0, main.height);
        main.endShape();
        main.noFill();
    }

    @Override
    public void update() {

    }

    private class Vertex {
        private float x, y;

        public Vertex(float x, float y){
            this.x = x;
            this.y = y;
        }

        public void render(PApplet main){
            main.vertex(x*main.width, y*main.height);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Vertex{");
            sb.append("x=").append(x);
            sb.append(", y=").append(y);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            if (Float.compare(vertex.x, x) != 0) return false;
            if (Float.compare(vertex.y, y) != 0) return false;

            return true;
        }

    }
}
