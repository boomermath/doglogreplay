package frc.robot.sim;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import java.util.ArrayList;

public class PhysicsSim {
    private static final PhysicsSim sim = new PhysicsSim();
    private final ArrayList<SimProfile> simProfiles = new ArrayList<>();

    /** Gets the robot simulator instance. */
    public static PhysicsSim getInstance() {
        return sim;
    }

    /**
     * Adds a TalonFX controller to the simulator.
     *
     * @param talonFX The TalonFX device
     */
    public TalonFXSimProfile addTalonFX(TalonFX talonFX) {
        TalonFXSimProfile simTalonFX = new TalonFXSimProfile(talonFX, 0.001);
        simProfiles.add(simTalonFX);
        return simTalonFX;
    }

    public TalonFXSimProfile addTalonFX(TalonFX talonFX, CANcoder cancoder) {
        TalonFXSimProfile simTalonFX = new TalonFXSimProfile(talonFX, 0.001, cancoder);
        simProfiles.add(simTalonFX);
        return simTalonFX;
    }

    /** Runs the simulator: - enable the robot - simulate sensors */
    public void run() {
        // Simulate devices
        for (SimProfile simProfile : simProfiles) {
            simProfile.run();
        }
    }

    /** Holds information about a simulated device. */
    static class SimProfile {
        private double _lastTime;
        private boolean _running = false;

        /** Runs the simulation profile. Implemented by device-specific profiles. */
        protected void run() {}

        /** Returns the time since last call, in seconds. */
        protected double getPeriod() {
            // set the start time if not yet running
            if (!_running) {
                _lastTime = Utils.getCurrentTimeSeconds();
                _running = true;
            }

            double now = Utils.getCurrentTimeSeconds();
            final double period = now - _lastTime;
            _lastTime = now;

            return period;
        }
    }
}
