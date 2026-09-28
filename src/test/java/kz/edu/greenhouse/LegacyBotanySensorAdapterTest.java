package kz.edu.greenhouse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LegacyBotanySensorAdapterTest {
    @Test
    void convertsLegacyPackedResult() throws Exception {
        StubLegacySensor stub = new StubLegacySensor(226174);
        PlantDataSource adapter = new LegacyBotanySensorAdapter(stub);

        PlantReading reading = adapter.read(new PlantQuery("P-77", "legacy"));

        assertEquals(22.0, reading.getTemperatureC());
        assertEquals(61, reading.getMoisturePercent());
        assertEquals(74, reading.getLightPercent());
        assertEquals(LegacyBotanySensor.METRICS_ALL, stub.receivedMask);
        assertEquals("P-77", new String(stub.receivedPlantCode));
    }

    @Test
    void translatesLegacyOfflineError() {
        StubLegacySensor stub = new StubLegacySensor(LegacyBotanySensor.ERR_OFFLINE);
        PlantDataSource adapter = new LegacyBotanySensorAdapter(stub);

        PlantDataException error = assertThrows(
                PlantDataException.class,
                () -> adapter.read(new PlantQuery("P-77", "legacy"))
        );

        assertEquals("Legacy botany sensor is offline", error.getMessage());
    }

    @Test
    void translatesLegacyBadPlantError() {
        StubLegacySensor stub = new StubLegacySensor(LegacyBotanySensor.ERR_BAD_PLANT);
        PlantDataSource adapter = new LegacyBotanySensorAdapter(stub);

        PlantDataException error = assertThrows(
                PlantDataException.class,
                () -> adapter.read(new PlantQuery("X", "legacy"))
        );

        assertEquals("Legacy botany sensor rejected the plant id", error.getMessage());
    }

    private static class StubLegacySensor extends LegacyBotanySensor {
        private final int result;
        private int receivedMask;
        private char[] receivedPlantCode;

        private StubLegacySensor(int result) {
            this.result = result;
        }

        @Override
        public int poll(int metricMask, char[] plantCode) {
            receivedMask = metricMask;
            receivedPlantCode = plantCode.clone();
            return result;
        }
    }
}
