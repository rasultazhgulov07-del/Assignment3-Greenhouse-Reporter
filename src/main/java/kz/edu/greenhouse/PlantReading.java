package kz.edu.greenhouse;

public class PlantReading {
    private final double temperatureC;
    private final int moisturePercent;
    private final int lightPercent;

    public PlantReading(double temperatureC, int moisturePercent, int lightPercent) {
        this.temperatureC = temperatureC;
        this.moisturePercent = moisturePercent;
        this.lightPercent = lightPercent;
    }

    public double getTemperatureC() {
        return temperatureC;
    }

    public int getMoisturePercent() {
        return moisturePercent;
    }

    public int getLightPercent() {
        return lightPercent;
    }
}
