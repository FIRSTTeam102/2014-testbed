/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Admin
 */
public class Relays extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    Relay[] relays;

    public Relays() {
        relays = new Relay[2];
        for (int i = 0; i < 2; i++) {
            relays[i] = new Relay(i + 1, Relay.Direction.kBoth);
        }
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void SetRelay(int port, Relay.Value val)
    {
        relays[port - 1].set(val);
    }
}
