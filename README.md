Pi RGB-LED
==========

A Java library to control RGB-LEDs and RGB-LED stripes on the Raspberry Pi.

how to use?
===========
Pi RGB-LED is now available in the maven central repository. You can use it with maven like this:

    <dependency>
        <groupId>com.pi3g.pi</groupId>
        <artifactId>pi-rgbled</artifactId>
        <version>1.1</version>
    </dependency>


example
=======

To use it with the PiBorg LedBorg just instantiate the class and use it like this:

    RGBLed rgbLed = new RGBLed(PinLayout.PIBORG_LEDBORG);
    rgbLed.displayColor(Color.RED);

Other RGB-LEDs (that use PWM to controll the brightness of each color) can also be controlled but need
a manual PinLayout. Just instantiate the PinLayout class with the appropriate pins for red, green and blue
and pass them the the RGBLed constructor.

To control a Dyco RGB LED stripe with e.g. 50 LEDs you can use this library like this:

    RGBLedStripe stripe = new RGBLedStripe(RaspiPin.GPIO_00, RaspiPin.GPIO_01, 50);
    stripe.setAllColors(Color.RED);
    stripe.update();

This will set all LEDs on the stripe to red. In this example GPIO_0 was connected to CLK and GPIO_1 to DATA (see http://pi4j.com/example/control.html#Wiring_Diagram).
A dyco led stripe has the following connections:

![ScreenShot](/docs/images/dycowiring.png)

The documentation for the used LEDs can be found here: http://www.kirronlightcomponents.com/images/downloads/dyco_led/dyco_led.pdf

You can buy the LEDs preassembled on a LED stripe or order them separatly and wire them yourself. Either
way this library supports both setups.

how to build?
=============

The entire project is build with maven. Just clone the master branch, open the directory in NetBeans and hit run. Or if
you prefer the command line:

    mvn install

should build everything correctly.

