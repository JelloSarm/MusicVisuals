# Music Visualiser Project

Name: Jason Garcia

Student Number: C22516126

Name : Jello Sarmiento

Student Number : C22531133

Name : Calvin Delporte

Student Number : C22319896

Name : Franz Shih

Student Number: C22522443

## Instructions
- Fork this repository and use it a starter project for your assignment
- Create a new package named your student number and put all your code in this package.
- You should start by creating a subclass of ie.tudublin.Visual
- There is an example visualiser called MyVisual in the example package
- Check out the WaveForm and AudioBandsVisual for examples of how to call the Processing functions from other classes that are not subclasses of PApplet

# Description of the assignment

This assignment takes inspiration from the game "Risk of Rain 2"
The song chosen for the visualiser is also from the game.
The visuals will include elements of outer space, space travel and an alien world.

[Link to the youtube video](https://www.youtube.com/watch?v=VGXBTaEiNeU)


# Instructions
Use numbers 1-4 to change scenes.
R can be used to reset the current scene.

Calvin :
My scene has keyboard control for movement.
WASD is used for moving around the center.
Q/E is used for zooming in and out of the center.

Jello :
For my scene on some devices the moving and shooting causes an error, but for others its completely fine.
The arrow keys are used to move the spaceship and 'X' is used for shooting the waveform laser.
Pressing 'J' will directly bring you to my scene with and bring you to the optimal part of the song to show off my scene.

Jason :
Go to my scene by pressing '4' or 'L' if you want to go to the part of the song that's intended for the scene.
No further user input is required.

# How it works

Jello :
My scene uses a 4 instances of a waveform, each waveform takes in a lerped audio buffer array that reads in each individual frequencies to put onto the scene.
The terrain that is constantly moving has set parameteres with offy and offx, which will keep the posiiton of the z values to move them down along the terrain.
this intern is keeping track of movement variable which tracks the speed that the terrain will be moving at, automatically its set to -0.02, but i have amplified it to
go with the frequency of the song, it takes in a lerped amplitude so its a smoother move but also means it will speed up the faster the song

Jason :
My scene depicts a moon exploding. It has 3 states. The moon before detonation, during detonation and after detonation. I keep track of how much time has passed
since the start of my scene with the millis() function which determines the moon state. The moon is constructed by two nested for loops. The first nested loop 
calculates longitude and latitude values, and converts them from spherical coordinates to rectangular coordinates. These points are saved as vectors. The other nested
loop connects these vectors and makes triangle strips which creates the sphere. The distortion effect is created by multiplying the vector by the lerped audio buffer
and making it go in a random direction.

# What I am most proud of in the assignment

Calvin: I am proud of my implementation of the features which use sin and cos.
My ring function uses sin, cos and the audio buffer to evenly space out each point around the sphere.
The camera movement also uses sin and cos to rotate around the center sphere.
Figuring out circular movement in 3D space was *very* tricky but alas i persevered.

Jello: I am most proud of my creation of terrain, implementing features that use noise and mapping.
My terrain function moves based on both Audio and x offset movement
Secondly I am most proud of my little space invader type movement giving more depth into the scene.
I am proud of including the very easy but scary looking alien that teleports every second multiple times, along with a waveform laser to kill the bug.
Ensuring that most things take consideration of audio was tricky when it came to mapping out the laser, and the terrain, but makes it more interesting knowing that
it moves along with the song.

Franz: Personally, I take the most pride in learning how to use 3d modelling software to create the
OBJ files for my scene. Although the softwares (paint 3d + 3d builder) were basic in nature, it is
an area that I have never had experience in and this project allowed me to venture out of my comfort zone.
I am also proud of orchestrating the scene as it did have to go through several iterations beforehand.
Learning how to use the camera in ways such as screen-shake were fun to learn and were worth the work.

Jason: I'm most proud of the way that I brought my creative vision to life. When we came up with the idea to do a ROR2 
themed project, the first thing I wanted to do was to create the scene where the planet explodes at the end of 
the game because I knew it was going to be cool. I knew it was going to be difficult to do, but I stuck with the 
idea and it all came together in the end. I'm also happy with how I handled the problem solving side of this assignment. 
I ran into a few issues such as low frame rates and scaling on different screen sizes. I was able to resolve these issues by 
optimizing my code and solving inefficiencies.

