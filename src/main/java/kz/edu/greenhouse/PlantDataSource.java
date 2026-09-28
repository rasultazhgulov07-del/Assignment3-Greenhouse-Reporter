package kz.edu.greenhouse;

public interface PlantDataSource {
    String key();

    PlantReading read(PlantQuery query) throws PlantDataException;
}
