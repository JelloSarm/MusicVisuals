package C22531133;

import ie.tudublin.*;
import example.MyVisual;

public class JellosVisual extends Visual{
    MyVisual js;

    public JellosVisual(MyVisual js)
    {
        this.js = js;
    }

    int rows = (int) (200+height / 20);
    int cols = (int) (width / 20);
    float[][] land;

    public void draw() {
        rows = (int) (200+height / 20);
        cols = (int) (width / 20);

        land = new float[cols][rows];
        js.background(0);
        js.stroke(255);
        js.noFill();

        for(int y = 0; y < rows-1;y++)
        {
            js.beginShape(TRIANGLE_STRIP);
            for(int x = 0; x < cols; x++)
            {
                js.vertex(x * 20, y * 20, land[x][y]);
                js.vertex(x * 20, (y+1)*20, land[x][y+1]);
            }
            js.endShape();
        }
    }
}
