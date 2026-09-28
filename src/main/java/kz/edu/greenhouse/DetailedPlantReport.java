package kz.edu.greenhouse;

public class DetailedPlantReport extends PlantReport {
    public DetailedPlantReport(PlantDataSource source) {
        super(source);
    }

    @Override
    public String generate(PlantQuery query) throws PlantDataException {
        PlantReading reading = source.read(query);
        return String.format(
                "Detailed report for %s%nSource: %s%nTemperature: %.1f C%nMoisture: %d%%%nLight: %d%%",
                query.getPlantId(),
                source.key(),
                reading.getTemperatureC(),
                reading.getMoisturePercent(),
                reading.getLightPercent()
        );
    }
}
