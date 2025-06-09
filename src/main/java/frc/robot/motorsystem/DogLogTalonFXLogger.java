package frc.robot.motorsystem;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import dev.doglog.DogLog;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

public class DogLogTalonFXLogger extends DogLog {

    private final StatusSignal<Angle> positionSignal;
    private final StatusSignal<AngularVelocity> velocitySignal;
    private final StatusSignal<Current> motorAmpsSignal;

    private final String key;
    private final int deviceID;

    public DogLogTalonFXLogger(String key, TalonFX talon) {
        this.key = key;
        deviceID = talon.getDeviceID();
        positionSignal = talon.getPosition();
        velocitySignal = talon.getVelocity();
        motorAmpsSignal = talon.getTorqueCurrent();

        BaseStatusSignal.setUpdateFrequencyForAll(250.0, positionSignal, velocitySignal, motorAmpsSignal);
    }

    public void doLog() {
        BaseStatusSignal.refreshAll(positionSignal, velocitySignal, motorAmpsSignal);

        log(key + "/Position", positionSignal.getValueAsDouble());
        log(key + "/Velocity", velocitySignal.getValueAsDouble());
        log(key + "/TorqueCurrent", motorAmpsSignal.getValueAsDouble());
    }
}
