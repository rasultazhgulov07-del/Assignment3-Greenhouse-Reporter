package kz.edu.greenhouse;

public abstract class PlantReport {
    protected final PlantDataSource source;

    protected PlantReport(PlantDataSource source) {
        if (source == null) {
            throw new IllegalArgumentException("Plant data source must not be null");
        }
        this.source = source;
    }

    public abstract String generate(PlantQuery query) throws PlantDataException;
}
