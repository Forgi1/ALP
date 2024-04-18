import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cell {
    // Attributes
    private String oem;
    private String model;
    private Integer launchAnnounced;
    private Float bodyWeight;
    private String launchStatus;
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
      this.launchAnnounced = fields.length > 2 ? parseYear(fields[2]) : null;
      this.launchStatus = fields.length > 3 ? fields[3] : null;
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

}
