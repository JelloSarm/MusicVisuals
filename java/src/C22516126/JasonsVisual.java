package C22516126;

import ie.tudublin.*;
import example.MyVisual;
import processing.core.*;

public class JasonsVisual extends Visual {

    MyVisual jg;
    int moon = 1;
    public JasonsVisual(MyVisual jg)
    {
        this.jg = jg;
    }

    public void render()
    {


        jg.textAlign(CENTER, CENTER);
        jg.textSize(48); 

        float countdown = (10000 - millis());
        String timer = String.format("%.2f", countdown / 1000);

        //planet
        jg.stroke(150, 255, 100);
        jg.noFill();
        jg.pushMatrix();
        jg.translate(-1000, (jg.height /2) + 200, -2000);

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

            jg.circle(260, jg.height / 2 + 70, (jg.getAudioBuffer().get(i) * 100) + 1450);
        }
        
        //before moon detonates
        if (countdown > 0)
        {
            jg.fill(0, 0, 255);
            jg.text("Moon Detonation Immenent\n"+timer, jg.width / 2, jg.height - 2000 / 2);

            //moon
            jg.stroke(200, 0, 255);
            jg.fill(200, 255, 255);
            jg.pushMatrix();
            jg.translate(jg.width / 2, (jg.height /2) + 170, 0);

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
            jg.text("Moon Detonation Immenent\n"+"0.00", jg.width / 2, jg.height - 2000 / 2);

            //moon
            jg.stroke(0, 255, 0);
            jg.fill(0, 255, 255);
            jg.pushMatrix();
            jg.translate(jg.width / 2, (jg.height /2) + 170, 0);

            //rotate the moon
            jg.rotateY(millis() / (float)(4000));
            jg.rotateX(millis() / (float)(80000));

            jg.sphere(300);
            jg.popMatrix();
        }
        


    }


}

/* 
 
        case 2:
            {
                for(int i = 0 ; i < ab.size() ; i ++)
                {
                
                    noFill();
                    float hue = map(i, 0, ab.size(), 0, 256);
                    stroke(hue, 255, 255);
                    circle(i, cy, ab.get(i) * cy);
                }
            }
            break;
        case 3: 
            for(int i = 0 ; i < ab.size() ; i ++)
            {

                noFill();
                float hue = map(i, 0, ab.size(), 0, 256);
                stroke(hue, 255, 255);
                circle(cx, cy, ab.get(i) * cy);
                System.out.println(ab.get(i) * cy);
            }

*/