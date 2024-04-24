package C22522443;

import com.jogamp.graph.ui.shapes.Rectangle;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;

public class Scene6 extends Visual{
    float x, y;
    float frequency = 2;
    float angle;
    float radius = 50;
    MyVisual Fs;
    float halfHeight;
    float halfWidth;
    float w;
    float h;
    float move = 0;
    float zoom = 0;
    float cameraZ = 600;
    float a;                 // Angle of rotation
    float offset = PI/24.0f;  // Angle offset between boxes
    int num = 12;
    float rot;
    int randX = 0;
    int randY = 0;
    int randZ = 0;

    public Scene6(MyVisual Fs)
    {
        this.Fs = Fs; 
        h = 864;
        w = 1536;
        halfHeight = this.h /2;
        halfWidth = this.w / 2; 
    } 

    public void render(PShape guy)
    {

             randY = (int) random(-5,5);
            randX = (int) random(-5,5);
            randZ = (int) random(-5,5);
            Fs.camera(0, 0,300,
            0+randX, 0+randY,
            0+ randZ, 0, 1, 0);

        Fs.colorMode(HSB);
        Fs.background(0);
       
        Fs.lights();
          
     
        a += 0.02;

        x = sin(radians(angle))*radius;
        y = cos(radians(angle))*radius;
        
       
        
        Fs.pushMatrix();
        Fs.scale(20);
        Fs.translate(-6,73,0);
        Fs.rotateY(radians(90));
        Fs.rotateX(radians(77));
        Fs.shape(guy,0,0);
        Fs.popMatrix();
      


        Fs.pushMatrix();
        Fs.noFill();
        Fs.stroke(255);
        Fs.rotateY(rot);
        Fs.sphere(7200);
        rot = rot + 0.1f/34;
        Fs.popMatrix();
     }
      
    }
