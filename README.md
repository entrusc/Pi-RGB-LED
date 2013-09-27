Pi RGB-LED
==========

A Java library to control RGB-LEDs on the Raspberry PI.

example
=======

To use it with the PiBorg LedBorg just instantiate the class and use it like this:

    RGBLed rgbLed = new RGBLed(PinLayout.PIBORG_LEDBORG);
    rgbLed.displayColor(Color.RED);

Other RGB-LEDs (that use PWM to controll the brightness of each color) can also be controlled but need
a manual PinLayout. Just instantiate the PinLayout class with the appropriate pins for red, green and blue
and pass them the the RGBLed constructor.

how to build / use?
===================

The entire project is build with maven. Just clone the master branch, open the directory in NetBeans and hit run. Or if
you prefer the command line: 

    mvn install

should build everything correctly. 

You can then use the library in your Projects like this:


    <dependency>
        <groupId>com.pi3g.pi</groupId>
        <artifactId>pi-rgbled</artifactId>
        <version>1.0</version>
    </dependency>
