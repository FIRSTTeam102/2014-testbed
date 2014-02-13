/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import Team102Lib.MathLib;
import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.PrintEncoder;

/**
 *
 * @author Admin
 */
public class EncoderSystem extends Subsystem
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    Encoder encoder;

    public EncoderSystem()
    {
        encoder = new Encoder(RobotMap.encoderAport, RobotMap.encoderBport);
        encoder.start();
    }

    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        setDefaultCommand(new PrintEncoder());
    }

    public void printValue()
    {
        MessageLogger.LogMessage("Encoder Distance: \t"
               + MathLib.round(encoder.getRaw(), 3));
    }
}
