package C22522443;

import com.jogamp.graph.ui.shapes.Rectangle;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;

public class FranzsVisual extends Visual{
    MyVisual Fs;
    float halfHeight;
    float halfWidth;
    float rotation = (float) Math.toRadians(0);
    float w;
    float h;

    public FranzsVisual(MyVisual Fs)
    {
        this.Fs = Fs; 
        h = Fs.height;
        w = Fs.width;
        halfHeight = this.h /2;
        halfWidth = this.w / 2;
        
        
    }

    public void render()
    {
        Fs.stroke(255);
        Fs.line(25,halfHeight-20,halfWidth-50,halfHeight-20);
        Fs.line(halfWidth-50,halfHeight-20,halfWidth+75,25);
        Fs.line(25,halfHeight+20,halfWidth-50,halfHeight+20);
        Fs.line(halfWidth-50,halfHeight+20,halfWidth+75,h-25);
        Fs.line(25,25,25,halfHeight-20);
        Fs.line(25,halfHeight+20,25,h-25);
        Fs.line(25,25,halfWidth+75,25);
        Fs.line(25,h-25,halfWidth+75,h-25);
        Fs.line(halfWidth-15,halfHeight,halfWidth+115,25);
        Fs.line(halfWidth-15,halfHeight,halfWidth+115,h-25);
        Fs.line(halfWidth+115,25,w-25,25);
        Fs.line(halfWidth+115,h-25,w-25,h-25);
        Fs.line(w-25,25,w-25,h-25);

        frame();
        templeInner();
        
    }

    private void templeInner()
    {
        //Fs.size(400, 400, P3D);
        //Fs.noStroke();
        //Fs.lights();
        Fs.stroke(255);
        Fs.noFill();
        Fs.translate((halfWidth/2)+500,halfHeight+(halfHeight/2)-650, 200);
       // Fs.pushMatrix();
        //Fs.rotateY(millis() / (float)(9000));
        Fs.sphere(1300);
       // Fs.popMatrix();
    }

    private void frame()
    {   
        Fs.noStroke();
        Fs.fill(0);
        Fs.quad(0,0,25,0,25,h,0,h);
        Fs.quad(0,0,w,0,w,25,0,25);
        Fs.quad(w-25,0,w,0,w,h,w-25,h);
        Fs.quad(0,h-25,w,h-25,w,h,0,h);
        Fs.quad(25,halfHeight-20,halfWidth-15,halfHeight-20,halfWidth-15,halfHeight+20,25,halfHeight+20);
        Fs.quad(halfWidth-50,halfHeight-20,halfWidth+75,25,halfWidth+115,25,halfWidth-15,halfHeight);
        Fs.quad(halfWidth-50,halfHeight+20,halfWidth+75,h-25,halfWidth+115,h-25,halfWidth-15,halfHeight);
    }
}