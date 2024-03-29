/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Admin
 */
public class LightEachLight extends CommandGroup {
    
    public LightEachLight() {
        // Add Commands here:
        addSequential(new LightALight(1, true, 1.0));
        addSequential(new LightALight(2, true, 1.0));
        addSequential(new LightALight(3, true, 1.0));
        addSequential(new LightALight(4, true, 1.0));
        addSequential(new LightALight(5, true, 1.0));
        addSequential(new LightALight(6, true, 1.0));
        addSequential(new LightALight(7, true, 1.0));
        addSequential(new LightALight(8, true, 1.0));
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
