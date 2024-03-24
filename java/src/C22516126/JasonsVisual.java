package C22516126;

import ie.tudublin.*;
import example.MyVisual;

public class JasonsVisual extends Visual {

    MyVisual jg;
    int moon = 1;

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
        jg.stroke(150, 255, 100);
        jg.noFill();
        jg.pushMatrix();
        jg.translate(-jg.height, (jg.height /2), -jg.width);

        //rotate the planet slowly
        //jg.rotateY(millis() / (float)(4000));
        jg.rotateX(millis() / (float)(80000));

        jg.sphere(2000);
        jg.popMatrix();

        //planet bounce
        for(int i = 0; i < jg.getAudioBuffer().size(); i++) 
        {
            jg.noFill();
            float hue = map(i, 0, jg.getAudioBuffer().size(), 0, 256);
            jg.stroke(hue, 255, 255);
            
            jg.pushMatrix();
            jg.translate(-jg.height, (jg.height /2), -jg.width);
            jg.circle(0, 0, (jg.getAudioBuffer().get(i) * multiplier) + 4010);
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

            jg.sphere(300);
            jg.popMatrix();
        }

        //after moon detonates
        if (countdown < 1 && moon == 1)
        {
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

            jg.sphere(300);
            jg.popMatrix();
        }
    }


}
