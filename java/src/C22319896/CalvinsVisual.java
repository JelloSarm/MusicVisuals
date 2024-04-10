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
    float cameraZ = 2000;
    double cameraRadius = Math.sqrt(cameraX * cameraX + cameraY * cameraY + cameraZ * cameraZ);

    int yUpDirection = 1;
    // rollLR/UD works with radians
    float rollLR = (float) Math.toRadians(90);
    float rollUD = (float) Math.toRadians(90);

    float rotateBG = 0;

    //converts degrees to radians
    //main planet
    float mainRotX = (float) Math.toRadians(-40);
    float mainRotY = (float) Math.toRadians(0);
    float mainRotZ = (float) Math.toRadians(-15);

    //secondary (moon/ship)
    float secRotX = (float) Math.toRadians(40);
    float secRotY = (float) Math.toRadians(0);
    float secRotZ = (float) Math.toRadians(15);

    //offset for ship
    float offset = 0f;

    int ring1Amp = 2500;
    int ring2Amp = 5000;
    int ring1radius = 700;
    int ring2radius = 1400;
    
    float lerpFactor = 0.05f;

    float smoothedAudioBuffer[];

    // Lerped audio buffer
    float lerpedAudioBuffer[];

    public void render( boolean keyQpressed, boolean keyWpressed, boolean keyEpressed, 
                        boolean keyApressed, boolean keySpressed, boolean keyDpressed,
                        PShape rocket)
    { 
        Cd.background(0);
        Cd.lights();
        Cd.colorMode(PApplet.HSB);
        Cd.smooth();
        lerpedAudioBuffer();

        // -- camera -- 
        // All objects center around the point 0,0,0
        Cd.camera(cameraX, cameraY, cameraZ,    // camera position
        0.0f, 0.0f, 0.0f,                       // look at position
        0.0f, yUpDirection, 0.0f);              // up direction

        // checks keyboard input
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

            Cd.stroke(180, 255,((i * (255 / bands.length) ) /2 ) ); // outlines dim
            Cd.pushMatrix();
            Cd.translate(0, 0, 0);
            Cd.rotateY(rotateBG += 0.00002f*i);
            Cd.noFill();
            Cd.sphere(3000 + Cd.getAmplitude()*h*6);
            Cd.popMatrix();
        }
        
        // -- ring --
        Cd.pushMatrix();
        Cd.strokeWeight(3);
    
        drawRing(0, 0, 0,ring1radius,lerpedAudioBuffer.length,ring1Amp);
        drawRing(0, 0, 0,ring2radius,lerpedAudioBuffer.length,ring2Amp);

        Cd.popMatrix();

        // -- ship --
        Cd.pushMatrix();
        rocket.resetMatrix();
        Cd.translate(0, 0, 0);

        offset += 0.01f;

        Cd.rotateX(-mainRotX - offset);
        Cd.rotateY(-secRotY);
        Cd.rotateZ(mainRotZ + offset);

        Cd.translate(0 , 0 , 400f);
        Cd.rotateY( (float) Math.toRadians(90f) );

        Cd.shape(rocket);

        Cd.popMatrix();

        // -- moon --
        Cd.fill(0,0,125);
        Cd.stroke(0, 0, 255);
        Cd.strokeWeight(1);
        Cd.pushMatrix();
        Cd.translate(0, 0, 0);

        // moon rotation
        Cd.rotateX(mainRotX);
        Cd.rotateY(secRotY);
        Cd.rotateZ(mainRotZ);
            
        // moon movement
        secRotY += Cd.getAmplitude() * 0.24f;

        Cd.translate(0 , 0 , 1000f);
        Cd.rotateY(secRotY*10);
        Cd.sphere(90);
        Cd.popMatrix();

        // -- planet -- 
        Cd.pushMatrix();
        Cd.stroke(0);
        Cd.fill(198, 255, 255);   
        Cd.translate(0, 0, 0);

        //planet orientation
        Cd.rotateX(mainRotX);
        Cd.rotateY(mainRotY);
        Cd.rotateZ(mainRotZ);
        
        // rotation
        mainRotY -= Cd.getAmplitude() * 0.12f;

        Cd.sphere(300);
        Cd.popMatrix();

        // create planet atmosphere
        Cd.pushMatrix();
        Cd.fill(140, 240, 200, 20);
        Cd.stroke(140,240,200,0);
        for (int i = 0; i < 12; i++) {
            Cd.sphere(300 + (i * 10));
        }
        Cd.popMatrix();
    
    }

    public void drawRing(float centerX, float centerY, float centerZ, float radius, int numPoints, float amplifier)
    {
        float angleIncrement = 2*PI / lerpedAudioBuffer.length;
        float angle = 0;

        for (int i = 0; i < lerpedAudioBuffer.length; i++) {
            Cd.stroke(PApplet.map(i, 0, lerpedAudioBuffer.length, 0, 255), 255, 255);

            // start points
            float x = centerX + radius * cos(angle);
            float y = centerY + radius * sin(angle);
            float z = centerZ;

            // end points, away from center
            float x2 = x + cos(angle) * amplifier * lerpedAudioBuffer[i];
            float y2 = y + sin(angle) * amplifier * lerpedAudioBuffer[i];
            float z2 = z ;

            // use this for double sided visualiser
            float xDouble = x - (cos(angle) * amplifier * lerpedAudioBuffer[i]);
            float yDouble = y - (sin(angle) * amplifier * lerpedAudioBuffer[i]);

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

        // camera up direction flips to give the illusion of rotation after 180 degrees
        yUpDirection = (sin(rollUD) >= 0) ? 1 : -1;
    }

    public void lerpedAudioBuffer() {

        if (smoothedAudioBuffer == null) {
            smoothedAudioBuffer = new float[Cd.getAudioBuffer().size()];
            // Initialize lerpedAudioBuffer array if not initialized
            if (lerpedAudioBuffer == null) {
                lerpedAudioBuffer = new float[Cd.getAudioBuffer().size()];
            }
        }

        // Update smoothedAudioBuffer with new values
        for (int i = 0; i < Cd.getAudioBuffer().size(); i++) {
            smoothedAudioBuffer[i] = lerp(smoothedAudioBuffer[i], Cd.getAudioBuffer().get(i), lerpFactor);
            // Update lerpedAudioBuffer with smoothed values
            lerpedAudioBuffer[i] = smoothedAudioBuffer[i];
        }
    }
}