package C22531133;

import ie.tudublin.*;
import processing.core.PApplet;
import processing.core.PShape;
import example.MyVisual;

public class JellosVisual extends Visual {
    MyVisual js;

    public JellosVisual(MyVisual js) {
        this.js = js;
    }

    // Scale to decide on the sizes of the triangles on the terrain
    int scale = 20;
    int rows;
    int cols;
    // Array to hold the z values for the terrain
    float[][] land;
    float movement = 0;
    float offx = 0, offy = 0;
    float tiltY = 0;
    float tiltX = 0;
    int lock = 0;
    int noOfBugs = 0;
    int score = 0;
    float bugY;
    float bugX;
    boolean isAlive = false;

    float lerpFactor = 0.05f;
    float smoothedAudioBuffer[];

    float lerpedAudioBuffer[];

    public void render(PShape rocket, boolean keyLeftpressed, boolean keyRightpressed, boolean keyUppressed,
            boolean keyDownpressed, boolean keyXpressed) {
        lerpedAudioBuffer();
        // Width and height variables
        int w = js.width;
        int h = js.height;

        // Code used for the parameteres for the camera
        float fov = PI / (float) 3.0;
        float z = (h / 2.0f) / tan(fov / 2.0f);

        // Camera Function
        js.camera(w / 2.0f, h / 2.0f, z,
                w / 2.0f, h / 2.0f,
                0, 0, 1, 0);

        // Deciding how fast the it should move
        movement -= 0.02f + js.getSmoothedAmplitude() / 2;

        // Adding more to row so it goes off screen
        rows = (int) (50 + h / scale);
        cols = (int) (w / scale);

        land = new float[cols][rows];
        // Y coordinate offset to move the previous Z value on the terrain
        // so that it is a smoother surface
        float yoff = movement;
        for (int y = 0; y < rows - 1; y++) {
            // X coordinate offset to make the previous Z value more smooth
            float xoff = 0;
            for (int x = 0; x < cols; x++) {
                land[x][y] = map(noise(xoff, yoff) + (js.getSmoothedAmplitude() * 2), 0, 1, 0, 80);
                land[x][y] = map(noise(xoff, yoff) - (js.getSmoothedAmplitude() * 2), 0, 1, 0, 60);
                xoff += 0.2f;
            }
            yoff += 0.2f;
        }

        js.background(140, 240, 200);
        js.stroke(0);
        js.fill(198, 255, 255);

        // Rotate the object so that it is at an angle

        js.translate(w / 2, h / 4);
        js.rotateX(PI / 3);
        js.translate(-w / 2, -h * 1.15f);

        // For loop for creating the terrain

        for (int y = 0; y < rows - 1; y++) {
            // Start the use of Triangle_Strip when using vertex's
            js.beginShape(TRIANGLE_STRIP);
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

                js.vertex(x * scale, y * scale, land[x][y]);
                js.vertex(x * scale, (y + 1) * scale, land[x][y + 1]);
            }
            // End of using Triangle_Strip
            js.endShape();
        }

        // Audio wave
        for (int i = 0; i < js.getAudioBuffer().size(); i++) {
            // Mapping for the audio buffer to go with the width of the screen
            float mapx = PApplet.map(i, 0, lerpedAudioBuffer.length, 0, w);
            float mapy = PApplet.map(i, 0, lerpedAudioBuffer.length, 0, h * 2);
            js.stroke(198, 255, 255);
            js.noFill();

            // Back row WaveForm

            js.line(mapx, 0, -50,
                    mapx, 0, (6000 * lerpedAudioBuffer[i]));

            // Left side Waveform
            js.line(0, mapy, -50,
                    0, mapy, (6000 * lerpedAudioBuffer[i]));

            // Right side Waveform
            js.line(w, mapy, -50,
                    w, mapy, (6000 * lerpedAudioBuffer[i]));
        }

        js.pushMatrix();

        float shipx = w / 4, shipy = h / 1.08f;
        float bulx = w / 2;

        if (keyLeftpressed) {
            if (offx > -w / 20) {
                offx -= 1f;
                if (tiltY < -6) {
                    tiltY -= 0.2f;
                } else {
                    tiltY -= 0.5f;
                }
                lock = 1;
            }
        }
        if (keyRightpressed) {
            if (offx < w / 20) {
                offx += 1f;

                // Make the tilting smoother
                if (tiltY > 6) {
                    tiltY += 0.2f;
                } else {
                    tiltY += 0.5;
                }
                lock = 1;
            }

        }
        if (keyUppressed) {
            if (offy > -40) {
                offy -= 1f;
                if (tiltX < -6) {
                    tiltX -= 0.2f;
                } else {
                    tiltX -= 0.5f;
                }
                lock = 1;
            }
        }

        if (keyDownpressed) {
            if (offy < 0) {
                offy += 1f;
                if (tiltX > 6) {
                    tiltX += 0.2f;
                } else {
                    tiltX += 0.5;
                }
                lock = 1;
            }
        }

        // Code for creating the rocket starting it and rotating it to the correct
        // orientation

