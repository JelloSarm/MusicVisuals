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
    float move = 0;
    float movex = 0;
    int movey = 0;

    public FranzsVisual(MyVisual Fs)
    {
        this.Fs = Fs; 
        h = Fs.height;
        w = Fs.width;
        halfHeight = this.h /2;
        halfWidth = this.w / 2;
    }  

   

    public void render(PShape hand, PShape guy,PShape star)
    {
        Fs.colorMode(PApplet.HSB);
        //cameraSetup();


        frame();
        //templeInner();
        //drawHand(hand);

        //drawStar(star);
        Fs.lights();
        Fs.translate(300,500,0);
        Fs.rotateX(5);
       // Fs.rotateY(90);
        for(int i = 0 ; i < Fs.getAudioBuffer().size() ; i ++)
        {
            Fs.stroke(
                PApplet.map(i, 0, Fs.getAudioBuffer().size(), 0, 255)
                , 255
                , 255
            );

            Fs.pushMatrix();
            Fs.scale(50);
            //Fs.translate(0,Fs.getAudioBuffer().get(i),0);
            Fs.shape(hand,0,0);
            Fs.popMatrix();

        }
        
    }

    private void templeInner()
    {
        //Fs.size(400, 400, P3D);
        //Fs.noStroke();
        //Fs.lights();
        
        Fs.noFill();
        Fs.translate((halfWidth/2)+500,halfHeight+(halfHeight/2)-650, 200);
        //Fs.pushMatrix();
        //Fs.rotateY(millis() / (float)(9000));
        Fs.sphere(1300);
        //Fs.popMatrix();
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
        
    }

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


    private void drawHand(PShape hand)
    {
        Fs.lights();
        Fs.translate(0,0,0);
        
       
        //Fs.scale(0,0,0);
        hand.resetMatrix();
        hand.rotateY(225); 
        hand.rotateX(180); 
        //hand.rotateZ();
        Fs.pushMatrix();
        Fs.scale(900+move,500,500);
        //Fs.translate(movex,move,move);
         
        Fs.shape(hand,0,0);
        Fs.popMatrix();

        move += (float)5;
        movex -= (float)0.0009;
    }


    private void drawStar(PShape star)
    {
        float num;

        num = (float)0.01;

        star.resetMatrix();
        star.scale(2);

        Fs.pushMatrix();
        star.rotateX(30);
        Fs.shape(star,halfHeight,halfWidth);
        Fs.popMatrix();
    }
}