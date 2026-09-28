# Design Rationale - Greenhouse Plant Reporter

## Problem
The system creates plant-condition reports for a university greenhouse. The same report style may obtain data from different sources, and the same source may be used by different report styles. The project currently has two report variants (Quick and Detailed) and three data-source implementations (Local, Remote, and Legacy).

## Why Bridge is used
Without Bridge, report type and data source can become one combined inheritance hierarchy, for example `QuickLocalReport`, `QuickRemoteReport`, `DetailedLocalReport`, and so on. This grows as both dimensions grow. Bridge separates them: `PlantReport` is the abstraction hierarchy and `PlantDataSource` is the implementation hierarchy. A report stores only a `PlantDataSource` reference, so both sides can vary independently.

## Why Adapter is also required
Bridge alone does not make the old `LegacyBotanySensor` compatible with `PlantDataSource`. The legacy class has a different method and signature: `poll(int metricMask, char[] plantCode)`. It returns one packed integer instead of a `PlantReading`, and failures are negative integer error codes instead of `PlantDataException`.

`LegacyBotanySensorAdapter` wraps the unchanged legacy class. It converts `PlantQuery` into the legacy parameters, decodes the packed result, and translates every legacy failure into `PlantDataException`. No report class references the legacy class, its constants, or its error codes.

## Why Adapter alone is not enough
Adapter can make the legacy sensor look like a `PlantDataSource`, but it does not separate report variants from source variants. Without Bridge, new report types and new source types would still create repeated combinations.

## Required complexity module
Chosen module: **Dynamic implementor selection**.

The source key is read from runtime input. `SourceSelector` selects the matching `PlantDataSource`. Java `ServiceLoader` discovers the installed implementations from configuration, so the client does not hard-code a concrete implementation choice with a source-specific branch.

## Open/Closed Principle
A new report variant can extend `PlantReport` without modifying existing source classes. A new source can implement `PlantDataSource` and be registered in the service configuration without changing existing report classes or `SourceSelector`. The two axes therefore remain independently extensible.

## Tests
JUnit 5 tests use handwritten stubs. `PlantReportTest` verifies normal delegation for both refined abstractions. `LegacyBotanySensorAdapterTest` verifies request conversion, packed-result decoding, and translation of legacy error codes. `SourceSelectorTest` verifies runtime selection.

## Limitation
The legacy packed integer format assumes bounded numeric values and is intentionally simple. A real sensor protocol would need stronger validation, versioning, and possibly checksums.
