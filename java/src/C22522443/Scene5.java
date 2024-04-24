package C22522443;

import com.jogamp.graph.ui.shapes.Rectangle;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;

public class Scene5 extends Visual{
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
    
    public Scene5(MyVisual Fs)
    {
        this.Fs = Fs; 
        h = 864;
        w = 1536;
        halfHeight = this.h /2;
        halfWidth = this.w / 2; 
    } 

    public void render(PShape guy2, int start,float grab)
    {

        if(cameraZ > 200)
        {
            zoom = zoom + 0.4f;
            cameraZ = cameraZ - zoom;
 
        }
        
        Fs.camera(0,0,cameraZ,
        0.0f, 0.0f, 0.0f,                      
        0.0f, 1, 0.0f);


        Fs.noStroke();
        Fs.colorMode(HSB);
        Fs.background(0);
       
        Fs.lights();
          
        for(int i = 0 ; i < Fs.getBands().length ; i ++)
        { 
            for(int j = 0; j < num; j++) 
            {
                Fs.pushMatrix();
                Fs.fill(PApplet.map(i, 0, Fs.getBands().length, 0, 255), 255, 255);
                Fs.rotateY(a + offset*j);
                Fs.rotateX(a/2 + offset*j);
                Fs.box(17 +Fs.getSmoothedBands()[i] * 0.075f );
                Fs.popMatrix();
             } 
        }
        a += 0.02;

        x = sin(radians(angle))*radius;
        y = cos(radians(angle))*radius;
        
       

        Fs.pushMatrix();
        Fs.translate(x, 0, y);
        Fs.scale(4);
        Fs.sphere(1);
        angle += frequency;
        Fs.popMatrix();

        Fs.pushMatrix();
        Fs.translate(0, y, x);
        Fs.scale(4);
        Fs.sphere(1);
        angle += frequency;
        Fs.popMatrix();

        Fs.pushMatrix();
        Fs.translate(y, x, 0);
        Fs.scale(4);
        Fs.sphere(1);
        angle += frequency;
        Fs.popMatrix();
        Fs.noFill();
        Fs.noStroke();

        Fs.pushMatrix();
        Fs.scale(10);
        Fs.translate(-8,start,0 - grab);
        Fs.rotateZ(radians(180));
        Fs.rotateY(radians(-25));
        Fs.shape(guy2,0,0);
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
