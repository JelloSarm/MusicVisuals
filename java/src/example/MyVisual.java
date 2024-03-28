package example;


import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;

import com.jogamp.newt.event.KeyAdapter;

import C22522443.FranzsVisual;
import C22319896.CalvinsVisual;
import C22531133.JellosVisual;
import C22516126.JasonsVisual;
import ie.tudublin.*;

public class MyVisual extends Visual {
    WaveForm wf;
    AudioBandsVisual abv;
    FranzsVisual Franz;
    CalvinsVisual Calvin;
    JellosVisual Jello;
    JasonsVisual Jason;
    int frame = 1;
    

    public void settings() {
        size(1024, 500,P3D);

        // Use this to make fullscreen
        //fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        fullScreen(P3D, SPAN);
    }

    public void setup() {
        startMinim();

        // Call loadAudio to load an audio file to process
        loadAudio("Chris Christodoulou - The Face of the Deep  ROR2_ Survivors of the Void (2022).mp3");

        // Call this instead to read audio from the microphone
        // startListening();

        wf = new WaveForm(this);
        abv = new AudioBandsVisual(this);
        Calvin = new CalvinsVisual(this);
        Jello = new JellosVisual(this);
        Jason = new JasonsVisual(this);
        Franz = new FranzsVisual(this);
        
    }

    // key inputs
    // to implement keyboard input into your scene you must
    // add them as paramenters into your render()

    boolean keyWpressed = false;
    boolean keyApressed = false;
    boolean keySpressed = false;
    boolean keyDpressed = false;
    boolean keyQpressed = false;
    boolean keyEpressed = false;

    int milliseconds = 0;
    int seconds = 0;

    public void keyPressed() {
        if(key == ' ')
        {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
        
        if (key == '1') {
            frame = 1;
        }
        if (key == '2') {
            frame = 2;
        }
        if (key == '3') {
            frame = 3;
        }
        if (key == '4') {
            frame = 4;
        }
        if (key == '5') {
            frame = 5;
        }
        if (key == '6') {
            frame = 6;
        }

        // WASD key inputs
        if (key == 'w' || key == 'W') { // W
            keyWpressed = true;
        }
        if (key == 'a' || key == 'A') { // A
            keyApressed = true;
        }
        if (key == 's' || key == 'S') { // S
            keySpressed = true;
        }
        if (key == 'd' || key == 'D') { // D
            keyDpressed = true;
        }
        if (key == 'q' || key == 'Q') { // Q
            keyQpressed = true;
        }
        if (key == 'e' || key == 'E') { // E
            keyEpressed = true;
        }
    }

    // WASD handles key releases
    public void keyReleased() {
        if (key == 'w' || key == 'W') { // W
            keyWpressed = false;
        }
        if (key == 'a' || key == 'A') { // A
            keyApressed = false;
        }
        if (key == 's' || key == 'S') { // S
            keySpressed = false;
        }
        if (key == 'd' || key == 'D') { // D
            keyDpressed = false;
        }
        if (key == 'q' || key == 'Q') { // Q
            keyQpressed = false;
        }
        if (key == 'e' || key == 'E') { // E
            keyEpressed = false;
        }
        if (key == 'r' || key == 'R') { // R: RESET (restarts all scenes)
            Calvin = null;
            Jello = null;
            Jason = null;
            Calvin = new CalvinsVisual(this);
            Jello = new JellosVisual(this);
            Jason = new JasonsVisual(this);
        }
    }

    public void draw() {
        background(0);
        try {
            // Call this if you want to use FFT data
            calculateFFT();
        } catch (VisualException e) {
            e.printStackTrace();
        }
        // Call this is you want to use frequency bands
        calculateFrequencyBands();

        // Call this is you want to get the average amplitude
        calculateAverageAmplitude();

        // prints status of keyboard inputs every second
        if (millis() > milliseconds) {
            System.out.println("Keyboard input- (every second) - second: " + seconds +
                                "\nW:" + keyWpressed + "| Q:" + keyQpressed +
                                "\nA:" + keyApressed + "| E:" + keyEpressed +
                                "\nS:" + keySpressed + "|" +
                                "\nD:" + keyDpressed + "|" );
            milliseconds += 1000;
            seconds += 1;
        }

        if(frame == 1)
        {
            wf.render();   
        }
        else if(frame == 2)
        {
            abv.render();   
        }
        else if(frame == 3 )
        {
            Calvin.render(  keyQpressed,keyWpressed,keyEpressed,
                            keyApressed,keySpressed,keyDpressed);
        }
        else if(frame == 4)
        {
            Jello.draw();
        }
        else if (frame == 5)
        {
            Franz.render();
        }
        else if (frame == 6)
        {
            Jason.render();
        }
    }
}