        rocket.resetMatrix();
        rocket.rotateX(radians(270));
        tiltY = tiltyf(tiltY, lock, rocket);
        tiltX = tiltxf(tiltX, lock, rocket);
        rocket.translate(shipx + offx, shipy + offy, 30);
        js.scale(2);
        js.shape(rocket);
        js.popMatrix();
        lock = 0;

        if (keyXpressed) {
            bullet(bulx, offx, h, offy, bugX, bugY);
        }

        if (!isAlive) {
            bugX = random(w / 2.8f, w - w / 2.8f);
            spawnbug(bugX, bugY);
        }

        // bugY = bugY - bugY - (js.getSmoothedAmplitude() * 60);
        bugY = (bugY - 1) - (score);
        if (bugY < -h - 1000) {
            bugY = 0;
        }

        // stars
    }

    // This function allows the ship to tilt when you move and return to its
    // original orientation when you stop moving but on the y axis
    private float tiltyf(float tiltY, int lock, PShape rocket) {

        rocket.rotateY(radians(5 * tiltY));

        if (lock == 0) {
            if (tiltY > 0) {
                tiltY -= 0.5f;
            }

            if (tiltY < 0) {
                tiltY += 0.5f;
            }
        }

        // Stops the ship from tilting too much
        if (tiltY > 9) {
            tiltY = 9;
        }

        if (tiltY < -9) {
            tiltY = -9;
        }

        return tiltY;
    }

    // This function allows the ship to tilt when you move and return to its
    // original orientation when you stop moving but on the X axis
    private float tiltxf(float tiltX, int lock, PShape rocket) {

        rocket.rotateX(radians(-tiltX));

        if (lock == 0) {
            if (tiltX > 0) {
                tiltX -= 0.5f;
            }

            if (tiltX < 0) {
                tiltX += 0.5f;
            }
        }

        // Stops the ship from tilting too much
        if (tiltX > 9) {
            tiltX = 9;
        }

        if (tiltX < -9) {
            tiltX = -9;
        }

        return tiltX;
    }

    void spawnbug(float x, float y) {
        js.stroke(255, 0, 255);

        // Top half
        js.line(x, 0 - bugY, 60, x - 50, 50 - bugY, 60);
        js.line(x - 50, 50 - bugY, 60, x + 50, 50 - bugY, 60);
        js.line(x + 50, 50 - bugY, 60, x, 0 - bugY, 60);

        // Bars connecting the top and bottomg half
        js.line(x, 0 - bugY, 40, x, 0 - bugY, 60);
        js.line(x - 50, 50 - bugY, 40, x - 50, 50 - bugY, 60);
        js.line(x + 50, 50 - bugY, 40, x + 50, 50 - bugY, 60);

        // Bottom half
        js.line(x, 0 - bugY, 40, x - 50, 50 - bugY, 40);
        js.line(x - 50, 50 - bugY, 40, x + 50, 50 - bugY, 40);
        js.line(x + 50, 50 - bugY, 40, x, 0 - bugY, 40);

        js.line(x, 0 - bugY, 40, x - 50, 50 - bugY, 60);
        js.line(x, 0 - bugY, 60, x - 50, 50 - bugY, 40);
        js.line(x - 50, 50 - bugY, 40, x + 50, 50 - bugY, 60);
        js.line(x - 50, 50 - bugY, 60, x + 50, 50 - bugY, 40);
        js.line(x + 50, 50 - bugY, 40, x, 0 - bugY, 60);
        js.line(x + 50, 50 - bugY, 60, x, 0 - bugY, 40);
    }

    void bullet(float startx, float offx, float h, float offy, float bugX, float bugY) {
        js.stroke(255, 255, 255);
        js.strokeWeight(1);
        for (int i = 0; i < js.getAudioBuffer().size(); i++) 
        {
            float mapped = map((float) i, 0, lerpedAudioBuffer.length, 0, (float) js.height + ((h-140) + (offy * 2)));
            //js.line((startx + (offx * 2)), ((h + 950) + (offy * 2)), 60, (startx + 8(offx * 2)), (-1) , 60); safe one simple line
            js.line((startx + (offx * 2)) , mapped, 60, ((startx + (offx * 2)) + (lerpedAudioBuffer[i] * js.width/2)/2), mapped , 60);
        }
        js.strokeWeight(1);
        if (startx + offx >= bugX - 25 && startx + offx <= bugX + 25) {
            killbug(bugY);
        }
    }

    void killbug(float bugY) {
        score++;
        bugX = random(js.width / 2.8f , js.width - js.width/ 2.8f);
        this.bugY = -20;
        isAlive = false;
    }

    public void lerpedAudioBuffer() {

        if (smoothedAudioBuffer == null) {
            smoothedAudioBuffer = new float[js.getAudioBuffer().size()];
            // Initialize lerpedAudioBuffer array if not initialized
            if (lerpedAudioBuffer == null) {
                lerpedAudioBuffer = new float[js.getAudioBuffer().size()];
            }
        }

        // Update smoothedAudioBuffer with new values
        for (int i = 0; i < js.getAudioBuffer().size(); i++) {
            smoothedAudioBuffer[i] = lerp(smoothedAudioBuffer[i], js.getAudioBuffer().get(i), lerpFactor);
            // Update lerpedAudioBuffer with smoothed values
            lerpedAudioBuffer[i] = smoothedAudioBuffer[i];
        }
    }
}
