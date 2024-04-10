package C22522443;

import com.jogamp.graph.ui.shapes.Rectangle;

import example.MyVisual;
import ie.tudublin.*;
import processing.core.*;

public class FranzsVisual1 extends Visual{
    MyVisual Fs;
    float halfHeight;
    float halfWidth;
    float rotation = (float) Math.toRadians(0);
    float w;
    float h;
    float move = 0;
    float movex = 0;
    int movey = 0;
    float[][] land;
    int rows;
    int cols;
    int scale = 20;
    float movement = 0;
    

    

    public FranzsVisual1(MyVisual Fs)
    {
        this.Fs = Fs; 
        h = 864;
        w = 1536;
        halfHeight = this.h /2;
        halfWidth = this.w / 2;
    }  

   

    public void render(PShape rocket2,PShape temple)
    {   
        float countdown = (10000 - millis());
        cameraSetup();

        if (countdown > 0)
        {
            Fs.background(140, 240, 200);
            Fs.stroke(0);
            Fs.fill(198, 255, 255);

            rows = (int) (50 + h / scale);
            cols = (int) (w / scale);

            land = new float[(int)(w / 20)][(int)(50 + h / 20)];
            Fs.colorMode(PApplet.HSB);
   
        

            movement -= 0.02f;
        
            // Y coordinate offset to move the previous Z value on the terrain
            // so that it is a smoother surface
            float yoff = movement;
            for (int y = 0; y < rows - 1; y++) {
                // X coordinate offset to make the previous Z value more smooth
                float xoff = 0;
                for (int x = 0; x < cols; x++) {
                    if (getSmoothedAmplitude() > 200) 
                    {
                        land[x][y] = map(noise(xoff, yoff), 0, 1, 0, 60); // (js.getSmoothedAmplitude()
                                                                                                            // * 50)
                    } else 
                    {
                        land[x][y] = map(noise(xoff, yoff) , 0, 1, 0, 60);
                    }
                    xoff += 0.2f;
                }
                yoff += 0.2f;
            }

            Fs.pushMatrix();
            Fs.translate(w / 2, h/2+90);
            Fs.rotateY(radians(-90));
            Fs.rotateX((PI / 2));
            Fs.rotateZ(radians(10));
            Fs.translate(-w / 2, -h * 1.15f);
            

            for (int y = 0; y < rows - 1; y++) 
            {
                // Start the use of Triangle_Strip when using vertex's
                Fs.beginShape(TRIANGLE_STRIP);
                for (int x = 0; x < cols; x++) {
                    /*
                    * Walls idea
                    * if(x < 5)
                    * {
                    * //js.vertex((x+1) * 20, (y) * 20, land[x][y] + 100);
                    * //js.vertex((x + 1) * 20, (y+1) * 20, land[x][y+1] + 100);
                    * land[x][y] = land[x][y] + 25;
                    * land[x][y+1] = land[x][y] + 25;
                    * }
                    * else if(x > cols - 7)
                    * {
                    * //js.vertex((x+1) * 20, (y) * 20, land[x][y] + 100);
                    * //js.vertex((x + 1) * 20, (y+1) * 20, land[x][y+1] + 100);
                    * land[x][y] = land[x][y] + 25;
                    * land[x][y+1] = land[x][y] + 25;
                    * }
                    */

                    Fs.vertex(x * scale, y * scale, land[x][y]);
                    Fs.vertex(x * scale, (y + 1) * scale, land[x][y + 1]);
                }
                // End of using Triangle_Strip
                Fs.endShape();
            }
            Fs.popMatrix();
            
            Fs.lights();
            Fs.pushMatrix();
            Fs.scale(4);
            Fs.rotateY(radians(10));
            Fs.rotateX(radians(-10));
            Fs.translate(210,115,130);
            Fs.shape(temple,0,0);
            Fs.popMatrix();

        // Fs.translate(250,120,0);
            Fs.scale(1);
            Fs.rotateY(radians(-90));
            Fs.pushMatrix();
            Fs.translate(0,0,movex);
            Fs.shape(rocket2,-200,320);
            Fs.popMatrix();

            movex -= 1;


            Fs.rotateY(radians(90));
            Fs.rotateX(radians(90));
            Fs.translate(0,-200,-480);
            for (int i = 0; i < Fs.getAudioBuffer().size(); i++) 
            {
                // Mapping for the audio buffer to go with the width of the screen
                float mapx = PApplet.map(i, 0, Fs.getAudioBuffer().size(), 0, w);
                float mapy = PApplet.map(i, 0, Fs.getAudioBuffer().size(), 0, h * 2);
                Fs.stroke(198, 255, 255);
                Fs.noFill();

                // Back row WaveForm

                Fs.line(mapx, 0, -50,
                        mapx, 0, (600 * Fs.getAudioBuffer().get(i)));

                // Left side Waveform
                Fs.line(0, mapy, -50,
                        0, mapy, (600 * Fs.getAudioBuffer().get(i)));

                // Right side Waveform
                Fs.line(w, mapy, -50,
                        w, mapy, (600 * Fs.getAudioBuffer().get(i)));
            }
        } else if (countdown <= 1)
        {
            Fs.background(0);
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
        //hand.rotateX(180); 
        //hand.rotateZ();
        Fs.pushMatrix();
        Fs.scale(90);
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
        star.scale(-10);

        Fs.pushMatrix();
        star.rotateX(30);
        Fs.shape(star,halfHeight,halfWidth);
        Fs.popMatrix();
    }
}