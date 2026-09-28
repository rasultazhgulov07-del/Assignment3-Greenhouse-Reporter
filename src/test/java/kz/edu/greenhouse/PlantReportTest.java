package kz.edu.greenhouse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlantReportTest {
    @Test
    void quickReportDelegatesToImplementor() throws Exception {
        RecordingSource stub = new RecordingSource(new PlantReading(24.0, 55, 70));
        PlantQuery query = new PlantQuery("P-10", "stub");
        PlantReport report = new QuickPlantReport(stub);

        String result = report.generate(query);

        assertEquals(1, stub.calls);
        assertSame(query, stub.lastQuery);
        assertTrue(result.contains("24.0 C"));
        assertTrue(result.contains("55%"));
    }

    @Test
    void detailedReportDelegatesToImplementor() throws Exception {
        RecordingSource stub = new RecordingSource(new PlantReading(19.5, 68, 83));
        PlantQuery query = new PlantQuery("P-22", "stub");
        PlantReport report = new DetailedPlantReport(stub);

        String result = report.generate(query);

        assertEquals(1, stub.calls);
        assertSame(query, stub.lastQuery);
        assertTrue(result.contains("19.5 C"));
        assertTrue(result.contains("68%"));
        assertTrue(result.contains("83%"));
    }

    private static class RecordingSource implements PlantDataSource {
        private final PlantReading reading;
        private int calls;
        private PlantQuery lastQuery;

        private RecordingSource(PlantReading reading) {
            this.reading = reading;
        }

        @Override
        public String key() {
            return "stub";
        }

        @Override
        public PlantReading read(PlantQuery query) {
            calls++;
            lastQuery = query;
            return reading;
        }
    }
}
