import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        String filePath = "cells.csv"; // Path to the CSV file

        HashMap<String, Cell> cellMap = readCellsFromCSV(filePath);
        System.out.println("Loaded " + cellMap.size() + " cell records.");

      String highestAvgWeightOem = Cell.oemWithHighestAverageWeight(cellMap);
      System.out.println("OEM with the highest average weight: " + highestAvgWeightOem);

      List<String> differentYearPhones = Cell.phonesWithDifferentAnnounceReleaseYears(cellMap);
      System.out.println("Phones announced one year and released in another:");
      differentYearPhones.forEach(System.out::println);

      int countOneSensor = Cell.countPhonesWithOneSensor(cellMap);
      System.out.println("Number of phones with only one feature sensor: " + countOneSensor);

      int mostLaunchesYear = Cell.yearWithMostLaunchesPost1999(cellMap);
      System.out.println("Year with the most phone launches after 1999: " + mostLaunchesYear);


        for (Cell cell : cellMap.values()) {
            System.out.println(cell);
        }



  }

    public static HashMap<String, Cell> readCellsFromCSV(String filePath) {
        HashMap<String, Cell> cells = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> fields = parseCSVLine(line);
                if (fields.size() < 12) continue; // Skip lines that do not have enough fields
                Cell cell = new Cell(fields.toArray(new String[0]));
                cells.put(fields.get(0) + fields.get(1), cell); // Using OEM + Model as a unique key
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cells;
    }

  private static List<String> parseCSVLine(String line) {
      List<String> fields = new ArrayList<>();
      StringBuilder fieldBuilder = new StringBuilder();
      boolean inQuotes = false;

      for (char c : line.toCharArray()) {
          if (c == '"') {
              inQuotes = !inQuotes; // Toggle state
              continue; // Skip adding quote marks to the field content
          }
          if (c == ',' && !inQuotes) {
              fields.add(fieldBuilder.toString().trim()); // Trim whitespace and add field
              fieldBuilder.setLength(0); // Reset the builder for the next field
          } else {
              fieldBuilder.append(c);
          }
      }
      fields.add(fieldBuilder.toString().trim()); // Add the last field
      return fields;
  }



}
