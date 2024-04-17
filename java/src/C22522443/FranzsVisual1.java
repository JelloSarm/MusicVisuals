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
    float rot = 0;
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
    int expand = 0;
    int fade = 0;
    int randX = 0;
    int randY = 0;
    int randZ = 0;
    

    public FranzsVisual1(MyVisual Fs)
    {
        this.Fs = Fs; 
        h = 864;
        w = 1536;
        halfHeight = this.h /2;
        halfWidth = this.w / 2;
    }  

   

    public void render(PShape rocket2,PShape temple,PShape door,PShape arm)/////////////////////////////////PART 1///////////////////////////////////////////////////////
    {   
        //countdown for changing the scenes
        float countdown = (25000 - millis());

        cameraSetup();

        //First scene
        if (countdown > 20000)
        {

            //Code from Jello for the terrain
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
                        Fs.vertex(x * scale, y * scale, land[x][y]);
                        Fs.vertex(x * scale, (y + 1) * scale, land[x][y + 1]);
                    }
                    // End of using Triangle_Strip
                    Fs.endShape();
                }
            Fs.popMatrix();
            
            //Rendering the temple and rocket
            Fs.lights();

            Fs.pushMatrix();
                Fs.scale(4);
                Fs.rotateY(radians(10));
                Fs.rotateX(radians(-10));
                Fs.translate(210,115,130);
                Fs.shape(temple,0,0);
                Fs.noFill(); 

           
                Fs.pushMatrix();
                    Fs.translate(40,-20,0);
                    Fs.rotateY(rot);
                    for(int i = 0 ; i < Fs.getBands().length ; i ++)
                        {   
                            Fs.fill(255, 240, 200, 20);
                            Fs.stroke(255,240,200);
                            Fs.sphere(70+ (Fs.getSmoothedBands()[i]/20)); 
                        }
                    rot = rot + 0.2f;
                Fs.popMatrix();
            Fs.popMatrix();

            Fs.scale(1);
            Fs.rotateY(radians(-90));
            Fs.pushMatrix();
                //Moving the rocket
                Fs.translate(0,0,movex);
                Fs.shape(rocket2,-200,320);
            Fs.popMatrix();

            movex -= 1f;
          
            //Rotating the waveform
            Fs.rotateY(radians(90));
            Fs.rotateX(radians(90));
            Fs.translate(0,-200,-480);

            //Code from Jello for getting the waveform
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
        } else if (countdown <= 20000 && countdown > 13000)/////////////////////////PART 2//////////////////////////////////////////////////////////////////////////////////////
        {   
            randY = (int) random(-12,12);
            randX = (int) random(-12,12);
            randZ = (int) random(-12,12);
            Fs.camera(w / 2.0f, h/ 2.0f, (h / 2.0f) / tan(PI / (float)3.0 / 2.0f),
            (w / 2.0f)+randX, (h / 2.0f)+randY,
            (0)+ randZ, 0, 1, 0);


            Fs.background(0, 0, 180);
            Fs.fill(140,255,255);
            Fs.stroke(140,255,255);
            Fs.lights();

            Fs.pushMatrix();
                Fs.scale(43);
                Fs.rotateY(radians(70));
                Fs.rotateX(radians(-4));
                Fs.rotateZ(radians(60));
                Fs.translate(4,1,-18);
                Fs.shape(arm,0,0);  
            Fs.popMatrix();

            Fs.pushMatrix();
                Fs.translate(750,432,600);
                for (int i = 0; i < Fs.getAudioBuffer().size(); i++) 
                {
                    // Mapping for the audio buffer to go with the width of the screen
                Fs.circle(0,0,35 +( Fs.getAudioBuffer().get(i)*30));
                }
                
                
                for(int i = 0; i < 3; i ++)
                {   
                    
                    for(int j = 0 ; j < Fs.getBands().length ; j ++)
                    {   
                        Fs.rect(45+(i * 12), 32, 10,-Fs.getSmoothedBands()[i] * 0.2f); 
                    }
                }
            Fs.popMatrix();
          
        //Third scene, destroying the forcefield
        } else if (countdown <= 13000 && countdown > 5000)
        {   
            //Same code as scene 1
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
                        land[x][y] = map(noise(xoff, yoff), 0, 1, 0, 60); 
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

                Fs.pushMatrix();
                    Fs.translate(40,-20,0);
                
                    //Forcefield dissipating
                    Fs.rotateY(rot);
                    for(int i = 0 ; i < Fs.getBands().length ; i ++)
                        {   
                            Fs.fill(255, 240, 200, 20 - fade);
                            Fs.stroke(255,240,200,200 - (fade+30));
                            Fs.sphere(70+ (Fs.getSmoothedBands()[i]/20)); 
                        }
                    
                    //When the countdown reaches this, fade the forcefield away
                    if(countdown <= 11000)
                    {
                        fade++;
                    }
                    //Rotate the forcefield
                    rot = rot + 0.2f;
                Fs.popMatrix();
            Fs.popMatrix();

            //Move the rocket
            Fs.scale(1);
            Fs.rotateY(radians(-90));
            Fs.pushMatrix();
                Fs.translate(0,0,movex);
                Fs.shape(rocket2,-200,320);

                Fs.translate(-200,320,0);

                //Draw and expand the ship weapon
                Fs.fill(140, 255, 255, 230 - expand/3);
                Fs.stroke(140,240,255,255 - expand/3);
                Fs.sphere(30 + expand);
                
            Fs.popMatrix();
                    
            movex -= 1.f;
            expand += 10;

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

                if(countdown < 5002)
                {   
                    //Reset move for the doors
                    move = 0;
                }
            }
        } 
        else if(countdown > 0)////////////////////////////////PART 3////////////////////////////////////////////////////////////////////////
        { 
            //Opening the temple doors
            Fs.pushMatrix();
                Fs.background(0);
                Fs.lights();
                Fs.scale(16);
                Fs.rotateX(radians(-90));

                Fs.translate(49.5f - move,0,-20);
                Fs.shape(door,0,0);
            Fs.popMatrix();

            Fs.pushMatrix();
                Fs.rotateX(radians(-90));
                Fs.rotateY(radians(180));
                Fs.scale(16);
                Fs.translate(-50.5f - move,0,-71.4f);
                Fs.shape(door,0,0);
            Fs.popMatrix();

            if(countdown < 3500)
            {
                move = move + 1;
            }
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
    /* 
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

 */
}