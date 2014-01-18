/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Admin
 */
public class ToggleOneRelay extends CommandGroup {
    
    public ToggleOneRelay(int port) {
        // Add Commands here:
        addSequential(new SetRelay(port, Relay.Value.kOff, 1.0));
        addSequential(new SetRelay(port, Relay.Value.kForward, 1.0));
        addSequential(new SetRelay(port, Relay.Value.kOn, 1.0));
        addSequential(new WaitCommand(0.5));
        addSequential(new SetRelay(port, Relay.Value.kReverse, 1.0));
        addSequential(new WaitCommand(0.5));
        addSequential(new SetRelay(port, Relay.Value.kOff, 1.0));
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
