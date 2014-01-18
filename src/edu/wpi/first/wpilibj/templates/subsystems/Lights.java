/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 *
 * @author Admin
 */
public class Lights extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    DigitalOutput[] lights;

    public Lights() {
        lights = new DigitalOutput[8];

        for (int i = 0; i < 8; i++) {
            lights[i] = new DigitalOutput(i + 1);
            lights[i].disablePWM();
        }
    }

    public void initDefaultCommand() {

    }

    public void toggleLight(int port, boolean turnOn) {
        lights[port - 1].set(turnOn);
        MessageLogger.LogError("Setting light " + port + " to " + turnOn);
    }
}
