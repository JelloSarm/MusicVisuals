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
        float rotX = -15 * PI / 180;
        float rotZ = -15 * PI / 180;
        

        float gap = Cd.width / (float) Cd.getBands().length;
        for(int i = 0 ; i < Cd.getBands().length ; i ++)
        {
            Cd.background(0);
            Cd.noStroke();
            Cd.pushMatrix();
            Cd.translate(130, height/2, 0);
            Cd.fill(125);
            Cd.box(100);
            Cd.popMatrix();


            Cd.stroke(255);
            Cd.fill(0);
            Cd.pushMatrix();
            Cd.translate(width*7f, height*4f, -200f);
            Cd.rotateX(rotX);
            Cd.rotateY(rotate);
            Cd.rotateZ(rotZ);
            
            rotate += 0.0001 + getAmplitude() / 8.0f;
            Cd.sphere(200);
            Cd.popMatrix(); 
            System.out.println("byeah");
        }
    }

}
