package example;


import C22319896.CalvinsVisual;
import ie.tudublin.*;

public class MyVisual extends Visual {
    WaveForm wf;
    AudioBandsVisual abv;
    CalvinsVisual Calvin;
    int frame = 1;
    

    public void settings() {
        size(1024, 500);

        // Use this to make fullscreen
        fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        // fullScreen(P3D, SPAN);
    }

    public void setup() {
        startMinim();

        // Call loadAudio to load an audio file to process
        loadAudio("Chris Christodoulou - The Face of the Deep  ROR2_ Survivors of the Void (2022).mp3");

        // Call this instead to read audio from the microphone
        // startListening();

        wf = new WaveForm(this);
        abv = new AudioBandsVisual(this);
        Calvin = new CalvinsVisual();
        
    }

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

        if(frame == 1)
        {
            wf.render();   
        }
        else if(frame == 2)
        {
            abv.render();   
        }
        else if(frame == 3)
        {
            Calvin.render();
        }
        
    }
}
