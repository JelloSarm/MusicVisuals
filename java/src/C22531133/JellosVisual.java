package C22531133;

import ie.tudublin.*;

public class JellosVisual extends Visual{
    int rows = (int) (200+height / 20);
    int cols = (int) (width / 20);
    float[][] land;

    public void draw() {
        rows = (int) (200+height / 20);
        cols = (int) (width / 20);

        land = new float[cols][rows];
    }
}
