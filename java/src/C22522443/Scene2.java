package C22522443;

import com.jogamp.graph.ui.shapes.Rectangle;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;

public class Scene2 extends Visual{
    MyVisual Fs;
    float halfHeight;
    float halfWidth;
    float rot = 0;
    float w;
    float h;
    int randX = 0;
    int randY = 0;
    int randZ = 0;


    public Scene2(MyVisual Fs)
    {
        this.Fs = Fs; 
        h = 864;
        w = 1536;
        halfHeight = this.h /2;
        halfWidth = this.w / 2;
        
    } 

    public void render(PShape arm)
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
                Fs.scale(43);
                Fs.rotateY(radians(70));
                Fs.rotateX(radians(-4));
                Fs.rotateZ(radians(60));
                Fs.translate(4,1,-18);
                Fs.shape(arm,0,0);  
            Fs.popMatrix();

            Fs.pushMatrix();
                Fs.translate(750,432,600);
                for (int i = 0; i < Fs.getAudioBuffer().size(); i++) 
                {
                    // Mapping for the audio buffer to go with the width of the screen
                Fs.circle(0,-7,35 +( Fs.getAudioBuffer().get(i)*30));
                }
                
                
                for(int i = 0; i < 3; i ++)
                {   
                    
                    for(int j = 0 ; j < Fs.getBands().length ; j ++)
                    {   
                        Fs.rect(45+(i * 12), 33, 10,-Fs.getSmoothedBands()[i] * 0.16f); 
                    }
                }

                Fs.textSize(10);
                Fs.text("EMP READY",0, 24 , 0);  // Specify a z-axis value
            Fs.popMatrix();
    }
}