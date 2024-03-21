package C22319896;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;

public class CalvinsVisual extends Visual {

    MyVisual Cd;
    float cy;

    public CalvinsVisual(MyVisual Cd)
    {
        this.Cd = Cd; 
        cy = this.Cd.height /2;
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
        Cd.colorMode(PApplet.HSB);
        
        for(int i = 0 ; i < Cd.getBands().length ; i ++)
        {
            

            // -- background stars --

            // -- planet -- 
            Cd.stroke(255);
            Cd.fill(0);
            Cd.pushMatrix();
            Cd.translate(width*7, height*4, -1000);

            //planet orientation
            Cd.rotateX(mainRotX);
            Cd.rotateY(mainRotY);
            Cd.rotateZ(mainRotZ);
            
            // rotation
            mainRotY -= 0.000 + Cd.getAmplitude() * 0.02f;

            // create
            Cd.sphere(300);
            Cd.popMatrix();


            // -- ring --
            Cd.pushMatrix();
            Cd.strokeWeight(3);
    
            drawRing(width*7, height*4,-1000,700,Cd.getAudioBuffer().size(),300);

            Cd.strokeWeight(1);
            Cd.popMatrix();

            // -- moon --
            Cd.fill(255);
            Cd.stroke(180, 255, 255);
            Cd.pushMatrix();
            Cd.translate(width*7, height*4, -1000);

            // moon rotation
            Cd.rotateX(mainRotX);
            Cd.rotateY(secRotY);
            Cd.rotateZ(mainRotZ);
            
            secRotY += Cd.getAmplitude() * 0.04f;

            Cd.translate(width*7 - (Cd.getAmplitude() * 900f), 1 , -400);
            Cd.rotateY(secRotY*10);
            Cd.sphere(90);
            Cd.popMatrix(); 

            System.out.println(getAmplitude());
        }
    }

    public void drawRing(float centerX, float centerY, float centerZ, float radius, int numPoints, float amplifier)
    {
        float angleIcrement = 2*PI / Cd.getAudioBuffer().size();
        float angle = 0;

        for (int i = 0; i < Cd.getAudioBuffer().size(); i++) {
            Cd.stroke(
                    PApplet.map(i, 0, Cd.getAudioBuffer().size(), 0, 255)
                    , 255
                    , 255
                );

            // start points
            float x = centerX + radius * cos(angle);
            float y = centerY + radius * sin(angle);
            float z = centerZ;

            // end points, away from center
            float x2 = x + cos(angle) * amplifier * Cd.getAudioBuffer().get(i);
            float y2 = y + sin(angle) * amplifier * Cd.getAudioBuffer().get(i);
            float z2 = z ;

            // use this for double sided visualiser
            float xDouble = x - (cos(angle) * amplifier * Cd.getAudioBuffer().get(i));
            float yDouble = y - (sin(angle) * amplifier * Cd.getAudioBuffer().get(i));

            Cd.line(xDouble, yDouble, z, x2, y2, z2);

            angle += angleIcrement;
        }
    }
}
