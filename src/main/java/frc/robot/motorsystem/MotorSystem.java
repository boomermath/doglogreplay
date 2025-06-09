package frc.robot.motorsystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Main;

public class MotorSystem extends SubsystemBase {
    MotorIO motorIO;

    public MotorSystem() {
        motorIO = !Main.isReplay ? new MotorIOTalonFX() : new MotorIOReplayable();

        motorIO.init();
    }


    @Override
    public void periodic() {
        motorIO.periodic();
    }

    public Command moveAtPosition(double position) {
        return runOnce(() -> motorIO.toPosition(position));
    }

    public Command stop() {
        return runOnce(motorIO::stop);
    }
}
