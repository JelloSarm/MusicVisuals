package C22516126;

import ie.tudublin.*;
import example.MyVisual;
import processing.core.*;

public class JasonsVisual extends Visual {

    MyVisual jg;
    public JasonsVisual(MyVisual jg)
    {
        this.jg = jg;
    }

    public void render()
    {
        jg.stroke(200, 255, 255);
        jg.noFill();
        jg.pushMatrix();
        jg.translate(jg.width / 2, jg.height /2, 0);
        jg.sphere(300);
        jg.popMatrix();
    }
}