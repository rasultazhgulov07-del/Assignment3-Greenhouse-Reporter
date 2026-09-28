package kz.edu.greenhouse;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SourceSelectorTest {
    @Test
    void selectsImplementationFromRuntimeKey() throws Exception {
        PlantDataSource local = new LocalGreenhouseSource();
        PlantDataSource remote = new RemoteGreenhouseSource();
        SourceSelector selector = new SourceSelector(List.of(local, remote));

        assertSame(remote, selector.select("remote"));
        assertSame(local, selector.select("LOCAL"));
    }

    @Test
    void rejectsUnknownRuntimeKey() {
        SourceSelector selector = new SourceSelector(List.of(new LocalGreenhouseSource()));

        assertThrows(PlantDataException.class, () -> selector.select("missing"));
    }
}
