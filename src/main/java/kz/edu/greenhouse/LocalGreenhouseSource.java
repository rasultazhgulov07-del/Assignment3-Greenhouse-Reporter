package kz.edu.greenhouse;

public class LocalGreenhouseSource implements PlantDataSource {
    @Override
    public String key() {
        return "local";
    }

    @Override
    public PlantReading read(PlantQuery query) {
        return new PlantReading(22.5, 62, 78);
    }
}
