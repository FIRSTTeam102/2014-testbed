/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import Team102Lib.MathLib;
import Team102Lib.MessageLogger;

/**
 *
 * @author Admin
 */
public class RunAllMotors extends CommandBase {

    double speed;

    public RunAllMotors(double speed) {
        requires(motors);
        this.speed = speed;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
        motors.runMotor(speed);
        MessageLogger.LogMessage("RunAllMotors(" + MathLib.round(speed, 2) + ") initialize");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        motors.runMotor(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        MessageLogger.LogMessage("RunAllMotors(" + MathLib.round(speed, 2) + ") Interrupted");
        end();
    }
}
