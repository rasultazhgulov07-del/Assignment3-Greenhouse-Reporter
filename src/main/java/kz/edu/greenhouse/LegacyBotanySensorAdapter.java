package kz.edu.greenhouse;

public class LegacyBotanySensorAdapter implements PlantDataSource {
    private final LegacyBotanySensor legacySensor;

    public LegacyBotanySensorAdapter() {
        this(new LegacyBotanySensor());
    }

    public LegacyBotanySensorAdapter(LegacyBotanySensor legacySensor) {
        if (legacySensor == null) {
            throw new IllegalArgumentException("Legacy sensor must not be null");
        }
        this.legacySensor = legacySensor;
    }

    @Override
    public String key() {
        return "legacy";
    }

    @Override
    public PlantReading read(PlantQuery query) throws PlantDataException {
        int result = legacySensor.poll(
                LegacyBotanySensor.METRICS_ALL,
                query.getPlantId().toCharArray()
        );

        if (result < 0) {
            throw translateFailure(result);
        }

        int temperature = result / 10000;
        int moisture = (result / 100) % 100;
        int light = result % 100;

        return new PlantReading(temperature, moisture, light);
    }

    private PlantDataException translateFailure(int errorCode) {
        if (errorCode == LegacyBotanySensor.ERR_OFFLINE) {
            return new PlantDataException("Legacy botany sensor is offline");
        }
        if (errorCode == LegacyBotanySensor.ERR_BAD_PLANT) {
            return new PlantDataException("Legacy botany sensor rejected the plant id");
        }
        return new PlantDataException("Legacy botany sensor failed");
    }
}
