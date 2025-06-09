package frc.robot.motorsystem;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import org.littletonrobotics.junction.Logger;

public class MotorIOReplayable implements MotorIO {
    private final MotorInputsAutoLogged motorInputs = new MotorInputsAutoLogged();

    private final PIDController motorPID = new PIDController(10, 0, 0);

    private double positionSetTo = 0;

    @Override
    public void toPosition(double positionSet) {
        positionSetTo = positionSet;
        // noop
    }


    @Override
    public void stop() {
        // noop
    }

    @Override
    public void init() {

    }

    @Override
    public Angle getPosition() {
        return Units.Rotations.of(motorInputs.position);
    }

    @Override
    public void periodic() {

        Logger.processInputs("Robot/" + SystemConfig.LOG_KEY, motorInputs);
        Logger.recordOutput("PositionReoutputted", motorInputs.position);
        Logger.recordOutput("PIDExampleOutput", motorPID.calculate(motorInputs.position, positionSetTo));
    }
}
