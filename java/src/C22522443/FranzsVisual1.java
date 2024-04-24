package C22522443;

import com.jogamp.graph.ui.shapes.Rectangle;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;

public class FranzsVisual1 extends Visual{
    Scene0 Scene_0;
    Scene1 Scene_1;
    Scene2 Scene_2;
    Scene3 Scene_3;
    Scene4 Scene_4;
    Scene5 Scene_5;
    Scene6 Scene_6;
    Scene7 Scene_7;

    MyVisual Fs;
    float halfHeight;
    float halfWidth;
    int start = 5000;
    float w;
    float h;
    int move = 0;
    float grab = 0;

    public FranzsVisual1(MyVisual Fs)
    {
        this.Fs = Fs; 
        h = 864;
        w = 1536;
        halfHeight = this.h /2;
        halfWidth = this.w / 2;

        Scene_0 = new Scene0(Fs);
        Scene_1 = new Scene1(Fs);
        Scene_2 = new Scene2(Fs);
        Scene_3 = new Scene3(Fs);
        Scene_4 = new Scene4(Fs);
        Scene_5 = new Scene5(Fs);
        Scene_6 = new Scene6(Fs);
        Scene_7 = new Scene7(Fs);
    }  

    public void render(PShape rocket2,PShape temple,PShape door,PShape arm,PShape guy)/////////////////////////////////PART 1///////////////////////////////////////////////////////
    {   
        //countdown for changing the scenes
        float countdown = (65000- millis());

       // cameraSetup();

        //First scene
        if (countdown > 60000)
        {   
            Scene_1.render(rocket2,temple);
        } else if (countdown <= 60000 && countdown > 56000)/////////////////////////PART 2//////////////////////////////////////////////////////////////////////////////////////
        {   
           Scene_0.render(guy);
        //Third scene, destroying the forcefield
        }else if (countdown <= 56000 && countdown > 49000)/////////////////////////PART 2//////////////////////////////////////////////////////////////////////////////////////
        {   
           Scene_2.render(arm);
        //Third scene, destroying the forcefield
        } else if (countdown <= 49000 && countdown > 41000)
        {   
           Scene_3.render(rocket2,temple);
        } 
        else if(countdown <= 41000 && countdown > 36000)////////////////////////////////PART 3////////////////////////////////////////////////////////////////////////
        { 
            Scene_4.render(temple,door,move);

            if(countdown < 38500)
            {
                move = move + 1;
            }
        }
        else if(countdown <= 36000 && countdown > 6000)////////////////////////////////PART 3////////////////////////////////////////////////////////////////////////
        { 
            Scene_5.render(arm,start,grab);

            if(countdown < 15000)
            {
                start = 10;

                grab += 0.07f;
            }
        }
        else if(countdown > 2000)
        {
            Scene_6.render(guy);
        }
        else
        { 
            //cameraSetup();
            Scene_7.render(rocket2,temple);
        }
    }

    //Function to set camera so other scenes dont affect it
    private void cameraSetup()
    {
        //Code from Jellos camera so mine doesnt change
        float fov = PI / (float)3.0;
        float z = (h / 2.0f) / tan(fov / 2.0f);
        
        // Camera Function
        Fs.camera(w / 2.0f, h/ 2.0f, z,
        w / 2.0f, h / 2.0f,
        0, 0, 1, 0);
    }

}