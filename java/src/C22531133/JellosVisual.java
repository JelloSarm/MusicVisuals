package C22531133;

import ie.tudublin.*;
import processing.core.PApplet;
import example.MyVisual;

public class JellosVisual extends Visual{
    MyVisual js;

    public JellosVisual(MyVisual js)
    {
        this.js = js;
    }

    // Scale to decide on the sizes of the triangles on the terrain
    int scale = 20;
    int rows;
    int cols;
    // Array to hold the z values for the terrain
    float[][] land;
    float movement = 0;

    public void draw() {
        
        
        // Width and height variables
        int w = js.width;
        int h = js.height;

        //Code used for the parameteres for the camera
        float fov = PI / (float)3.0;
        float z = (h / 2.0f) / js.tan(fov / 2.0f);
        
        // Camera Function
        js.camera(w / 2.0f, h/ 2.0f, z,
        w / 2.0f, h / 2.0f,
        0, 0, 1, 0);

        // Deciding how fast the it should move
        movement -= 0.01f;

        // Adding more to row so it goes off screen
        rows = (int) (50 + h / scale);
        cols = (int) (w / scale);

        land = new float[cols][rows];
        
        // Y coordinate offset to move the previous Z value on the terrain
        // so that it is a smoother surface
        float yoff = movement;
        for(int y = 0; y < rows-1; y++)
        {
            // X coordinate offset to make the previous Z value more smooth
            float xoff = 0;
            for(int x = 0; x < cols; x++)
            {
                land[x][y] = map(noise(xoff, yoff),0 ,1 ,0, 60);
                xoff += 0.2f;
            }
            yoff += 0.2f;
        }

        js.background(0);
        js.stroke(0);
        js.fill(198,255,255);

        // Rotate the object so that it is at an angle
        
        js.translate(w / 2 , h / 4);
        js.rotateX(PI/3);
        js.translate(-w / 2, -h * 1.14f);
        
        // For loop for creating the terrain

        for(int y = 0; y < rows-1;y++)
        {
            // Start the use of Triangle_Strip when using vertex's
            js.beginShape(TRIANGLE_STRIP);
            for(int x = 0; x < cols; x++)
            {
                /* 
                Walls idea
                if(x < 5)
                {
                    //js.vertex((x+1) * 20, (y) * 20, land[x][y] + 100);
                    //js.vertex((x + 1) * 20, (y+1) * 20, land[x][y+1] + 100);
                    land[x][y] = land[x][y] + 25;
                    land[x][y+1] = land[x][y] + 25;
                }
                else if(x > cols - 7)
                {
                    //js.vertex((x+1) * 20, (y) * 20, land[x][y] + 100);
                    //js.vertex((x + 1) * 20, (y+1) * 20, land[x][y+1] + 100);
                    land[x][y] = land[x][y] + 25;
                    land[x][y+1] = land[x][y] + 25;
                }
                */

                js.vertex(x * scale, y * scale, land[x][y]);
                js.vertex(x * scale, (y+1)*scale, land[x][y+1]);
            }
            // End of using Triangle_Strip
            js.endShape();
        }

        // Audio wave 
        for( int i = 0 ; i < js.getAudioBuffer().size(); i++)
        {
            // Mapping for the audio buffer to go with the width of the screen
            float mapx = PApplet.map(i, 0, js.getAudioBuffer().size(), 0, w);
            float mapy = PApplet.map(i, 0, js.getAudioBuffer().size(), 0, h*2);
            js.stroke(255);
            js.noFill();
            
            // Back row WaveForm

            js.line(mapx, 0, 0, 
                    mapx, 0, (500 * js.getAudioBuffer().get(i)));

            // Left side Waveform
            js.line(0, mapy, 0, 
                    0, mapy, (500 * js.getAudioBuffer().get(i)));

            // Right side Waveform
            js.line(w, mapy, 0, 
                    w, mapy, (500 * js.getAudioBuffer().get(i)));
        }
        
    }
}
