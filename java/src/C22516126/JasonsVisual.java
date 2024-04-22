/*
todo list
-correct timer
X ship flying away
X make UI look drippy 
-ship flying away scene?
X stars in background?
*/

package C22516126;

import ie.tudublin.*;
import processing.core.PShape;
import processing.core.PVector;
import example.MyVisual;

public class JasonsVisual extends Visual
{
    //PShape ship;

    MyVisual jg;

    //global variables
    float reversecount = 0;
    float moonimplodesize = 300;

    //lerped audio buffer
    float smoothedAudioBuffer[];
    float lerpedAudioBuffer[];
    float lerpFactor = 0.05f;

    //store star coordinates
    float[] row;
    float[] col;

    public JasonsVisual(MyVisual jg)
    {
        jg.background(0, 0, 0);

        //camera settings
        float fov = PI / (float)3.0; // A field of view of 60 degrees
        float cameraZ = (jg.height / 2.0f) / tan(fov / 2.0f);

        //dafault camera position
        jg.camera(jg.width / 2.0f, jg.height / 2.0f, cameraZ,
                  jg.width / 2.0f, jg.height / 2.0f, 0,
                  0, 1, 0);

        //ship = jg.loadShape("ship1.obj");
        this.jg = jg;

        //text settings
        jg.textAlign(CENTER, CENTER);
        jg.textSize(jg.height * (float)0.045 ); 

        //generate random star coordinates
        row = new float[300];
        col = new float[300];

        //assign random positions to stars
        for(int i = 0; i < 300; i++)
        {
            row[i] = jg.random(jg.width);
            col[i] = jg.random(jg.height);

            //reassign position if it spawns within planet or UI
            while ( ((row[i] > (0.3f * jg.width) && col[i] < (0.28f * jg.height)) ||
            (row[i] > (0.5f * jg.width) && col[i] < (0.47f * jg.height)) ||
            (row[i] > (0.58f * jg.width) && col[i] < (0.63f * jg.height)) ||
            (row[i] > (0.69f * jg.width) && col[i] < (0.75f * jg.height)) ||
            (row[i] > (0.84f * jg.width) && col[i] < (0.88f * jg.height)))
            )
            {
                row[i] = jg.random(jg.width);
                col[i] = jg.random(jg.height);
            }   
        }
        
    }//setup end

    public void render(PShape rocket)
    { 
        lerpedAudioBuffer();

        //detonation timer
        float countdown = (17000 - millis());

        //multipliers for scaling
        float multiplier = jg.height * (float)0.2;
        float planetmultiplier = jg.height * (float)0.6;
        
        //moon placement + size
        //jg.width / 2, (jg.height * (float)0.65)
        float moonX = jg.width*(float)0.4;
        float moonY = jg.height * (float)0.65;
        float moonsize = jg.width * (float)0.17;
    
        //ship placement
        float shipX = 0;
        float shipY = 0;
        shipY = moonY - moonsize*sin(45) - moonsize * 0.15f;
        shipX = moonX - moonsize*cos(45) - moonsize * 0.15f;
        shipY = shipY - millis() / 50;
        shipX = shipX - millis() / 50;

        //planet placement
        //(-jg.height, (jg.height /2), -jg.width);
        float planetX = jg.height*4;
        float planetY = -jg.height * 2;
        float planetZ = -jg.width;
        float planetsize = jg.width* 2;


        //objects
        moonDetUI(multiplier);  //create UI
        stars();    //make randomly positioned stars in the background
        fly(rocket, shipX, shipY);  //make moving ship
        makePlanet(planetX, planetY, planetZ, planetsize);  //make planet
        planetbounce(planetX, planetY, planetZ, planetsize, planetmultiplier); //planet bounce
        
        //moon states
        //before moon detonates
        if (countdown > 0)
        {
            moon1(moonX, moonY, moonsize, countdown, multiplier);
        }
        //implosion begins
        else if (countdown <= 1 && moonimplodesize > 0)
        {
            moon2(moonX, moonY, moonsize, countdown, multiplier);
        }
        //implosion
        else
        {
            moon3(moonX, moonY, moonsize, countdown, multiplier);
        }

    }//render() end


    //FUNCTIONS
    //state of the moon before detonation
    private void fly(PShape rocket, float shipX, float shipY)
    {
        rocket.resetMatrix();
        jg.pushMatrix();
        jg.translate(shipX, shipY);
        jg.rotateY(PI*(float)0.6);
        jg.rotateX(PI * (float)1.7);
        jg.scale(2);
        jg.shape(rocket);
        jg.popMatrix();
    }

