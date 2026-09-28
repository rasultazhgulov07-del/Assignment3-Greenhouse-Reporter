package kz.edu.greenhouse;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

public class SourceSelector {
    private final Map<String, PlantDataSource> sources = new LinkedHashMap<>();

    public SourceSelector(Collection<PlantDataSource> installedSources) {
        for (PlantDataSource source : installedSources) {
            sources.put(source.key().toLowerCase(), source);
        }
    }

    public static SourceSelector loadInstalled() {
        ServiceLoader<PlantDataSource> loader = ServiceLoader.load(PlantDataSource.class);
        return new SourceSelector(loader.stream().map(ServiceLoader.Provider::get).toList());
    }

    public PlantDataSource select(String key) throws PlantDataException {
        if (key == null) {
            throw new PlantDataException("Source key is required");
        }

        PlantDataSource source = sources.get(key.trim().toLowerCase());
        if (source == null) {
            throw new PlantDataException("Unknown source: " + key);
        }
        return source;
    }

    public Set<String> availableKeys() {
        return Set.copyOf(sources.keySet());
    }
}
