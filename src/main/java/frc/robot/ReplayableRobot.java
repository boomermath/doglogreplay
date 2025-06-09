package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.sim.PhysicsSim;
import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggedDriverStation;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import java.lang.reflect.Field;

public class ReplayableRobot extends LoggedRobot {
    private Command autonomousCommand;
    private RobotContainer robotContainer;

    public static LogTable entry = null;

    public ReplayableRobot() {
        if (!isSimulation()) {
            throw new IllegalArgumentException("Not a replayable robot");
        }

        setUseTiming(false);
        String logPath = LogFileUtil.findReplayLog(); // Pull the replay log from AdvantageScope (or prompt the user)
        String myPath = "C:\\Users\\Rikhil\\Projects\\doglogakit\\logs\\FRC_20250609_194931.wpilog";
        Logger.setReplaySource(new DogLogAkitReader(logPath)); // Read replay log
        Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_simreplay")));

        Logger.start();

        try {
            Field entryField = Logger.class.getDeclaredField("entry");
            if (!entryField.trySetAccessible()) throw new RuntimeException("failed to set accessible");

            entry = (LogTable) entryField.get(null);

            if (entry == null) {
                throw new RuntimeException("failed to set accessible");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {

        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        robotContainer = new RobotContainer();
        System.out.println(entry.getSubtable("Robot"));
    }

    /**
     * This method is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.

        LoggedDriverStation.replayFromLog(entry.getSubtable("Robot/DriverStation"));

        CommandScheduler.getInstance().run();
    }

    /**
     * This method is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
     * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        autonomousCommand = robotContainer.getAutonomousCommand();

        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    /**
     * This method is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    /**
     * This method is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This method is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    /**
     * This method is called once when the robot is first started up.
     */
    @Override
    public void simulationInit() {
    }

    /**
     * This method is called periodically whilst in simulation.
     */
    @Override
    public void simulationPeriodic() {
        PhysicsSim.getInstance().run();
    }
}