    private void makePlanet(float planetX, float planetY, float planetZ,float planetsize)
    {
        jg.pushMatrix();
        jg.translate(planetX, planetY, planetZ);
        jg.rotateX(millis() / (float)(80000)); //rotate the planet slowly
        jg.stroke(150, 255, 255);
        jg.fill(150, 255, 50, 255);
        jg.sphere(planetsize);
        jg.popMatrix();

    }

    private void planetbounce(float planetX, float planetY, float planetZ,float planetsize, float planetmultiplier)
    {
        for(int i = 0; i < jg.getAudioBuffer().size(); i++) 
        {
            jg.noFill();

            //loop a range of colours
            int hue = 150 + (i%105);
            jg.stroke(hue, 255, 255);
            
            jg.pushMatrix();
            jg.translate(planetX, planetY, planetZ);
            jg.circle(0, 0, (jg.getAudioBuffer().get(i) * planetmultiplier) + (planetsize * 2));
            jg.popMatrix();
        }
        
    }

    private void stars()
    {
        for(int i = 0; i < 300; i++)
        {
            jg.stroke(0, 0, 255);
            jg.circle(row[i], col[i], 2);
        }
    }

    //state of the moon before detonation
    private void moon1(float moonX, float moonY, float moonsize, float countdown, float multiplier)
    {
     
        jg.fill(0, 0, 255);
        String timer = String.format("%.2f", countdown / 1000);

        //formatting
        if (countdown > 10000)
        {
            jg.text("Moon Detonation Immenent\n00:"+timer, jg.width / 2, jg.height * (float) 0.07);
        }
        else
        {
            jg.text("Moon Detonation Immenent\n00:0"+timer, jg.width / 2, jg.height * (float) 0.07);
        }
        
        //moon
        jg.stroke(200, 0, 255);
        jg.fill(200, 255, 255);
        jg.pushMatrix();
        jg.translate(moonX, moonY, 0);

        //rotate the moon
        jg.rotateY(millis() / (float)(4000));
        jg.rotateX(millis() / (float)(80000));

        //jg.sphere(moonsize);
        distortedPlanet(moonsize, multiplier);
        jg.popMatrix();

        reversecount = millis() + 3000;
    }

    //state of the moon during detonation
    private void moon2(float moonX, float moonY, float moonsize, float countdown, float multiplier)
    {
        float unscaledMoonsize = reversecount - millis();
        moonimplodesize = unscaledMoonsize / 10;
        jg.fill(0, 255, 255);
        jg.text("Moon Detonation Immenent\n00:00.00", jg.width / 2, jg.height * (float) 0.07);

        //moon
        jg.stroke(0, 255, 0);
        jg.fill(0, 255, 255);
        jg.pushMatrix();
        jg.translate(moonX, moonY, 0);

        //rotate the moon
        jg.rotateY(millis() / (float)(4000));
        jg.rotateX(millis() / (float)(80000));

        distortedPlanet(moonimplodesize, multiplier * 1.5f);
        //jg.sphere(moonimplodesize);
        jg.popMatrix();


        web(moonX, moonY, moonsize, multiplier);

        for(int i = 0; i < jg.getAudioBuffer().size(); i++) 
        {
            jg.fill(0, 0, 0);
            jg.circle(moonX, moonY, moonsize * 2);
            jg.noFill();

            //looping colours
            int hue = (i%105);
            jg.stroke(hue, 255, 255);
            jg.circle(moonX, moonY, (jg.getAudioBuffer().get(i) * multiplier) + (moonsize* 2));
        }
        
    }

    private void moon3(float moonX, float moonY, float moonsize, float countdown, float multiplier)
    {   
        
        jg.fill(0, 255, 255);
        jg.text("Moon Detonation Immenent\n00:00.00", jg.width / 2, jg.height * (float) 0.07);

        jg.fill(0, 0, 0);
        jg.circle(moonX, moonY, moonsize * 2);
        for(int i = 0; i < jg.getAudioBuffer().size(); i++) 
        {
            jg.noFill();

            //looping colours
            int hue = (i%105);
            jg.stroke(hue, 255, 255);
            jg.circle(moonX, moonY, (jg.getAudioBuffer().get(i) * multiplier) + (moonsize* 2));
        }
        web(moonX, moonY, moonsize, multiplier);
    }

