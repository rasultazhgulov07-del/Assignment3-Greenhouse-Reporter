package kz.edu.greenhouse;

public class PlantQuery {
    private final String plantId;
    private final String sourceKey;

    public PlantQuery(String plantId, String sourceKey) {
        if (plantId == null || plantId.isBlank()) {
            throw new IllegalArgumentException("Plant id must not be empty");
        }
        if (sourceKey == null || sourceKey.isBlank()) {
            throw new IllegalArgumentException("Source key must not be empty");
        }
        this.plantId = plantId.trim();
        this.sourceKey = sourceKey.trim().toLowerCase();
    }

    public String getPlantId() {
        return plantId;
    }

    public String getSourceKey() {
        return sourceKey;
    }
}
