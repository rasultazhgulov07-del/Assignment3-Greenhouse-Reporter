package kz.edu.greenhouse;

public class QuickPlantReport extends PlantReport {
    public QuickPlantReport(PlantDataSource source) {
        super(source);
    }

    @Override
    public String generate(PlantQuery query) throws PlantDataException {
        PlantReading reading = source.read(query);
        return String.format(
                "Quick report for %s: %.1f C, moisture %d%%",
                query.getPlantId(),
                reading.getTemperatureC(),
                reading.getMoisturePercent()
        );
    }
}
