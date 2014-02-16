/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import Team102Lib.MessageLogger;

/**
 *
 * @author Admin
 */
public class DriveADistance extends CommandBase {

    public DriveADistance(double distanceToTravelInInches) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(motorWithEncoder);
        motorWithEncoder.setSetpoint(distanceToTravelInInches);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        motorWithEncoder.encoder.reset();
        motorWithEncoder.encoder.start();
        motorWithEncoder.enable();
        MessageLogger.LogMessage("PID Loop Enabled");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Math.abs(motorWithEncoder.getSetpoint() - motorWithEncoder.getPosition()) < 0.5) {
            return true;
        } else {
            return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
        motorWithEncoder.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
