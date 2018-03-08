/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6977.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	// ALL FRC CLASSES http://first.wpi.edu/FRC/roborio/release/docs/java/
	
	// Port numbers
	private final int driveJoystickPort = 0;
	private final int mechanismJoystickPort = 1;
	private final int leftFrontMotorPort = 2;
	private final int leftBackMotorPort = 3;
	private final int rightFrontMotorPort = 0;
	private final int rightBackMotorPort = 1;
	private final int leftCollectorPort = 4;
	private final int rightCollectorPort = 5;
	private final int winchPort = 7;
    private final int basketPort = 6;	
	// Joystick threshold
	private static double threshold = 0.05;
	
	// Joystick
	private Joystick driveJoystick;
	private Joystick mechanismJoystick;
	
	// Motors
	private Spark leftFrontMotor;
	private Spark leftBackMotor;
	private Spark rightFrontMotor;
	private Spark rightBackMotor;
	private Spark leftCollectorMotor;
	private Spark rightCollectorMotor;
	private Spark winchMotor;
	private Spark basketMotor;
	// Miscellaneous Variables
	
	boolean startTimeToggle;
	
	int driveMode = 0;
	double startTime, currentTime;
	double duration;
	double left, right;
	
	// Ultrasonic Sensor
	/*private Ultrasonic myUltrasonic;
	private static int pingChannel = 0;
	private static int echoChannel = 1;*/
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Initialize Joysticks
		driveJoystick = new Joystick(driveJoystickPort);
		mechanismJoystick = new Joystick(mechanismJoystickPort);
		
		// Initialize Sensor
		//myUltrasonic = new Ultrasonic(pingChannel, echoChannel, Ultrasonic.Unit.kInches);
		
		// Initialize Motors
		leftFrontMotor = new Spark(leftFrontMotorPort);
		leftBackMotor = new Spark(leftBackMotorPort);
		rightFrontMotor = new Spark(rightFrontMotorPort);
		rightBackMotor = new Spark(rightBackMotorPort);
		leftCollectorMotor = new Spark(leftCollectorPort);
		rightCollectorMotor = new Spark(rightCollectorPort);
		winchMotor = new Spark(winchPort);
		basketMotor = new Spark(basketPort);
		
		rightFrontMotor.setInverted(true);
		rightBackMotor.setInverted(true);
	
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		//leftFrontMotor.set(0.5);
		//lightFrontMotor.set(0.5);
		//Timer.delay(3);
		//leftFrontMotor.set(0);
		//rightFrontMotor.set(0);
		startTimeToggle = true;
	}
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		
		if (startTimeToggle) {
			startTime = System.currentTimeMillis();
			startTimeToggle = false;
		}
		else {
			currentTime = System.currentTimeMillis();
		}
		
		duration = (currentTime - startTime) / 1000; //gives the seconds
		
		//if the duration is smaller than ___ seconds, run motors
		if (duration <= 3) {
			leftFrontMotor.set(0.5); //sets it to half speed
			leftBackMotor.set(0.5);
			
			rightFrontMotor.set(0.5);
			rightBackMotor.set(0.5);
		}
		
		else {
			leftFrontMotor.set(0); //stops motors
			leftBackMotor.set(0);
			
			rightFrontMotor.set(0);
			rightBackMotor.set(0);
		
		//If the duration is in between ___ and ___ seconds, lift basket
		if (duration > 3 && duration <= 6) {
			basketMotor.set(-0.55); //lift basket for 3 seconds
			
		}
		
		else if (duration > 6 && duration <= 8) {
			basketMotor.set(0.50); //bring down basket for 2 seconds
			
		}
		
		else {
			basketMotor.set(0);
		}
		
		
			
			
			
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		
		// Tank Drive
		left = -driveJoystick.getRawAxis(5) + driveJoystick.getRawAxis(0);
		right = -driveJoystick.getRawAxis(5) - driveJoystick.getRawAxis(0);
		
		//left = left * left * left;
		//right = right * right * right;
		
		if (driveJoystick.getRawButton(5)) { //L1
			driveMode = 1;
		}
		else if (driveJoystick.getRawButton(6)) {
			driveMode = 0;
		}
		
		if (driveMode == 1) {
			leftFrontMotor.set(left);
			leftBackMotor.set(left);
			rightFrontMotor.set(right);
			rightBackMotor.set(right);
		}
		
		else if (driveMode == 0) {
			leftFrontMotor.set(left * 0.7);
			leftBackMotor.set(left * 0.7);
			rightFrontMotor.set(right * 0.7);
			rightBackMotor.set(right * 0.7);
		}
		
		
		if (mechanismJoystick.getRawButton(5)) {
			// Winch Up
			winchMotor.setSpeed(1);
		}
		else if (mechanismJoystick.getRawButton(3)) {
			// Winch Down
			winchMotor.setSpeed(-1);
		}
		else {
			// Winch Idle
			winchMotor.setSpeed(0);
		}
		
		if (mechanismJoystick.getRawButton(11)) {
			// Collector In
			leftCollectorMotor.setSpeed(1);
			rightCollectorMotor.setSpeed(-1);
		}
		else if (mechanismJoystick.getRawButton(12)) {
			// Collector Out
			leftCollectorMotor.setSpeed(-1);
			rightCollectorMotor.setSpeed(1);
		}
		else {
			// Collector Idle
			leftCollectorMotor.setSpeed(0);
			rightCollectorMotor.setSpeed(0);
		}
		if (mechanismJoystick.getRawButton(3)) {
			//basket down
			basketMotor.setSpeed (0.50);
			
		}
		else if(mechanismJoystick.getRawButton(6)) {
			//basket up
			basketMotor.setSpeed(-0.55);
		}
		
		else {
			//basket Idle
			basketMotor.setSpeed(0);
		}
		
		/* 
		Example of using buttons
		if (testTalon.getRawButton(1)) {
		    yourMotor.setSpeed(1);
		}
		else if (testTalon.getRawButton(2)) {
		    yourMotor.setSpeed(-1);
		}
		else {
		    yourMotor.setSpeed(0);
		}*/
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
