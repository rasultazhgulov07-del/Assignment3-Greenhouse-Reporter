package kz.edu.greenhouse;

public class RemoteGreenhouseSource implements PlantDataSource {
    @Override
    public String key() {
        return "remote";
    }

    @Override
    public PlantReading read(PlantQuery query) throws PlantDataException {
        if (query.getPlantId().equalsIgnoreCase("NETWORK-OFF")) {
            throw new PlantDataException("Remote greenhouse service is unavailable");
        }
        return new PlantReading(23.0, 58, 72);
    }
}
