package example;


import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;

import com.jogamp.newt.event.KeyAdapter;

import C22522443.FranzsVisual1;
import C22319896.CalvinsVisual;
import C22531133.JellosVisual;
import C22516126.JasonsVisual;
import ie.tudublin.*;
import processing.core.PShape;

public class MyVisual extends Visual {
    WaveForm wf;
    AudioBandsVisual abv;
    FranzsVisual1 Franz;
    CalvinsVisual Calvin;
    JellosVisual Jello;
    JasonsVisual Jason;
    int frame = 1;
    PShape hand;
    PShape rocket;
    PShape rocket2;
    PShape guy;
    PShape temple;
    PShape door;
    PShape arm;

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
       // loadAudio("heroplanet.mp3");
        loadAudio("Chris Christodoulou - The Face of the Deep  ROR2_ Survivors of the Void (2022).mp3");
       // loadAudio("Meatball Parade (128kbps).mp3");
        //loadAudio("Genesis Retake Light (128kbps).mp3");


        // Call this instead to read audio from the microphone
        // startListening();

        wf = new WaveForm(this);
        abv = new AudioBandsVisual(this);
        Calvin = new CalvinsVisual(this);
        Jello = new JellosVisual(this);
        Jason = new JasonsVisual(this);
        Franz = new FranzsVisual1(this);

        door = loadShape("Door.obj");
        temple = loadShape("Temple.obj");
        arm = loadShape("arm.obj");


        // Rocket ship file
        rocket = loadShape("rocketShip2.obj");
        rocket2 = loadShape("rocketShip2.obj");
        // First create the shape

        
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
    boolean keyLeftpressed = false;
    boolean keyRightpressed = false;
    boolean keyUppressed = false;
    boolean keyDownpressed = false;

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
            Franz = null;
            Franz = new FranzsVisual1(this);
        }
        if (key == '6') {
            frame = 6;
            Jason = null;
            Jason = new JasonsVisual(this);
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

        if ( keyCode == LEFT) {
            keyLeftpressed = true;
        }
        if ( keyCode == RIGHT) {
            keyRightpressed = true;
        }
        if (keyCode == UP) {
            keyUppressed = true;
        }
        if (keyCode == DOWN) {
            keyDownpressed = true;
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
            Franz = null;
            Franz = new FranzsVisual1(this);
            Calvin = new CalvinsVisual(this);
            Jello = new JellosVisual(this);
            Jason = new JasonsVisual(this);
        }

        if (keyCode == LEFT) {
            keyLeftpressed = false;
        }
        if (keyCode == RIGHT) {
            keyRightpressed = false;
        }
        if (keyCode == UP) {
            keyUppressed = false;
        }
        if (keyCode == DOWN) {
            keyDownpressed = false;
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
                                "\nS:" + keySpressed + "| <:" + keyLeftpressed +
                                "\nD:" + keyDpressed + "| >:" + keyRightpressed +
                                "\n^:" + keyUppressed+ "| V:" + keyDownpressed);
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
                            keyApressed,keySpressed,keyDpressed,
                            rocket);
        }
        else if(frame == 4)
        {
            Jello.render(rocket, keyLeftpressed, keyRightpressed, keyUppressed, keyDownpressed);
            Jello.spawnBug(rocket, width, height);
            
        }
        else if (frame == 5)
        {
            Franz.render(rocket2,temple,door,arm);
            
        }
        else if (frame == 6)
        {
            Jason.render(rocket);
        }
    }
}
