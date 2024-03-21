package C22319896;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;

public class CalvinsVisual extends Visual {

    MyVisual Cd;

    public CalvinsVisual(MyVisual Cd)
    {
        this.Cd = Cd; 
    }
    
    //converts degrees to radians, first number is degrees
    //main planet
    float mainRotX = -40 * PI / 180;
    float mainRotY = 0 * PI / 180;
    float mainRotZ = -15 * PI / 180;

    //secondary (moon)
    float secRotX = 40 * PI / 180;
    float secRotY = 0 * PI / 180;
    float secRotZ = 15 * PI / 180;

    public void render() 
    { 
        Cd.background(0);
        Cd.lights();
        
        float gap = Cd.width / (float) Cd.getBands().length;
        for(int i = 0 ; i < Cd.getBands().length ; i ++)
        {
            Cd.background(0);
            Cd.noStroke();

            // planet
            Cd.stroke(255);
            Cd.fill(0);
            Cd.pushMatrix();
            Cd.translate(width*7, height*4, -1000);
            Cd.rotateX(mainRotX);
            Cd.rotateY(mainRotY);
            Cd.rotateZ(mainRotZ);
            
            mainRotY -= 0.0001 + Cd.getAmplitude() * 0.02f;
            Cd.sphere(200);
            Cd.popMatrix();
            // line
            

            // moon
            Cd.fill(255);
            Cd.stroke(180, 255, 255);
            Cd.pushMatrix();
            Cd.translate(width*7, height*4, -1000);
            Cd.rotateX(mainRotX);
            Cd.rotateY(secRotY);
            Cd.rotateZ(mainRotZ);
            
            secRotY += (0.0001 + Cd.getAmplitude() * 0.02f);

            Cd.translate(width*7 - (Cd.getAmplitude() * 900f), 1 , -400);
            Cd.rotateY(secRotY);
            Cd.sphere(90);
            Cd.popMatrix(); 

            System.out.println(getAmplitude());
        }
    }
}
