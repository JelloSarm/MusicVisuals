package C22319896;

import ie.tudublin.*;
import processing.core.*;

public class CalvinsVisual extends Visual {

    public void render() {
        // TODO Auto-generated method stub
    
        background(0);
        lights();

        noStroke();
        pushMatrix();
        translate(130, height/2, 0);
        rotateY(1.25f);
        rotateX(-0.4f);
        box(100);
        popMatrix();

        noFill();
        stroke(255);
        pushMatrix();
        translate(500f, height*0.35f, -200f);
        sphere(280);
        popMatrix();
    }

}
