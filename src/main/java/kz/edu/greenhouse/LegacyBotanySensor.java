package kz.edu.greenhouse;

public class LegacyBotanySensor {
    public static final int METRICS_ALL = 7;
    public static final int ERR_OFFLINE = -100;
    public static final int ERR_BAD_PLANT = -200;

    public int poll(int metricMask, char[] plantCode) {
        String code = new String(plantCode).trim();

        if (code.equalsIgnoreCase("OFF")) {
            return ERR_OFFLINE;
        }
        if (code.length() < 2) {
            return ERR_BAD_PLANT;
        }

        int temperature = 21;
        int moisture = 64;
        int light = 81;

        return temperature * 10000 + moisture * 100 + light;
    }
}
