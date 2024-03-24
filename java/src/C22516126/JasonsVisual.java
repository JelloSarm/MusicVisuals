package C22516126;

import ie.tudublin.*;
import example.MyVisual;

public class JasonsVisual extends Visual {

    MyVisual jg;
    float moonsize = 300;
    float reversecount = 0;

    //variable to ensure scaling through different screen sizes
    public JasonsVisual(MyVisual jg)
    {
        this.jg = jg;
    }

    public void render()
    {
        float multiplier = jg.height * (float)0.2;

        float fov = jg.PI / (float)3.0; // A field of view of 60 degrees
        float cameraZ = (jg.height / 2.0f) / jg.tan(fov / 2.0f);

        //default camera position
        jg.camera(jg.width / 2.0f, jg.height / 2.0f, cameraZ,
        jg.width / 2.0f, jg.height / 2.0f,
        0, 0, 1, 0);

        
        jg.textAlign(CENTER, CENTER);
        jg.textSize(48); 

        float countdown = (10000 - millis());
        String timer = String.format("%.2f", countdown / 1000);

        //planet
        jg.stroke(150, 255, 255);
        //jg.fill(150, 255, 100);
        jg.noFill();
        jg.pushMatrix();
        jg.translate(-jg.height, (jg.height /2), -jg.width);

        //rotate the planet slowly
        //jg.rotateY(millis() / (float)(4000));
        jg.rotateX(millis() / (float)(80000));

        jg.sphere(jg.width);
        jg.popMatrix();

        //planet bounce
        for(int i = 0; i < jg.getAudioBuffer().size(); i++) 
        {
            jg.noFill();

            //looping colours
            int hue = 150 + (i%105);
            jg.stroke(hue, 255, 255);
            
            jg.pushMatrix();
            jg.translate(-jg.height, (jg.height /2), -jg.width);
            jg.circle(0, 0, (jg.getAudioBuffer().get(i) * multiplier) + (jg.width * 2));
            jg.popMatrix();


        }
        
        //before moon detonates
        if (countdown > 0)
        {
            jg.fill(0, 0, 255);
            jg.text("Moon Detonation Immenent\n"+timer, jg.width / 2, jg.height * (float)0.2);

            //moon
            jg.stroke(200, 0, 255);
            jg.fill(200, 255, 255);
            jg.pushMatrix();
            jg.translate(jg.width / 2, (jg.height * (float)0.65), 0);

            //rotate the moon
            jg.rotateY(millis() / (float)(4000));
            jg.rotateX(millis() / (float)(80000));

            jg.sphere(jg.width * (float)0.13);
            jg.popMatrix();

            reversecount = millis() + 3000;
        }

        //after moon detonates
        if (countdown < 1 && moonsize > 0)
        {
            float unscaledMoonsize = reversecount - millis();
            moonsize = unscaledMoonsize / 10;
            jg.fill(0, 0, 255);
            jg.text("Moon Detonation Immenent\n0.00", jg.width / 2, jg.height * (float)0.2);

            //moon
            jg.stroke(0, 255, 0);
            jg.fill(0, 255, 255);
            jg.pushMatrix();
            jg.translate(jg.width / 2, (jg.height * (float)0.65), 0);

            //rotate the moon
            jg.rotateY(millis() / (float)(4000));
            jg.rotateX(millis() / (float)(80000));

            jg.sphere(moonsize);
            jg.popMatrix();


            jg.fill(0, 0, 0);
            jg.circle(jg.width / 2, (jg.height * (float)0.65), (jg.width * (float)0.26));
            for(int i = 0; i < jg.getAudioBuffer().size(); i++) 
            {
                jg.noFill();

                //looping colours
                int hue = (i%105);
                jg.stroke(hue, 255, 255);
                jg.circle(jg.width / 2, (jg.height * (float)0.65), (jg.getAudioBuffer().get(i) * multiplier) + (jg.width * (float)0.26));
            }
                
            
        }

        //placeholder for implosion
        else if (countdown < 1 && moonsize < 0)
        {
            //fill(0, 0, 255);
            //jg.text("Moon Detonation Immenent\n0.00", jg.width / 2, jg.height * (float)0.2);

            
            jg.fill(0, 0, 0);
            jg.circle(jg.width / 2, (jg.height * (float)0.65), (jg.width * (float)0.26));
            for(int i = 0; i < jg.getAudioBuffer().size(); i++) 
            {
                jg.noFill();

                //looping colours
                int hue = (i%105);
                jg.stroke(hue, 255, 255);
                jg.circle(jg.width / 2, (jg.height * (float)0.65), (jg.getAudioBuffer().get(i) * multiplier) + (jg.width * (float)0.26));
                    
                //jg.stroke(hue, 255, 255);
                //float lineX = map(i, 0, jg.getAudioBuffer().size(), 0, jg.width);
                //jg.line(lineX, (jg.getAudioBuffer().get(i) * (jg.width / 6)) + jg.height/2, lineX, (jg.getAudioBuffer().get(i) * (jg.height / 6)) + jg.height/2);
            }
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
 
 */