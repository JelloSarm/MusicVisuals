/*
todo list
-correct timer
X ship flying away
X make UI look drippy 
-ship flying away scene?
-stars in background?
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
    public JasonsVisual(MyVisual jg)
    {
        //ship = jg.loadShape("ship1.obj");
        this.jg = jg;
    }

    public void render(PShape rocket)
    { 
        /*
        jg.pushMatrix();
        jg.translate(jg.width/2, jg.height/2);
        jg.rotateX(90);
        jg.scale(4);
        jg.shape(ship, 0, 0, 200, 200);
        jg.popMatrix();*/
    
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

        //make ship
        rocket.resetMatrix();
        jg.pushMatrix();
        jg.translate(shipX, shipY);
        jg.rotateY(PI*(float)0.6);
        jg.rotateX(PI * (float)1.7);
        jg.scale(2);
        jg.shape(rocket);
        jg.popMatrix();

        //planet placement
        //(-jg.height, (jg.height /2), -jg.width);
        float planetX = jg.height*4;
        float planetY = -jg.height * 2;
        float planetZ = -jg.width;
        float planetsize = jg.width* 2;

        float multiplier = jg.height * (float)0.2;
        float planetmultiplier = jg.height * (float)0.6;

        //camera settings
        float fov = PI / (float)3.0; // A field of view of 60 degrees
        float cameraZ = (jg.height / 2.0f) / tan(fov / 2.0f);


        //default camera position
        jg.camera(jg.width / 2.0f, jg.height / 2.0f, cameraZ,
                  jg.width / 2.0f, jg.height / 2.0f, 0,
                  0, 1, 0);

        //create UI
        moonDetUI(multiplier);
        
        //text settings
        jg.textAlign(CENTER, CENTER);
        jg.textSize(jg.height * (float)0.045 ); 

        //detonation timer
        float countdown = (10000 - millis()); //CHANGE LATER
        String timer = String.format("%.2f", countdown / 1000);

        //planet
        jg.stroke(150, 255, 255);
        jg.fill(150, 255, 50);
        //jg.noFill();
        jg.pushMatrix();
        jg.translate(planetX, planetY, planetZ);

        //rotate the planet slowly
        //jg.rotateY(millis() / (float)(4000));
        jg.rotateX(millis() / (float)(80000));

        jg.sphere(planetsize);
        jg.popMatrix();

        //planet bounce
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
        
        //before moon detonates
        if (countdown > 0)
        {
            moon1(moonX, moonY, moonsize, timer, multiplier);
        }

        //implosion begins
        else if (countdown <= 1 && moonimplodesize > 0)
        {
            moon2(moonX, moonY, moonsize, timer, multiplier);
        }

        //placeholder for implosion
        //else if (countdown <= 1 && moonimplodesize <= 0)
        else
        {
            moon3(moonX, moonY, moonsize, timer, multiplier);
        }
     

    //render() end
    }



    //FUNCTIONS

    //state of the moon before detonation
    void moon1(float moonX, float moonY, float moonsize, String timer, float multiplier) {
     
        jg.fill(0, 0, 255);
        jg.text("Moon Detonation Immenent\n00:0"+timer, jg.width / 2, jg.height * (float) 0.07);

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
    void moon2(float moonX, float moonY, float moonsize, String timer, float multiplier)
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

        distortedPlanet(moonimplodesize, multiplier);
        //jg.sphere(moonimplodesize);
        jg.popMatrix();


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
        
    }

    void moon3(float moonX, float moonY, float moonsize, String timer, float multiplier)
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

    }

    void moonDetUI(float multiplier)
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
        }
        

    }

    //create a sphere which distorts to the music
    //will make more efficient l8r
    void distortedPlanet(float moonsize, float multiplier)
    {
        float radius = moonsize;
        //lower detail if frames are low
        int sDetail = 20;
        
        //initialize distort variable
        float distort = 0;
        
        for(int k = 0; k < jg.getAudioBuffer().size(); k++) 
        {
            //v.mult(jg.getAudioBuffer().get(k) * multiplier / 5);
            distort = jg.getAudioBuffer().get(k) * multiplier / 3;

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
                v.mult(distort);
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

}

/*
 
for(int i = 0 ; i < ab.size() ; i ++)
{
    sum += abs(ab.get(i));
    lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.05f);

    float hue = map(i, 0, ab.size(), 200, 255);
    stroke(hue, 255, 255);
    line(300 + i, (ab.get(i) * (cx) / 2)+400, 300 + i, (ab.get(i) * (cy)/2 )+400); 


unused star function
float stars[][];
for(int i = 0; i < 100; i++)
{
    float row = 0;
    float col = 0;

    row = ((jg.width / 100)*i);
    for(int j = 0; j < 40; j++)
    {

        float offsetx = 0;
        float offsety = 0;

        offsetx = random(-10, 10);
        offsety = random(-10, 10);
        
        col = ((jg.height / 40)*j);
        jg.circle(row, col, 5);

    }
}*/