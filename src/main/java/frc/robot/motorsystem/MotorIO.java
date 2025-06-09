package frc.robot.motorsystem;

import edu.wpi.first.units.measure.Angle;
import org.littletonrobotics.junction.AutoLog;

public interface MotorIO {
    void toPosition(double positionSet);
    void stop();
    void init();
    Angle getPosition();
    void periodic();


    @AutoLog
    class MotorInputs {
        public double position;
        public double torqueCurrent;
        public double velocity;
    }
}
