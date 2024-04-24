package C22522443;

import com.jogamp.graph.ui.shapes.Rectangle;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;

public class Scene0 extends Visual{
    MyVisual Fs;
    float halfHeight;
    float halfWidth;
    float rot = 0;
    float w;
    float h;
    int randX = 0;
    int randY = 0;
    int randZ = 0;


    public Scene0(MyVisual Fs)
    {
        this.Fs = Fs; 
        h = 864;
        w = 1536;
        halfHeight = this.h /2;
        halfWidth = this.w / 2;
        
    } 

    public void render(PShape guy)
    {
        randY = (int) random(-12,12);
            randX = (int) random(-12,12);
            randZ = (int) random(-12,12);
            Fs.camera(w / 2.0f, h/ 2.0f, (h / 2.0f) / tan(PI / (float)3.0 / 2.0f),
            (w / 2.0f)+randX, (h / 2.0f)+randY,
            (0)+ randZ, 0, 1, 0);


            Fs.background(0, 0, 180);
            Fs.fill(140,255,255);
            Fs.stroke(140,255,255);
            Fs.lights();

            Fs.pushMatrix();
                Fs.scale(36);
                Fs.rotateY(radians(90));
                Fs.rotateX(radians(90));
                Fs.translate(0,24,-78);
                Fs.shape(guy,0,0);  
            Fs.popMatrix();

            
    }
}