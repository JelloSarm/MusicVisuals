package C22522443;

import com.jogamp.graph.ui.shapes.Rectangle;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;


public class Scene7 extends Visual{ 
    MyVisual Fs;
    float halfHeight;
    float halfWidth;
    float rotation = (float) Math.toRadians(0);
    float rot = 0;
    float w;
    float h;
    float movex = 0;
    float[][] land;
    int rows;
    int cols;
    int scale = 20;
    float movement = 0;
    float sink = 0;
    int randX = 0;
    int randY = 0;
    int randZ = 0;


    public Scene7(MyVisual Fs)
    {
        this.Fs = Fs; 
        h = 864;
        w = 1536;
        halfHeight = this.h /2;
        halfWidth = this.w / 2;
    }  

    public void render(PShape rocket2,PShape temple)/////////////////////////////////PART 1///////////////////////////////////////////////////////
    {  
        randY = (int) random(-6,6);
        randX = (int) random(-6,6);
        randZ = (int) random(-6,6);
        Fs.camera(w / 2.0f, h/ 2.0f, (h / 2.0f) / tan(PI / (float)3.0 / 2.0f),
        (w / 2.0f)+randX, (h / 2.0f)+randY,
        (0)+ randZ, 0, 1, 0);

         //Code from Jello for the terrain
         Fs.background(140, 240, 200F);
         Fs.stroke(0);
         Fs.fill(198, 255, 255);

         rows = (int) (50 + h / scale);
         cols = (int) (w / scale);

         land = new float[(int)(w / 20)][(int)(50 + h / 20)];
         Fs.colorMode(PApplet.HSB);

         movement -= 0.02f;
     
         // Y coordinate offset to move the previous Z value on the terrain
         // so that it is a smoother surface
         float yoff = movement;
         for (int y = 0; y < rows - 1; y++) {
             // X coordinate offset to make the previous Z value more smooth
             float xoff = 0;
             for (int x = 0; x < cols; x++) {
                 if (getSmoothedAmplitude() > 200) 
                 {
                     land[x][y] = map(noise(xoff, yoff), 0, 1, 0, 60); // (js.getSmoothedAmplitude()
                                                                                                         // * 50)
                 } else 
                 {
                     land[x][y] = map(noise(xoff, yoff) , 0, 1, 0, 60);
                 }
                 xoff += 0.2f;
             }
             yoff += 0.2f;
         }

         Fs.pushMatrix();
             Fs.translate(w / 2, h/2+90);
             Fs.rotateY(radians(-90));
             Fs.rotateX((PI / 2));
             Fs.rotateZ(radians(10));
             Fs.translate(-w / 2, -h * 1.15f);
             
             for (int y = 0; y < rows - 1; y++) 
             {
                 // Start the use of Triangle_Strip when using vertex's
                 Fs.beginShape(TRIANGLE_STRIP);
                 for (int x = 0; x < cols; x++) {
                     Fs.vertex(x * scale, y * scale, land[x][y]);
                     Fs.vertex(x * scale, (y + 1) * scale, land[x][y + 1]);
                 }
                 // End of using Triangle_Strip
                 Fs.endShape();
             }
         Fs.popMatrix();
         
         //Rendering the temple and rocket
         Fs.lights();

         Fs.pushMatrix();
             Fs.scale(4);
             Fs.rotateY(radians(10 + sink));
             Fs.rotateX(radians(-10 - sink));
             Fs.translate(210,115 + sink,130);
             Fs.shape(temple,0,0);
             Fs.noFill(); 
             sink += 0.01;
         Fs.popMatrix();

         Fs.scale(1);
         
         Fs.pushMatrix();
         Fs.rotateY(radians(90));
             //Moving the rocket
             Fs.translate(0,30,900 + movex);
             Fs.shape(rocket2,-200,320);
         Fs.popMatrix();

         movex -= 1.26f;
       
         //Rotating the waveform
      
         Fs.rotateX(radians(90));
         Fs.translate(0,-200,-480);

         //Code from Jello for getting the waveform
         for (int i = 0; i < Fs.getAudioBuffer().size(); i++) 
         {
             // Mapping for the audio buffer to go with the width of the screen
             float mapx = PApplet.map(i, 0, Fs.getAudioBuffer().size(), 0, w);
             float mapy = PApplet.map(i, 0, Fs.getAudioBuffer().size(), 0, h * 2);
             Fs.stroke(198, 255, 255);
             Fs.noFill();

             // Back row WaveForm

             Fs.line(mapx, 0, -50,
                     mapx, 0, (600 * Fs.getAudioBuffer().get(i)));

             // Left side Waveform
             Fs.line(0, mapy, -50,
                     0, mapy, (600 * Fs.getAudioBuffer().get(i)));

             // Right side Waveform
             Fs.line(w, mapy, -50,
                     w, mapy, (600 * Fs.getAudioBuffer().get(i)));
         }
        }
}
