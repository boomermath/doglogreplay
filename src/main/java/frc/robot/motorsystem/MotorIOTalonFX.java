package frc.robot.motorsystem;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import frc.robot.sim.PhysicsSim;

public class MotorIOTalonFX implements MotorIO {
    TalonFX motor = new TalonFX(0);

    StatusSignal<Angle> positionSignal = motor.getPosition();
    StatusSignal<AngularVelocity> velocitySignal = motor.getVelocity();
    StatusSignal<Current> motorAmpSignal = motor.getTorqueCurrent();

    DogLogTalonFXLogger logger = new DogLogTalonFXLogger(SystemConfig.LOG_KEY, motor);
    PositionDutyCycle pos = new PositionDutyCycle(0).withSlot(0);

    @Override
    public void init() {
        motor.getConfigurator().apply(new Slot0Configs().withKP(0.1).withKV(0.7));
        BaseStatusSignal.setUpdateFrequencyForAll(250.0, positionSignal, velocitySignal, motorAmpSignal);
        PhysicsSim.getInstance().addTalonFX(motor);
    }

    @Override
    public void toPosition(double positionSet) {
        motor.setControl(pos.withPosition(positionSet));
    }

    @Override
    public Angle getPosition() {
        return positionSignal.getValue();
    }

    @Override
    public void periodic() {
        BaseStatusSignal.refreshAll(positionSignal, velocitySignal, motorAmpSignal);

        logger.doLog();
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }
}
