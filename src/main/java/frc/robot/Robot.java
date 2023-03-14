package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.GenericHID;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.BasePigeon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  WPI_TalonFX m_leftF = new WPI_TalonFX(4);
  WPI_TalonFX m_leftB = new WPI_TalonFX(2);
  MotorControllerGroup left = new MotorControllerGroup(m_leftF, m_leftB);

  WPI_TalonFX m_rightF = new WPI_TalonFX(5);
  WPI_TalonFX m_rightB = new WPI_TalonFX(3);
  MotorControllerGroup right = new MotorControllerGroup(m_rightF, m_rightB);

  DifferentialDrive tankDrive = new DifferentialDrive(left, right);

  BasePigeon titltSensor = new BasePigeon(4,null);

  double pitch = 0;
  double yaw = 0;

  public double getPitch() {
    return pitch;
  }

  public double getYaw() {
    return yaw;
  }

  Joystick leftStick = new Joystick(0);
  Joystick rightStick = new Joystick(1);
  GenericHID mechPad = new GenericHID(2);

  Timer timer = new Timer();
  int kSlotIdx = 0;
  int kPIDLoopIdx = 0;
  int kTimeoutMs = 10;


  private static final String kDefaultAuto = "Default Auto";
  private static final String kAutoCenterField = "Auto Center Field";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("Auto Center Field", kAutoCenterField);
    SmartDashboard.putData("Auto choices", m_chooser);

    right.setInverted(false);
    left.setInverted(true);
    CameraServer.startAutomaticCapture();



  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    timer.reset();
    timer.start();

  }


  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() { //autonomous = 15
    switch (m_autoSelected) {
      case kAutoCenterField:
        // Put custom auto code here
        break;
      case kDefaultAuto:

   if(Timer.getMatchTime() > 14.5){
          
      tankDrive.tankDrive(-0.76, -0.7);

   }else if(Timer.getMatchTime() > 13){

    if(pitch < 0 && yaw == 0){

    tankDrive.tankDrive(-0.66, -0.6);

    }else if(pitch > 0 && yaw == 0){

    tankDrive.tankDrive(0.66, 0.6);

    }

    }else{

      tankDrive.tankDrive(0, 0);

    }

    break;
  }

  }
        // Put default auto code here
      /*if(Timer.getMatchTime() > 13.0 && Timer.getMatchTime() < 15.0){
        tankDrive.tankDrive(-0.76,-0.7);
      }else if(Timer.getMatchTime() > 11.0 && Timer.getMatchTime() < 13.0){
        tankDrive.tankDrive(-0.76,0.7);
      }else if(Timer.getMatchTime() > 9.0 && Timer.getMatchTime() < 11.0){
        tankDrive.tankDrive(-0.76,-0.7);
      }else {tankDrive.tankDrive(0,0);}*/
    
    
    /*if(Timer.getMatchTime() > 13.0 && Timer.getMatchTime() < 15.0){
          
      tankDrive.tankDrive(-0.76, -0.7);

    }else if(Timer.getMatchTime() > 10.0 && Timer.getMatchTime() < 13.0){

      tankDrive.tankDrive(0.76, 0.7);

    }else{

      tankDrive.tankDrive(0, 0);

    }*/
    
  

  /* This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator+++ control. */
  @Override
  public void teleopPeriodic() {

      tankDrive.tankDrive(mechPad.getRawAxis(1), mechPad.getRawAxis(3));

      double timeNow = Timer.getMatchTime();

      SmartDashboard.putNumber("Match Time Now", timeNow);



  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}

}
