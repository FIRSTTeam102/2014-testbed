/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import Team102Lib.MathLib;
import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Admin
 */
public class MotorWithEncoder extends PIDSubsystem {

    private static final double Kp = 0.2;
    private static final double Ki = 0.0;
    private static final double Kd = 0.0;
    Talon motor;
    public Encoder encoder;
    

    // Initialize your subsystem here
    public MotorWithEncoder() {
        super("MotorWithEncoder", Kp, Ki, Kd);

        motor = new Talon(RobotMap.frontRightMotor);
        encoder = new Encoder(RobotMap.encoderAport,RobotMap.encoderBport);
        encoder.setDistancePerPulse(RobotMap.EncoderInchesPerPulse);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        MessageLogger.LogMessage("Encoder Distance: \t"
                + MathLib.round(encoder.getDistance(), 3)+ "\t" + MathLib.round(encoder.getRaw(), 3));
        return encoder.getDistance();
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
        MessageLogger.LogMessage("Motor Output: \t"
                + MathLib.round(output, 3));
        if(output > 1.0){
            output = 1.0;
        }
        if(output < -1.0){
            output = -1.0;
        }
        motor.set(output);
    }
}
