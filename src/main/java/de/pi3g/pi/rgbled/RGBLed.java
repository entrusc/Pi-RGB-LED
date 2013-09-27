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
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.wiringpi.SoftPwm;
import java.awt.Color;

/**
 * This class drives a RGB LED using software PWM to dim each component according
 * to the given color. Most RGB LEDs are simply connected to one GPIO pin per color
 * channel.
 * 
 * @author Florian Frankenberger
 */
public class RGBLed {
    
    private final PinLayout pinLayout;
    private Color color = Color.BLACK;

    /**
     * constructs a new RGBLed using the given pinLayout to control the
     * LED
     * 
     * @param pinLayout a GPIO pinLayout
     */
    public RGBLed(PinLayout pinLayout) {
        this.pinLayout = pinLayout;
        
        final GpioController gpio = GpioFactory.getInstance();

        final GpioPinDigitalOutput ledRed = gpio.provisionDigitalOutputPin(pinLayout.getRedPin());
        final GpioPinDigitalOutput ledGreen = gpio.provisionDigitalOutputPin(pinLayout.getGreenPin());
        final GpioPinDigitalOutput ledBlue = gpio.provisionDigitalOutputPin(pinLayout.getBluePin());

        ledRed.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        ledGreen.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        ledBlue.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);

        SoftPwm.softPwmCreate(pinLayout.getRedPin().getAddress(), 0, 50);
        SoftPwm.softPwmCreate(pinLayout.getGreenPin().getAddress(), 0, 50);
        SoftPwm.softPwmCreate(pinLayout.getBluePin().getAddress(), 0, 50);
        
        off();
    }

    /**
     * sets the RGB LED to display the given color. Note that the color is only
     * an approximation.
     * 
     * @param color the color to display
     */
    public void displayColor(Color color) {
        final float[] colors = color.getRGBColorComponents(null);
        SoftPwm.softPwmWrite(pinLayout.getRedPin().getAddress(), (int) (colors[0] * 50f));
        SoftPwm.softPwmWrite(pinLayout.getGreenPin().getAddress(), (int) (colors[1] * 50f));
        SoftPwm.softPwmWrite(pinLayout.getBluePin().getAddress(), (int) (colors[2] * 50f));
        this.color = color;
    }
    
    /**
     * disables the RGB LED by cutting the power. Note that this is the same as displayColor(Color.BLACK).
     * It is also a good idea to call this before the application exits as otherwise the RGB LED will stay
     * in its last state even if the application terminates.
     */
    public final void off() {
        displayColor(Color.BLACK);
    }

    /**
     * returns the currently displayed color of the RGB LED.
     * 
     * @return the color displayed
     */
    public Color getDisplayedColor() {
        return color;
    }

}
