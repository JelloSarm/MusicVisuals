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


# Instructions

# How it works

# What I am most proud of in the assignment

Calvin: I am proud of my implementation of the features which use sin and cos.
My ring function uses sin, cos and the audio buffer to evenly space out each point around the sphere.
The camera movement also uses sin and cos to rotate around the center sphere.
Figuring out circular movement in 3D space was *very* tricky but alas i persevered.

Jello: I am most proud of my creation of terrain, implementing features that use noise and mapping.
My terrain function moves based on both Audio and x offset movement
Secondly I am most proud of my little space invader type movement giving more depth into the scene.

# Markdown Tutorial

This is *emphasis*

This is a bulleted list

- Item
- Item

This is a numbered list

1. Item
1. Item

This is a [hyperlink](http://bryanduggan.org)

# Headings
## Headings
#### Headings
##### Headings

This is code:

```Java
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

So is this without specifying the language:

```
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

This is an image using a relative URL:

![An image](images/p8.png)

This is an image using an absolute URL:

![A different image](https://bryanduggandotorg.files.wordpress.com/2019/02/infinite-forms-00045.png?w=595&h=&zoom=2)

This is a youtube video:

[![YouTube](http://img.youtube.com/vi/J2kHSSFA4NU/0.jpg)](https://www.youtube.com/watch?v=J2kHSSFA4NU)

This is a table:

| Heading 1 | Heading 2 |
|-----------|-----------|
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |

