package frc.robot;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj.DriverStation;
import org.littletonrobotics.conduit.ConduitApi;

public class CustomDSLogger extends DogLog {
    public static void startLoggingDS() {
        ConduitApi.getInstance();
    }

    public static void refreshCaptureData() {
        ConduitApi.getInstance().captureData();
    }

    public static void log(String key) {
        ConduitApi conduit = ConduitApi.getInstance();
        key = key + "/";
        log(key + "AllianceStation", conduit.getAllianceStation());
        log(key + "EventName", conduit.getEventName().trim());
        log(key + "GameSpecificMessage", conduit.getGameSpecificMessage().trim());
        log(key + "MatchNumber", conduit.getMatchNumber());
        log(key + "ReplayNumber", conduit.getReplayNumber());
        log(key + "MatchType", conduit.getMatchType());
        log(key + "MatchTime", conduit.getMatchTime());

        int controlWord = conduit.getControlWord();
        log(key + "Enabled", (controlWord & 1) != 0);
        log(key + "Autonomous", (controlWord & 2) != 0);
        log(key + "Test", (controlWord & 4) != 0);
        log(key + "EmergencyStop", (controlWord & 8) != 0);
        log(key + "FMSAttached", (controlWord & 16) != 0);
        log(key + "DSAttached", (controlWord & 32) != 0);

        for (int id = 0; id < DriverStation.kJoystickPorts; id++) {
            String joystickTable = key + "Joystick" + id + "/";
            log(joystickTable + "Name", conduit.getJoystickName(id).trim());
            log(joystickTable + "Type", conduit.getJoystickType(id));
            log(joystickTable + "Xbox", conduit.isXbox(id));
            log(joystickTable + "ButtonCount", conduit.getButtonCount(id));
            log(joystickTable + "ButtonValues", conduit.getButtonValues(id));

            int povCount = conduit.getPovCount(id);
            int[] povValues = new int[povCount];
            System.arraycopy(conduit.getPovValues(id), 0, povValues, 0, povCount);
            log(joystickTable + "POVs", povValues);

            int axisCount = conduit.getAxisCount(id);
            float[] axisValues = new float[axisCount];
            int[] axisTypes = new int[axisCount];
            System.arraycopy(conduit.getAxisValues(id), 0, axisValues, 0, axisCount);
            System.arraycopy(conduit.getAxisTypes(id), 0, axisTypes, 0, axisCount);
            log(joystickTable + "AxisValues", axisValues);
            log(joystickTable + "AxisTypes", axisTypes);
        }
    }
}
