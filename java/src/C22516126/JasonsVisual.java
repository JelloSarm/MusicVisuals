package C22516126;

import ie.tudublin.*;
import example.MyVisual;

public class JasonsVisual extends Visual
{

    MyVisual jg;
    float reversecount = 0;
    float moonimplodesize = 300;
    

    //variable to ensure scaling through different screen sizes
    public JasonsVisual(MyVisual jg)
    {
        this.jg = jg;
    }

    public void render()
    { 
        //moon placement + size
        //jg.width / 2, (jg.height * (float)0.65)
        float moonX = jg.width*(float)0.4;
        float moonY = jg.height * (float)0.65;
        float moonsize = jg.width * (float)0.17;

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
        jg.width / 2.0f, jg.height / 2.0f,
        0, 0, 1, 0);

        
        //text settings
        //jg.textAlign(CENTER, CENTER);
        jg.textSize(48); 

        //detonation timer
        float countdown = (10000 - millis());
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
            moon1(moonX, moonY, moonsize, timer);
        }

        //implosion begins
        if (countdown < 1 && moonimplodesize > 0)
        {
            moon2(moonX, moonY, moonsize, timer, multiplier);
        }

        //placeholder for implosion
        else if (countdown < 1 && moonimplodesize < 0)
        {
            moon3(moonX, moonY, moonsize, timer, multiplier);
        }
     

    //render() end
    }



    
    //state of the moon before detonation
    void moon1(float moonX, float moonY, float moonsize, String timer) {
     
            jg.fill(0, 0, 255);
            jg.text("Moon Detonation Immenent\n"+"00:0"+timer, jg.width * (float) 0.05, jg.height * (float) 0.1);

            //moon
            jg.stroke(200, 0, 255);
            jg.fill(200, 255, 255);
            jg.pushMatrix();
            jg.translate(moonX, moonY, 0);

            //rotate the moon
            jg.rotateY(millis() / (float)(4000));
            jg.rotateX(millis() / (float)(80000));

            jg.sphere(moonsize);
            jg.popMatrix();

            reversecount = millis() + 3000;
    }

    //state of the moon during detonation
    void moon2(float moonX, float moonY, float moonsize, String timer, float multiplier)
    {
        float unscaledMoonsize = reversecount - millis();
        moonimplodesize = unscaledMoonsize / 10;
        jg.fill(0, 200, 255);
        jg.text("Moon Detonation Immenent\n0.00", jg.width * (float) 0.05, jg.height * (float) 0.1);

        //moon
        jg.stroke(0, 255, 0);
        jg.fill(0, 255, 255);
        jg.pushMatrix();
        jg.translate(moonX, moonY, 0);

        //rotate the moon
        jg.rotateY(millis() / (float)(4000));
        jg.rotateX(millis() / (float)(80000));

        jg.sphere(moonimplodesize);
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