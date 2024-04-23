package C22516126;

import ie.tudublin.*;
import processing.core.PShape;
import processing.core.PVector;
import example.MyVisual;

public class JasonsVisual extends Visual
{
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
        this.jg = jg;
        jg.background(0, 0, 0);

        //camera settings
        float fov = PI / 3.0f; // A field of view of 60 degrees
        float cameraZ = (jg.height / 2.0f) / tan(fov / 2.0f);

        //dafault camera position
        jg.camera(jg.width / 2.0f, jg.height / 2.0f, cameraZ,
                  jg.width / 2.0f, jg.height / 2.0f, 0,
                  0, 1, 0);

        //text settings
        jg.textAlign(CENTER, CENTER);
        jg.textSize(jg.height * 0.045f); 

        starPos(); //generate random positions for stars
    }//setup end

    public void render(PShape rocket)
    { 
        lerpedAudioBuffer();

        float countdown = (20000 - millis()); //detonation timer

        //multipliers for scaling
        float multiplier = jg.height * 0.2f;
        float planetmultiplier = jg.height * 0.6f;
        
        //moon placement + size
        float moonX = jg.width* 0.4f;
        float moonY = jg.height * 0.65f;
        float moonsize = jg.width * 0.17f;
    
        //ship placement
        float shipX = 0;
        float shipY = 0;
        shipY = moonY - moonsize*sin(45) - moonsize * 0.125f;
        shipX = moonX - moonsize*cos(45) - moonsize * 0.25f;
        shipY = shipY - millis() / 50;
        shipX = shipX - millis() / 50;

        //planet placement
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
    private void fly(PShape rocket, float shipX, float shipY)
    {
        rocket.resetMatrix();
        jg.pushMatrix();
        jg.translate(shipX, shipY);
        jg.rotateY(PI* 0.6f);
        jg.rotateX(PI * 1.7f);
        jg.scale(2);
        jg.shape(rocket);
        jg.popMatrix();
    }

    //make planet
    private void makePlanet(float planetX, float planetY, float planetZ,float planetsize)
    {
        jg.pushMatrix();
        jg.translate(planetX, planetY, planetZ);
        jg.rotateX(millis() / 80000f); //rotate the planet slowly
        jg.stroke(150, 255, 255);
        jg.fill(150, 255, 50, 255);
        jg.sphere(planetsize);
        jg.popMatrix();

    }

    //planet circle effect
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

    //assign star positions
    private void starPos()
    {
        row = new float[300];
        col = new float[300];

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
    }

    //create stars
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
        if (countdown > 10000) //if timer has double digit seconds
        {
            jg.text("Moon Detonation imminent\n00:"+timer, jg.width / 2, jg.height * 0.07f);
        }
        else //single digit seconds
        {
            jg.text("Moon Detonation imminent\n00:0"+timer, jg.width / 2, jg.height * 0.07f);
        }
        
        //moon
        jg.stroke(200, 0, 255);
        jg.fill(200, 255, 255);
        jg.pushMatrix();
        jg.translate(moonX, moonY, 0);

        //rotate the moon
        jg.rotateY(millis() / 8000f);
        jg.rotateX(millis() / 24000f);
        distortedPlanet(moonsize, multiplier, 20);
        jg.popMatrix();

        reversecount = millis() + 3000; //record time for moon2 function
        
    }

    //state of the moon during detonation
    private void moon2(float moonX, float moonY, float moonsize, float countdown, float multiplier)
    {
        float unscaledMoonsize = reversecount - millis();
        moonimplodesize = unscaledMoonsize / 10;
        jg.fill(0, 255, 255);
        jg.text("Moon Detonation imminent\n00:00.00", jg.width / 2, jg.height * 0.07f);

        //moon
        jg.stroke(0, 255, 0);
        jg.fill(0, 255, 255);
        jg.pushMatrix();
        jg.translate(moonX, moonY, 0);

        //rotate the moon
        jg.rotateY(millis() / 8000f);
        jg.rotateX(millis() / 24000f);

        distortedPlanet(moonimplodesize, multiplier * 1.4f, 12); //higher multiplier to emphasize implosion effect
        jg.popMatrix();

        //create webbing effect behind moon
        web(moonX, moonY, moonsize, multiplier);
    }

    //moon state after detonation
    private void moon3(float moonX, float moonY, float moonsize, float countdown, float multiplier)
    {   
        
        jg.fill(0, 255, 255);
        jg.text("Moon Detonation imminent\n00:00.00", jg.width / 2, jg.height * 0.07f);

        web(moonX, moonY, moonsize, multiplier);
    }

    private void moonDetUI(float multiplier)
    {
        //UI
        jg.stroke(255, 255, 255);
        jg.strokeWeight(2);
        jg.fill(150, 0, 20);
        jg.quad(jg.width * 0.3f, 0, jg.width * 0.35f, jg.height * 0.25f, jg.width * 0.65f, jg.height * 0.25f, jg.width * 0.7f, 0);
        jg.strokeWeight(1);

        //waveform
        float wfstart = jg.width * 0.4f;
        float wfend = jg.width * 0.6f;

        for(int i = 0 ; i < jg.getAudioBuffer().size() ; i ++)
        {
            float hue = map(i, 0, jg.getAudioBuffer().size() -1, 170, 250);
            float wflength = map(i, 0, jg.getAudioBuffer().size() -1, wfstart, wfend);
            jg.stroke(hue, 255, 255);
            jg.line(wflength, jg.height * 0.2f, wflength, (jg.height * 0.2f) + (multiplier / 2) * jg.getAudioBuffer().get(i));

            //uncomment for lerped buffer version
            //jg.line(wflength, jg.height * 0.2f, wflength, (jg.height * 0.2f) + (multiplier * 4) * lerpedAudioBuffer[i]);
        }

    }

    //create a sphere which distorts to the music
    private void distortedPlanet(float moonsize, float multiplier, int sDetail)
    {
        float[] distort;
        distort = new float[lerpedAudioBuffer.length];
        PVector[][] moon;
        moon = new PVector[sDetail+1][sDetail+1];
        
        //calculate buffer length outside of loop to prevent triple nested for loop
        for(int k = 0; k < lerpedAudioBuffer.length; k++) 
        {
            distort[k] = lerpedAudioBuffer[k] * multiplier * 3;//save buffer to array, use as distortion effect in another loop
        }

        //nested loop to create points of sphere
        for (int i = 0; i <= sDetail; i ++)
        {
            float lat = map(i, 0, sDetail, 0, PI); //calculate latitude sphere values

            for (int j = 0; j <= sDetail; j ++)
            {
                float lon = map(j, 0, sDetail, 0, TWO_PI); //calculate longitude sphere values

                //convert from spherical coordinates to rectangular coordinates
                float x = moonsize * sin(lon) * cos(lat);
                float y = moonsize * sin(lon) * sin(lat);
                float z = moonsize * cos(lon);

                moon[i][j] = new PVector(x, y, z); //save vertex position

                if (sDetail == 20) //effect before moon explodes
                {
                    // ripple/distortion effect - random
                    PVector v = PVector. random3D();
                    v.mult(distort[j]);
                    moon[i][j].add(v);
                }

                else //effect during moon explosion
                {
                    //increasing/decreasing size effect - add
                    PVector v = new PVector(x, y, z);
                    v.limit(1.3f);
                    v.mult(distort[j]);
                    moon[i][j].add(v);
                }
            }
        }

        //draw sphere
        for (int i = 0; i < sDetail; i ++)
        {
            jg.beginShape(TRIANGLE_STRIP);
            for (int j = 0; j < sDetail; j ++)
            {
                PVector v1 = moon[i][j];
                jg.stroke(0, 0, 255);
                jg.vertex(v1.x, v1.y, v1.z);
                PVector v2 = moon[i+1][j+1];
                jg.vertex(v2.x, v2.y, v2.z); //connected vertex
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

    //webbing effect
    private void web(float moonX, float moonY, float moonsize, float multiplier)
    {
        float points = 100;
        float pointangle = TWO_PI / points;
        float pointX = 0;
        float pointY = 0;
        float endPointX = 0;
        float endPointY = 0;

        for(int i = 0; i < lerpedAudioBuffer.length; i++)
        {
            //calculate each starting point of webbing effect
            pointX = moonX + moonsize * cos(pointangle * i);
            pointY = moonY + moonsize * sin(pointangle * i);

            //end points
            endPointX = moonX + lerpedAudioBuffer[i] * cos(pointangle * i) * -multiplier * 20;
            endPointY = moonY + lerpedAudioBuffer[i] * sin(pointangle * i) * multiplier * 20;

            float hue = map(i, 0, jg.getAudioBuffer().size() -1, 150, 200);
            jg.stroke(hue, 255, 255);
            jg.line(pointX, pointY, endPointX, endPointY);

            //bouncing circle effect
            jg.noFill();
            float hue2 = 180 + (i % 50);
            jg.stroke(hue2, 255, 255);
            jg.circle(moonX, moonY, (jg.getAudioBuffer().get(i) * multiplier) + (moonsize* 2));
        }
    }

}