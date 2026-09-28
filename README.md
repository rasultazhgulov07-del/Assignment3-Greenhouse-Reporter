# Assignment 3 - Greenhouse Plant Reporter

A small Java project that combines **Bridge** and **Adapter** in one design.

## Domain
University greenhouse plant-condition reports.

## Pattern mapping

### Bridge
- Abstraction: `PlantReport`
- Refined Abstractions: `QuickPlantReport`, `DetailedPlantReport`
- Implementor: `PlantDataSource`
- Concrete Implementors: `LocalGreenhouseSource`, `RemoteGreenhouseSource`, `LegacyBotanySensorAdapter`

### Adapter
`LegacyBotanySensor` is incompatible with `PlantDataSource`:
- normal interface method: `read(PlantQuery)`
- legacy method: `poll(int metricMask, char[] plantCode)`
- normal result: `PlantReading`
- legacy result: packed `int`
- normal failure: `PlantDataException`
- legacy failure: negative integer error codes

`LegacyBotanySensorAdapter` converts the request, decodes the result, and translates every legacy error into `PlantDataException`.

## Complexity module
**Dynamic implementor selection.**

`App` reads the source key from user input. `SourceSelector` chooses the implementation at runtime. Implementations are discovered with Java `ServiceLoader`, so the selector does not contain a `switch` or `if` for concrete source classes.

## Requirements
- JDK 17 or newer
- Maven 3.8+

No Mockito is used. The tests use small handwritten stubs, so there is no Byte Buddy / Java 25 compatibility problem.

## Run tests

```bash
mvn clean test
```

## Build

```bash
mvn clean package
```

## Run the program

```bash
java -cp target/greenhouse-reporter-1.0.0.jar kz.edu.greenhouse.App
```

Try:
- plant id: `P-17`
- source: `legacy`
- report type: `detailed`

To see translated legacy failure, use plant id `OFF` with source `legacy`.

## Submission files
- source code: `src/main/java`
- tests: `src/test/java`
- UML: `docs/uml.png` and `docs/uml.svg`
- design rationale: `docs/design-rationale.md`
- report: `Assignment3_Report.docx`
