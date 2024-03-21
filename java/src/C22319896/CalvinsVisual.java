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
    
    public void render() 
    {
        float rotate = 0;
        

        rotate += getAmplitude() / 8.0f;
        Cd.background(0);
        Cd.lights();

        float gap = Cd.width / (float) Cd.getBands().length;
        for(int i = 0 ; i < Cd.getBands().length ; i ++)
        {
            Cd.background(0);
            Cd.noStroke();
            Cd.pushMatrix();
            Cd.translate(300, height/2, 0);
            Cd.rotateY(180);
            Cd.rotateX(rotate);
            Cd.box(500);
            Cd.popMatrix();

            Cd.noFill();
            Cd.stroke(255);
            Cd.pushMatrix();
            Cd.translate(width*7f, height*4f, -200f);
            Cd.sphere(200);
            Cd.popMatrix(); 
            System.out.println("byeah");
        }
    }

}
