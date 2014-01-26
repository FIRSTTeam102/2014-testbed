/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import Team102Lib.MathLib;
import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.commands.GetGyroAngle;

/**
 *
 * @author Admin
 */
public class GyroSubsystem extends Subsystem {
    // Put methods for controlling this subsystem

    public Gyro gyro;
    public AnalogChannel aChannel;

    public GyroSubsystem() {
        gyro = new Gyro(1, 1);

        aChannel = new AnalogChannel(1, 2);
    }

    public void initialize()
    {
        gyro.reset();
        gyro.setSensitivity(0.007);
    
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new GetGyroAngle());
    }

    public double getAngle() {
        double angle = gyro.getAngle();
        double rate = gyro.getRate();
        double aiAvg = aChannel.getAverageValue();
        
        MessageLogger.LogMessage("Gyro angle, rate, aiAvg: \t"
                + MathLib.round(angle, 3)
                + "\t" + MathLib.round(rate, 3)
                + "\t" + MathLib.round(aiAvg, 3)
        );

        return angle;
    }
}
