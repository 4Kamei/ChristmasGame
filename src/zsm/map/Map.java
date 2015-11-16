package zsm.map;

import processing.core.PApplet;
import zsm.logger.Logger;
import zsm.render.Renderable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
                vertices.add(new Vertex(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
            }catch (Exception e){Logger.log(Logger.LogLevel.ERROR, "Could not add vertex <%s, %s>", coords[0], coords[1]);}
        }
    }

    public boolean checkCollision(int x, int y) {

        Vertex left = vertices.get(0), right = vertices.get(vertices.size()-1);
        for (Vertex vertex : vertices) {
            if (vertex.x < x) {
                left = vertex;
            } else {
                right = vertex;
                break;
            }
        }

        if (left != null && right != null){
            int dy = right.y - left.y;
            int dx = right.x - left.x;
            double g = ((float) dy) / ((float) dx) * -1;
            if (dx == 0)
                g = 0;
            double actualX = (right.x - x);
            double newY = actualX * g + right.y;
            return y > newY;
        }
        //Logger.log(Logger.LogLevel.DEBUG, "Left is : %s, right is %s  New Y = %.1f", left, right, newY);

        //Y goes down when object goes up. Nice.
        return false;
    }


    public double getG(double x) {
        Vertex left = null, right = null;
        for (Vertex vertex : vertices) {
            if (vertex.x < x) {
                left = vertex;
            } else {
                right = vertex;
                break;
            }
        }

        if (left != null && right != null) {
            int dy = right.y - left.y;
            int dx = right.x - left.x;
            double g = ((float) dy) / ((float) dx) * -1;
            return g;
        }
        return 1;
    }


    @Override
    public void setup() {

    }


    @Override
    public void render() {
        main.fill(158);
        main.beginShape();
            vertices.forEach(vertex -> vertex.render(main));
            main.vertex(1200, 800);
            main.vertex(0, 800);
        main.endShape();
        main.noFill();
    }

    private class Vertex {
        private int x, y;

        public Vertex(int x, int y){
            this.x = x;
            this.y = y;
        }

        public void render(PApplet main){
            main.vertex(x, y);
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

            if (x != vertex.x) return false;
            //noinspection RedundantIfStatement
            if (y != vertex.y) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
