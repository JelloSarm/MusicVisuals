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
    float movement = 0;

    public void draw() {
        int w = js.width;
        int h = js.height;

        movement -= 0.01f;

        rows = (int) (200 + h / scale);
        cols = (int) (w / scale);

        land = new float[cols][rows];

        float yoff = movement;
        for(int y = 0; y < rows; y++)
        {
            float xoff = 0;
            for(int x = 0; x < cols; x++)
            {
                land[x][y] = map(noise(xoff, yoff),0 ,1 ,-40, 40);
                xoff += 0.2f;
            }
            yoff += 0.2f;
        }


        js.background(0);
        js.stroke(255);
        js.noFill();

        js.translate(w / 2 , h / 4);
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
                js.vertex(x * scale, y * scale, land[x][y]);
                js.vertex(x * scale, (y+1)*scale, land[x][y+1]);
            }
            js.endShape();
        }
    }
}
