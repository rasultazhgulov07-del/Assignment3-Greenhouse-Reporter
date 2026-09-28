package kz.edu.greenhouse;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SourceSelector selector = SourceSelector.loadInstalled();

        System.out.println("University Greenhouse Plant Reporter");
        System.out.println("Available data sources: " + selector.availableKeys());

        System.out.print("Plant id: ");
        String plantId = scanner.nextLine().trim();

        System.out.print("Source (local / remote / legacy): ");
        String sourceKey = scanner.nextLine().trim();

        System.out.print("Report type (quick / detailed): ");
        String reportType = scanner.nextLine().trim().toLowerCase();

        try {
            PlantDataSource source = selector.select(sourceKey);
            PlantReport report = createReport(reportType, source);
            PlantQuery query = new PlantQuery(plantId, sourceKey);
            System.out.println();
            System.out.println(report.generate(query));
        } catch (PlantDataException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static PlantReport createReport(String reportType, PlantDataSource source) {
        if (reportType.equals("quick")) {
            return new QuickPlantReport(source);
        }
        if (reportType.equals("detailed")) {
            return new DetailedPlantReport(source);
        }
        throw new IllegalArgumentException("Unknown report type: " + reportType);
    }
}
