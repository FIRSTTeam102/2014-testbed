/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

/**
 *
 * @author Admin
 */
public class LightALight extends CommandBase {
    
    boolean turnOn = false;
    int index = 0;
    public LightALight(int index, boolean turnOn, double timeout) {
        requires(lights);
        this.turnOn = turnOn;
        this.index = index;
        setTimeout(timeout); 
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        lights.toggleLight(index, turnOn);
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
        lights.toggleLight(index, !turnOn);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
