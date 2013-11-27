/*
 * Copyright (C) 2013 Florian Frankenberger.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package de.pi3g.pi.rgbled;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import java.awt.Color;

/**
 * A RGB LED stripe where each LED can be addressed directly using a
 * monodirectional SPI chain. A sample for such a led stripe is
 * are dyco RGB LED stripes.
 * <p />
 * A compatible stripe should have four connections:
 * <ul>
 * <li>DATA</li>
 * <li>CLK</li>
 * <li>- GND</li>
 * <li>+ 5V</li>
 * </ul>
 *
 * @author Florian Frankenberger
 */
public class RGBLedStripe {

    private final Color[] colors;
    private final GpioPinDigitalOutput clkPin, dataPin;

    /**
     * inits this class
     *
     * @param clkPin the PIN where CLK of the LED stripe is connected
     * @param datPin the PIN where DATA of the LED stripe is connected
     * @param leds the amount of leds on the stripe to control
     */
    public RGBLedStripe(Pin clkPin, Pin datPin, int leds) {
        if (leds <= 0) {
            throw new IllegalArgumentException("the stripe needs to have at least one led");
        }

        final GpioController gpio = GpioFactory.getInstance();
        this.clkPin = gpio.provisionDigitalOutputPin(clkPin);
        this.dataPin = gpio.provisionDigitalOutputPin(datPin);
        this.colors = new Color[leds];

        //set all lights black
        setAllColors(Color.BLACK);
        update();
    }

    /**
     * sets the color for one led. Note that you have to call update()
     * to transmit the change to the hardware stripe.
     *
     * @param led
     * @param color
     */
    public void setColor(int led, Color color) {
        if (led < 0 || led >= colors.length) {
            throw new IllegalArgumentException("led must be between 0 and " + (colors.length - 1));
        }
        colors[led] = color;
    }

    /**
     * returns the color at the given position
     *
     * @param led
     * @return
     */
    public Color getColor(int led) {
        if (led < 0 || led >= colors.length) {
            throw new IllegalArgumentException("led must be between 0 and " + (colors.length - 1));
        }
        return colors[led];
    }

    /**
     * sets all colors of the stripe to the given one. Note that you
     * have to call update() to transmit the change to the hardware stripe.
     * @param color
     */
    public void setAllColors(Color color) {
        for (int i = 0; i < colors.length; ++i) {
            colors[i] = color;
        }
    }

    /**
     * returns the amount of leds in this stripe
     *
     * @return
     */
    public int getLeds() {
        return this.colors.length;
    }

    /**
     * sends the current set colors to the hardware RGB Stripe
     */
    public void update() {
        //32bit zeros = start frame
        shiftOut(0x00);
        shiftOut(0x00);

        for (Color color : colors) {
            sendColor(color);
        }

        //end led settings
        clkPin.setState(true);
        clkPin.setState(false);
    }

    private void sendColor(Color color) {
        int colorEncoded = 1 << 15
                         | ((color.getRed() >> 3) << 10)
                         | ((color.getBlue() >> 3) << 5)
                         | ((color.getGreen() >> 3) << 0);

        shiftOut(colorEncoded);
    }

    private void shiftOut(int data) {
        for (int i = 0; i < 16; ++i) {
            dataPin.setState((data >> (15 - i) & 0x01) > 0);
            clkPin.setState(true);
            clkPin.setState(false);
        }
    }

}
