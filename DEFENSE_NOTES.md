# Defense notes - simple version

## 30-second explanation
My project is a greenhouse plant reporter. I have two report types: Quick and Detailed. I also have three data sources: Local, Remote, and Legacy. Bridge separates report types from data sources, so I do not need classes like QuickLocal, QuickRemote, DetailedLocal, and DetailedRemote. Adapter is needed because the legacy sensor has a different method, different parameters, a packed integer result, and integer error codes.

## Important classes
- `PlantReport` - Bridge Abstraction
- `QuickPlantReport`, `DetailedPlantReport` - Refined Abstractions
- `PlantDataSource` - Bridge Implementor
- `LocalGreenhouseSource`, `RemoteGreenhouseSource` - normal implementations
- `LegacyBotanySensor` - old incompatible class
- `LegacyBotanySensorAdapter` - Adapter
- `SourceSelector` - runtime implementor selection

## Questions teacher may ask

**Why Bridge?**  
To separate report types from data sources so both can change independently.

**Why Adapter?**  
The old sensor cannot implement the new interface directly. Its method, parameters, return format, and failure mechanism are different.

**Why not only Adapter?**  
Adapter solves compatibility only. It does not solve the report-type x data-source combinations.

**What is the bridge?**  
The `PlantDataSource` reference stored inside `PlantReport`.

**Where is composition?**  
`PlantReport` contains a `PlantDataSource`. `LegacyBotanySensorAdapter` contains a `LegacyBotanySensor`.

**How is failure translated?**  
Negative legacy codes are converted to `PlantDataException` inside the adapter.

**What is dynamic selection?**  
The source name comes from user input, and `SourceSelector` chooses the implementation at runtime.

**How do tests satisfy the requirement?**  
Two report tests use a stub implementor to check delegation. Adapter tests use a stub legacy sensor to check decoding and failure translation.
