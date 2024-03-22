package C22531133;

import ie.tudublin.*;
import example.MyVisual;

public class JellosVisual extends Visual{
    MyVisual js;

    public JellosVisual(MyVisual js)
    {
        this.js = js;
    }
    int scale = 20;
    int rows;
    int cols;
    float[][] land;

    public void draw() {
        int w = js.width;
        int h = js.height;
        rows = (int) (200 + h / scale);
        cols = (int) (w / scale);

        land = new float[cols][rows];
        js.background(0);
        js.stroke(255);
        js.noFill();

        js.translate(w / 2 , h / 2);
        js.rotateX(PI/3);
        js.translate(-w / 2, -h);
        for(int y = 0; y < rows-1;y++)
        {
            js.beginShape(TRIANGLE_STRIP);
            for(int x = 0; x < cols; x++)
            {
                if(x == 0)
                {
                    
                    js.vertex((x+1) * 20, (y) * 20, land[x][y] + random(80, 150));
                    js.vertex((x + 1) * 20, (y+1) * 20, land[x][y+1] + random(80, 150));
                }
                else if(x == cols - 1)
                {
                    
                    js.vertex((x+1) * 20, (y) * 20, land[x][y] + random(80, 150));
                    js.vertex((x + 1) * 20, (y+1) * 20, land[x][y+1] + random(80, 150));
                }
                js.vertex(x * scale, y * scale, 1);
                js.vertex(x * scale, (y+1)*scale, 1);
            }
            js.endShape();
        }
    }
}
