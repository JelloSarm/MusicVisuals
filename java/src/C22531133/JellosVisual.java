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

    public void render(PShape rocket, boolean keyLeftpressed, boolean keyRightpressed, boolean keyUppressed,
            boolean keyDownpressed) {

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
                if (getSmoothedAmplitude() > 200) {
                    land[x][y] = map(noise(xoff, yoff) + (js.getSmoothedAmplitude() * 2), 0, 1, 0, 60); // (js.getSmoothedAmplitude()
                                                                                                        // * 50)
                } else {
                    land[x][y] = map(noise(xoff, yoff) - (js.getSmoothedAmplitude() * 2), 0, 1, 0, 60);
                }
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
            float mapx = PApplet.map(i, 0, js.getAudioBuffer().size(), 0, w);
            float mapy = PApplet.map(i, 0, js.getAudioBuffer().size(), 0, h * 2);
            js.stroke(198, 255, 255);
            js.noFill();

            // Back row WaveForm

            js.line(mapx, 0, -50,
                    mapx, 0, (600 * js.getAudioBuffer().get(i)));

            // Left side Waveform
            js.line(0, mapy, -50,
                    0, mapy, (600 * js.getAudioBuffer().get(i)));

            // Right side Waveform
            js.line(w, mapy, -50,
                    w, mapy, (600 * js.getAudioBuffer().get(i)));
        }

        js.pushMatrix();

        float shipx = w / 4, shipy = h / 1.08f;

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

    public class bug
    {
        
    }
    public void spawnBug(PShape guy, int w, int h) {
        js.pushMatrix();
        if (score < 10) {
            if (noOfBugs < 2) {
                spawnNewBug(guy, w, h);
                System.out.println("Dude has spawned");
                noOfBugs++;
            }
        }
        js.popMatrix();
    }

    public void spawnNewBug(PShape guy, int w, int h)
    {
        js.pushMatrix();
        guy.resetMatrix();
        guy.rotateX(radians(270));
        guy.translate(10, 10, 50);
        js.scale(2);
        js.shape(guy);
        js.popMatrix();
    }
}
