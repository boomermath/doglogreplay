package frc.robot;

import java.util.Iterator;

public class TimeIterator {
    private static int i = 0;

    public static long getTime() {
        return i++;
    }
}
