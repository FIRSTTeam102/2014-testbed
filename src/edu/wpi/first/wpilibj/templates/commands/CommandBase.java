package edu.wpi.first.wpilibj.templates.commands;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.subsystems.Chassis;
import edu.wpi.first.wpilibj.templates.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.templates.subsystems.GyroSubsystem;
import edu.wpi.first.wpilibj.templates.subsystems.Lights;
import edu.wpi.first.wpilibj.templates.subsystems.MotorWithEncoder;
import edu.wpi.first.wpilibj.templates.subsystems.Motors;
import edu.wpi.first.wpilibj.templates.subsystems.Relays;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Chassis chassis;
    public static MotorWithEncoder motorWithEncoder;
    public static Motors motors;
    public static Lights lights;
    public static Relays relays;
    public static GyroSubsystem gyro;
    
    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        try {
            // SUBSYSTEMS need to be created before OI!!!
           motors = new Motors();
            lights = new Lights();
            relays = new Relays();
            gyro = new GyroSubsystem();
 //           motorWithEncoder = new MotorWithEncoder();

            oi = new OI();
//            chassis = new Chassis();

            // Show what command your subsystem is running on the SmartDashboard
            //        SmartDashboard.putData(motors);
        } catch (Exception e) {
            MessageLogger.LogError("Unhandled Exception in CommandBase.init()");
            MessageLogger.LogError(e.toString());
            e.printStackTrace();
        }

    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
