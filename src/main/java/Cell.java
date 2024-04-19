import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Cell {
    // Attributes
    private String oem;
    private String model;
    private Integer launchAnnounced;
    private Float bodyWeight;
    private Integer launchStatus;
    private String bodyDimensions;
    private String bodySim;
    private String displayType;
    private Float displaySize;
    private String displayResolution;
    private String featuresSensors;
    private String platformOS;

    // Constructor
  public Cell(String[] fields) {
      this.oem = fields.length > 0 ? fields[0] : null;
      this.model = fields.length > 1 ? fields[1] : null;
      this.launchAnnounced = fields.length > 2 ? extractYear(fields[2]) : null;
      this.launchStatus = fields.length > 3 ? extractYear(fields[3]) : null;
      this.bodyDimensions = fields.length > 4 ? fields[4] : null;
      this.bodyWeight = fields.length > 5 ? parseWeight(fields[5]) : null;
      this.bodySim = fields.length > 6 ? fields[6] : null;
      this.displayType = fields.length > 7 ? fields[7] : null;
      this.displaySize = fields.length > 8 ? parseDisplaySize(fields[8]) : null;
      this.displayResolution = fields.length > 9 ? fields[9] : null;
      this.featuresSensors = fields.length > 10 ? fields[10] : null;
      this.platformOS = fields.length > 11 ? fields[11] : null;
  }



    // Parsing methods
    private Integer parseYear(String year) {
        if (year.matches("\\d{4}")) return Integer.parseInt(year);
        return null;
    }

  private Float parseWeight(String weight) {
      Pattern p = Pattern.compile("(\\d+(\\.\\d+)?)\\s*g");
      Matcher m = p.matcher(weight);
      if (m.find()) return Float.parseFloat(m.group(1));
      return null;
  }


    private Float parseDisplaySize(String size) {
        if (size == null || size.trim().isEmpty() || size.equals("-")) return null;
        Pattern p = Pattern.compile("(\\d+(\\.\\d+)?)\\s*inches");
        Matcher m = p.matcher(size);
        if (m.find()) return Float.parseFloat(m.group(1));
        return null;
    }

    private String parseOS(String os) {
        if (os.contains(",")) return os.substring(0, os.indexOf(','));
        return os;
    }
  private static Integer extractYear(String text) {
      Pattern pattern = Pattern.compile("(\\d{4})");
      Matcher matcher = pattern.matcher(text);
      if (matcher.find()) {
          return Integer.parseInt(matcher.group(1));
      }
      return null; // Or appropriate value if the year is not found
  }

    // Getters and other methods as needed for processing

    // Mainly for debugging purposes to view cell info
  @Override
  public String toString() {
      return String.format("OEM: %s, Model: %s, Launch Year: %s, Body Weight: %s g, Launch Status: %s, Body Dimensions: %s, SIM: %s, Display Type: %s, Display Size: %s inches, Display Resolution: %s, Sensors: %s, OS: %s",
          oem, // OEM name
          model, // Model name
          launchAnnounced != null ? launchAnnounced : "Unknown", // Launch year
          bodyWeight != null ? String.format("%.2f", bodyWeight) : "Unknown", // Body weight with two decimal places, or "Unknown" if null
          launchStatus != null ? launchStatus : "Unknown", // Launch status, or "Unknown" if null
          bodyDimensions != null ? bodyDimensions : "Unknown", // Body dimensions, or "Unknown" if null
          bodySim != null ? bodySim : "None", // SIM information, or "None" if null
          displayType != null ? displayType : "Unknown", // Type of display, or "Unknown" if null
          displaySize != null ? String.format("%.2f", displaySize) : "Unknown", // Display size with two decimal places, or "Unknown" if null
          displayResolution != null ? displayResolution : "Unknown", // Display resolution, or "Unknown" if null
          featuresSensors != null ? featuresSensors : "None", // Sensors, or "None" if null
          platformOS != null ? platformOS : "Unknown" // Operating system, or "Unknown" if null
      );
  }

  public String getOem() {
      return oem;
  }

  public String getModel() {
      return model;
  }

  public Integer getLaunchAnnounced() {
      return launchAnnounced;
  }

  public Float getBodyWeight() {
      return bodyWeight;
  }

  public Integer getLaunchStatus() {
      return launchStatus;
  }

  public String getBodyDimensions() {
      return bodyDimensions;
  }

  public String getBodySim() {
      return bodySim;
  }

  public String getDisplayType() {
      return displayType;
  }

  public Float getDisplaySize() {
      return displaySize;
  }

  public String getDisplayResolution() {
      return displayResolution;
  }

  public String getFeaturesSensors() {
      return featuresSensors;
  }

  public String getPlatformOS() {
      return platformOS;
  }


  // Assuming you add a new field for release year and modify the constructor accordingly

  public static String oemWithHighestAverageWeight(HashMap<String, Cell> cells) {
      HashMap<String, List<Float>> weights = new HashMap<>();
      for (Cell cell : cells.values()) {
          if (cell.getBodyWeight() != null) {
              weights.putIfAbsent(cell.getOem(), new ArrayList<>());
              weights.get(cell.getOem()).add(cell.getBodyWeight());
          }
      }

      String highestOem = null;
      float highestAvg = 0;
      for (Map.Entry<String, List<Float>> entry : weights.entrySet()) {
          float sum = 0;
          for (Float weight : entry.getValue()) {
              sum += weight;
          }
          float avg = sum / entry.getValue().size();
          if (avg > highestAvg) {
              highestAvg = avg;
              highestOem = entry.getKey();
          }
      }
      return highestOem;
  }


  public static List<String> phonesWithDifferentAnnounceReleaseYears(HashMap<String, Cell> cells) {
      List<String> results = new ArrayList<>();
      for (Cell cell : cells.values()) {
          if (cell.getLaunchAnnounced() != null && cell.getLaunchStatus() != null &&
              !cell.getLaunchAnnounced().equals(cell.getLaunchStatus())) {
              results.add(cell.getOem() + " " + cell.getModel());
          }
      }
      return results;
  }

  public static int countPhonesWithOneSensor(HashMap<String, Cell> cells) {
      int count = 0;
      for (Cell cell : cells.values()) {
          if (cell.getFeaturesSensors() != null && cell.getFeaturesSensors().split(",").length == 1) {
              count++;
          }
      }
      return count;
  }

  public static int yearWithMostLaunchesPost1999(HashMap<String, Cell> cells) {
      HashMap<Integer, Integer> yearCount = new HashMap<>();
      for (Cell cell : cells.values()) {
          if (cell.getLaunchAnnounced() != null && cell.getLaunchAnnounced() > 1999) {
              yearCount.put(cell.getLaunchAnnounced(), yearCount.getOrDefault(cell.getLaunchAnnounced(), 0) + 1);
          }
      }

      int maxYear = 0;
      int maxCount = 0;
      for (Map.Entry<Integer, Integer> entry : yearCount.entrySet()) {
          if (entry.getValue() > maxCount) {
              maxCount = entry.getValue();
              maxYear = entry.getKey();
          }
      }
      return maxYear;
  }


}
