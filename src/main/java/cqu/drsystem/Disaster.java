package cqu.drsystem;

/**
 * Represents a disaster event in the Disaster Response System.
 * This class encapsulates the key information about a disaster.
 */
public class Disaster {
    private String type;
    private String location;
    private String severity;
    private String description;

      /**
     * Constructs a new Disaster object with the specified details.
     *
     * @param type The type of the disaster (e.g., "Earthquake", "Flood")
     * @param location The location where the disaster occurred
     * @param severity The severity level of the disaster
     * @param description A brief description of the disaster
     */
    public Disaster(String type, String location, String severity, String description) {
        this.type = type;
        this.location = location;
        this.severity = severity;
        this.description = description;
    }

       /**
     * Retrieves the type of the disaster.
     *
     * @return The type of the disaster
     */
    public String getType() {
        return type;
    }

     /**
     * Sets the type of the disaster.
     *
     * @param type The new type of the disaster
     */
    public void setType(String type) {
        this.type = type;
    }

     /**
     * Retrieves the location of the disaster.
     *
     * @return The location where the disaster occurred
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the disaster.
     *
     * @param location The new location of the disaster
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Retrieves the severity of the disaster.
     *
     * @return The severity level of the disaster
     */
    public String getSeverity() {
        return severity;
    }

     /**
     * Sets the severity of the disaster.
     *
     * @param severity The new severity level of the disaster
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

      /**
     * Retrieves the description of the disaster.
     *
     * @return A brief description of the disaster
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the disaster.
     *
     * @param description The new description of the disaster
     */
    public void setDescription(String description) {
        this.description = description;
    }

     /**
     * Returns a string representation of the Disaster object.
     *
     * @return A string containing all the details of the disaster
     */
    @Override
    public String toString() {
        return "Disaster [Type=" + type + ", Location=" + location + ", Severity=" + severity + ", Description=" + description + "]";
    }
}
