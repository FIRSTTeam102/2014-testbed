/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import Team102Lib.MathLib;
import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;


/**
 *
 * @author Admin
 */
public class Motors extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
        SpeedController[] speedcontrollers;
      
    public Motors(){
        speedcontrollers = new SpeedController[9];
        speedcontrollers[0] = new Victor(1);
        speedcontrollers[1] = new Victor(2);
        speedcontrollers[2] = new Talon(3);
        speedcontrollers[3] = new Victor(4);
        speedcontrollers[4] = new Victor(5);
//        speedcontrollers[5] = new Talon(6);
        speedcontrollers[6] = new Victor(7);
        speedcontrollers[7] = new Victor(8);
        speedcontrollers[8] = new Jaguar(9);
    }
    
    

    public void initDefaultCommand() {
        
    }
   
    public void runMotor(int port, double speed){
        speedcontrollers[port - 1].set(speed);
        MessageLogger.LogMessage("Running motor " + port + " at " + MathLib.round(speed, 3));
    }
}
