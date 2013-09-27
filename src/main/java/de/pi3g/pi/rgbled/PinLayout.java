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

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Defines a pin layout for the connected RGB LED
 * 
 * @author Florian Frankenberger
 */
public class PinLayout {

    /**
     * the pin layout for Piborg's LedBorg
     * http://www.piborg.com/ledborg
     */
    public static PinLayout PIBORG_LEDBORG = new PinLayout(RaspiPin.GPIO_00, RaspiPin.GPIO_02, RaspiPin.GPIO_03);
    
    private final Pin redPin;
    private final Pin greenPin;
    private final Pin bluePin;

    /**
     * creates a pin layout for a RGB LED.
     * 
     * @param redPin the GPIO pin the red LED is connected to
     * @param greenPin the GPIO pin the green LED is connected to
     * @param bluePin the GPIO pin the blue LED is connected to 
     */
    public PinLayout(Pin redPin, Pin greenPin, Pin bluePin) {
        this.redPin = redPin;
        this.greenPin = greenPin;
        this.bluePin = bluePin;
    }

    public Pin getRedPin() {
        return redPin;
    }

    public Pin getGreenPin() {
        return greenPin;
    }

    public Pin getBluePin() {
        return bluePin;
    }
    
}
