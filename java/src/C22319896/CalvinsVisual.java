package C22319896;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;

public class CalvinsVisual extends Visual {

    MyVisual Cd;

    public CalvinsVisual(MyVisual Cd)
    {
        this.Cd = Cd; 
    }
    float rotate = 0;

    public void render() 
    {
        
        Cd.background(0);
        Cd.lights();

        //converts degrees to radians, first number is degrees
        //main planet
        float mainRotX = -40 * PI / 180;
        float mainRotZ = -15 * PI / 180;

        //secondary planet
        float secRotX = 40 * PI / 180;
        float secRotZ = 15 * PI / 180;
        

        float gap = Cd.width / (float) Cd.getBands().length;
        for(int i = 0 ; i < Cd.getBands().length ; i ++)
        {
            Cd.background(0);
            Cd.noStroke();
            Cd.pushMatrix();
            
            Cd.fill(125);
            Cd.box(100);
            Cd.popMatrix();


            Cd.stroke(255);
            Cd.fill(0);
            Cd.pushMatrix();
            Cd.translate(width*7f, height*4f, -200f);
            Cd.rotateX(mainRotX);
            Cd.rotateZ(mainRotZ);
            Cd.rotateY(rotate);
            
            rotate += 0.0001 + getAmplitude() * 8.0f;
            Cd.sphere(200);
            Cd.translate(width*7f, height*4f, -50f);
            Cd.sphere(100);
            Cd.popMatrix(); 
            Cd.translate(mouseX,mouseY);
            System.out.println("byeah");
        }
    }

}
