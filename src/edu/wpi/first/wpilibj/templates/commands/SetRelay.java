/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author Admin
 */
public class SetRelay extends CommandBase {
    
    int index;
    Relay.Value val;
    public SetRelay(int index, Relay.Value val, double timeout) {
        // Use requires() here to declare subsystem dependencies
        requires(relays);
        this.index = index;
        this.val = val;
        setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        relays.SetRelay(index, val);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        // Leave the relay in the set state.
//        Relay.Value newVal;
//        if(val == Relay.Value.kForward)
//            newVal = Relay.Value.kReverse;
//        else if(val == Relay.Value.kReverse)
//            newVal = Relay.Value.kForward;
//        else if(val == Relay.Value.kOn)
//            newVal = Relay.Value.kOff;
//        else if(val == Relay.Value.kOff)
//            newVal = Relay.Value.kOn;
//        else
//            newVal = Relay.Value.kOff;
//            
//        relays.SetRelay(index, newVal);
//        relays.SetRelay(index, Relay.Value.kOff);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
