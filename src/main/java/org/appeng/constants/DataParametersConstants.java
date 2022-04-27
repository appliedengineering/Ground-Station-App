package org.appeng.constants;

public class DataParametersConstants {

    // determines whether a graph is displayed
    public static final boolean[] DATA_PROPERTIES_GRAPH_CONFIG = new boolean[]{
            true,
            true,
            true,
            true,
            // Values from the rpi (boat data)
            false,
            false,
            true
    };

    public static final String[] DATA_PROPERTIES_IDS = new String[]{
            "TP",
            "DP",
            "CP",
            "BV",
            // Values from the rpi (boat data)
            "posLat",
            "posLon",
            "speed"
    };

    public static final String[] DATA_PROPERTIES_LABELS = new String[]{
            "Throttle Percentage",
            "Duty Percentage",
            "Chip Temperature",
            "Battery Voltage",
            // Values from the rpi (boat data)
            "GPS Lat",
            "GPS Long",
            "Boat Speed"
    };

    public static final String[] BOOL_PROPERTIES_IDS = new String[] {
            "SM",
            "EM",
            "UV",
            "OV"
    };

    public static final String[] BOOL_PROPERTIES_LABELS = new String[]{
            "Solar Mode",
            "Motor Enabled",
            "Under Voltage Protection",
            "Over Voltage Protection"
    };

}