    private void moonDetUI(float multiplier)
    {
        //UI
        jg.stroke(255, 255, 255);
        jg.strokeWeight(2);
        jg.fill(150, 0, 20);
        jg.quad(jg.width * (float)0.3, 0, jg.width * (float)0.35, jg.height * (float) 0.25, jg.width * (float)0.65, jg.height * (float) 0.25, jg.width * (float)0.7, 0);
        jg.strokeWeight(1);

        //waveform
        float wfstart = jg.width * (float)0.4;
        float wfend = jg.width * (float)0.6;

        for(int i = 0 ; i < jg.getAudioBuffer().size() ; i ++)
        {
            float hue = map(i, 0, jg.getAudioBuffer().size() -1, 170, 250);
            float wflength = map(i, 0, jg.getAudioBuffer().size() -1, wfstart, wfend);
            jg.stroke(hue, 255, 255);
            jg.line(wflength, jg.height * (float)0.2, wflength, (jg.height * (float)0.2) + (multiplier / 2) * jg.getAudioBuffer().get(i));

            //uncomment for lerped buffer version
            //jg.line(wflength, jg.height * (float)0.2, wflength, (jg.height * (float)0.2) + (multiplier * 4) * lerpedAudioBuffer[i]);
        }

    }

    //create a sphere which distorts to the music
    //will make more efficient l8r
    private void distortedPlanet(float moonsize, float multiplier)
    {
        float radius = moonsize;
        //lower detail if frames are low
        int sDetail = 20;
        
        //initialize distort variable
        float[] distort;
        distort = new float[lerpedAudioBuffer.length];
        
        for(int k = 0; k < lerpedAudioBuffer.length; k++) 
        {
            distort[k] = lerpedAudioBuffer[k] * multiplier * 3;

        }

        PVector[][] moon;
        moon = new PVector[sDetail+1][sDetail+1];
        for (int i = 0; i <= sDetail; i ++)
        {
            float lat = map(i, 0, sDetail, -HALF_PI, HALF_PI);

            for (int j = 0; j <= sDetail; j ++)
            {
                float lon = map(j, 0, sDetail, -PI, PI);

                //convert lon and lat values into xyz values
                float x = radius * sin(lon) * cos(lat);
                float y = radius * sin(lon) * sin(lat);
                float z = radius * cos(lon);

                
                moon[i][j] = new PVector(x, y, z);
                PVector v = PVector. random3D();
                v.mult(distort[j]);
                moon[i][j].add(v);
            }
        }

        //mesh
        for (int i = 0; i < sDetail; i ++)
        {
            jg.beginShape(TRIANGLE_STRIP);
            for (int j = 0; j < sDetail; j ++)
            {
                PVector v1 = moon[i][j];
                jg.stroke(0, 0, 255);
                //jg.strokeWeight(2);
                jg.vertex(v1.x, v1.y, v1.z);
                PVector v2 = moon[i+1][j+1];
                jg.vertex(v2.x, v2.y, v2.z);
            }
            jg.endShape();
        }
    }

    //taken from calvin/jello
    private void lerpedAudioBuffer()
    {
        if (smoothedAudioBuffer == null) {
            smoothedAudioBuffer = new float[jg.getAudioBuffer().size()];
            // Initialize lerpedAudioBuffer array if not initialized
            if (lerpedAudioBuffer == null) {
                lerpedAudioBuffer = new float[jg.getAudioBuffer().size()];
            }
        }

        // Update smoothedAudioBuffer with new values
        for (int i = 0; i < jg.getAudioBuffer().size(); i++) {
            smoothedAudioBuffer[i] = lerp(smoothedAudioBuffer[i], jg.getAudioBuffer().get(i), lerpFactor);
            // Update lerpedAudioBuffer with smoothed values
            lerpedAudioBuffer[i] = smoothedAudioBuffer[i];
        }
    }

    private void web(float moonX, float moonY, float moonsize, float multiplier)
    {

        //accidental webbing effect
        float dots = 100;
        float dotangle = TWO_PI / dots;
        float dotX = 0;
        float dotY = 0;
        float endX = 0;
        float endY = 0;
        for(int i = 0; i < lerpedAudioBuffer.length; i++)
        {
            dotX = moonX + moonsize * cos(dotangle * i);
            dotY = moonY + moonsize * sin(dotangle * i);

            endX = moonX + lerpedAudioBuffer[i] * cos(dotangle * i) * -multiplier * 20;
            endY = moonY + lerpedAudioBuffer[i] * sin(dotangle * i) * multiplier * 20;

            float hue = map(i, 0, jg.getAudioBuffer().size() -1, 80, 130);
            jg.stroke(hue, 255, 255);
            jg.line(dotX, dotY, endX, endY);
        }
    }

}