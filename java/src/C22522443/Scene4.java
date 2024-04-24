package C22522443;

import com.jogamp.graph.ui.shapes.Rectangle;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;

public class Scene4 extends Visual{
    MyVisual Fs;
    float halfHeight;
    float halfWidth;
    float w;
    float h;
    float rot;
  

    public Scene4(MyVisual Fs)
    {
        this.Fs = Fs; 
        h = 864;
        w = 1536;
        halfHeight = this.h /2;
        halfWidth = this.w / 2; 
    } 

    public void render(PShape temple,PShape door,int move)/////////////////////////////////PART 1///////////////////////////////////////////////////////
    { 
        
        Fs.pushMatrix();
        Fs.background(0);
        Fs.lights();
        Fs.scale(16);
        Fs.rotateX(radians(-90));

        Fs.translate(49.5f - move,0,-20);
        Fs.shape(door,0,0);
    Fs.popMatrix();

    Fs.pushMatrix();
        Fs.rotateX(radians(-90));
        Fs.rotateY(radians(180));
        Fs.scale(16);
        Fs.translate(-50.5f - move,0,-71.4f);
        Fs.shape(door,0,0);
    Fs.popMatrix();
    
    Fs.pushMatrix();
    Fs.noFill();
    Fs.stroke(255);
    Fs.rotateY(rot);
    Fs.sphere(6500);
    rot = rot + 0.1f/34;
    Fs.popMatrix();
    }
}
