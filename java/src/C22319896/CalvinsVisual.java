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

    // camera XYZ
    float cameraX = 0;
    float cameraY = 0;
    float cameraZ = 1600;
    double cameraRadius = Math.sqrt(cameraX * cameraX + cameraY * cameraY + cameraZ * cameraZ);
    // rollLR/UD works with radians
    float rollLR = (float) Math.toRadians(90);
    float rollUD = (float) Math.toRadians(90);

    float rotateBG = 0;

    //converts degrees to radians
    //main planet
    float mainRotX = (float) Math.toRadians(-40);
    float mainRotY = (float) Math.toRadians(0);
    float mainRotZ = (float) Math.toRadians(-15);

    //secondary (moon)
    float secRotX = (float) Math.toRadians(40);
    float secRotY = (float) Math.toRadians(0);
    float secRotZ = (float) Math.toRadians(15);

    public void render( boolean keyQpressed, boolean keyWpressed, boolean keyEpressed, 
                        boolean keyApressed, boolean keySpressed, boolean keyDpressed)
    { 
        Cd.background(0);
        Cd.lights();
        Cd.colorMode(PApplet.HSB);
        Cd.smooth();

        // -- camera -- 
        // All objects center around the point 0,0,0
        // -------------------------------------------------------------------------
        // !! CAMERA AFFECTS OTHER SCENES, COMMENT OUT THIS PORTION AND FIX LATER !!
        // -------------------------------------------------------------------------
        Cd.camera(cameraX, cameraY, cameraZ,    // camera position
        0.0f, 0.0f, 0.0f,                       // look at position
        0.0f, 1.0f, 0.0f);                      // up direction

        if (keyApressed || keyDpressed || keyWpressed || keySpressed || keyQpressed || keyEpressed) {
        
            if (keyApressed) {
                rollLR += 0.01f;
            }
            if (keyDpressed) {
                rollLR -= 0.01f;
            }
            if (keyWpressed) {
                rollUD += 0.01f;
            }
            if (keySpressed) {
                rollUD -= 0.01f;
            }
            if (keyQpressed) {
                cameraRadius -= 10;
            }
            if (keyEpressed) {
                cameraRadius += 10;
            }

            roll(rollLR, rollUD); // apply rolling movement
        }
        

        // -- background --
        float[] bands = Cd.getSmoothedBands();
        for(int i = 0 ; i < bands.length ; i ++)
        {
            float h = bands[i];

            Cd.stroke(180, 255,((i * (255 / bands.length) ) /2 ) );
            Cd.pushMatrix();
            Cd.translate(0, 0, 0);
            Cd.rotateY(rotateBG += 0.00002f*i);
            Cd.noFill();
            Cd.sphere(1650 + Cd.getAmplitude()*h*3);
            Cd.popMatrix();
        }

        // -- planet -- 
        Cd.stroke(255);
        Cd.fill(0);
        Cd.pushMatrix();
        Cd.translate(0, 0, 0);

        //planet orientation
        Cd.rotateX(mainRotX);
        Cd.rotateY(mainRotY);
        Cd.rotateZ(mainRotZ);
        
        // rotation
        mainRotY -= Cd.getAmplitude() * 0.12f;

        // create
        Cd.sphere(300);
        Cd.popMatrix();

        
        // -- ring --
        Cd.pushMatrix();
        Cd.strokeWeight(2);
    
        drawRing(0, 0, 0,700,Cd.getAudioBuffer().size(),300);

        Cd.popMatrix();

        // -- moon --
        Cd.fill(255);
        Cd.stroke(180, 255, 255);
        Cd.strokeWeight(1);
        Cd.pushMatrix();
        Cd.translate(0, 0, 0);

        // moon rotation
        Cd.rotateX(mainRotX);
        Cd.rotateY(secRotY);
        Cd.rotateZ(mainRotZ);
            
        // moon movement
        secRotY += Cd.getAmplitude() * 0.24f;

        Cd.translate(0 , 0 , 700f);
        Cd.rotateY(secRotY*10);
        Cd.sphere(90);
        Cd.popMatrix();
    
    }

    public void drawRing(float centerX, float centerY, float centerZ, float radius, int numPoints, float amplifier)
    {
        float angleIncrement = 2*PI / Cd.getAudioBuffer().size();
        float angle = 0;

        for (int i = 0; i < Cd.getAudioBuffer().size(); i++) {
            Cd.stroke(PApplet.map(i, 0, Cd.getAudioBuffer().size(), 0, 255), 255, 255);

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

            angle += angleIncrement;
        }
    }
    
    public void roll(float rollLR, float rollUD) {
        //spinning in 3d is hard
        //seems like the Z and Y axis have switched places somehow but it works now
        //probably to do with the camera being pushed into the Z axis
        // also it just goes poof initially when camera is changed
        cameraX = (float) (cameraRadius * cos(rollLR) * sin(rollUD));
        cameraZ = (float) (cameraRadius * sin(rollLR) * sin(rollUD));
        cameraY = (float) (cameraRadius * cos(rollUD));
    }
}
