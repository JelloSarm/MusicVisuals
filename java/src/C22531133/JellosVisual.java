package C22531133;

import ie.tudublin.*;
import example.MyVisual;

public class JellosVisual extends Visual{
    MyVisual js;

    public JellosVisual(MyVisual js)
    {
        this.js = js;
    }
    int scale = 10;
    int rows;
    int cols;
    float[][] land;

    public void draw() {
        rows = (int) (js.height / scale);
        cols = (int) (js.width / scale);

        land = new float[cols][rows];
        js.background(0);
        js.stroke(255);
        js.noFill();
        for(int y = 0; y < rows-1;y++)
        {
            js.beginShape(TRIANGLE_STRIP);
            for(int x = 0; x < cols; x++)
            {
                js.vertex(x * scale, y * scale, land[x][y]);
                js.vertex(x * scale, (y+1)*scale, land[x][y+1]);
            }
            js.endShape();
        }
    }
}
