package cqu.drsystem;

/**
 * The Resource class represents a resource that can be allocated during disaster response.
 * It contains information such as resource name, type, status, available quantity, and allocated quantity.
 */
public class Resource {
    private String id;
    private String name;
    private String type;
    private String status;
    private int availableQuantity;  // Available quantity of the resource
    private int allocatedQuantity;  // Quantity allocated to a disaster

    /**
     * Constructs a new Resource with the specified details.
     *
     * @param id the unique identifier of the resource
     * @param name the name of the resource
     * @param type the type of resource (e.g., Vehicle, Personnel)
     * @param status the current status of the resource (e.g., available, unavailable)
     * @param availableQuantity the number of available resources
     */
    public Resource(String id, String name, String type, String status, int availableQuantity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.availableQuantity = availableQuantity;
        this.allocatedQuantity = 0;  // Initial allocated quantity is set to 0
    }

    /**
     * Gets the name of the resource.
     *
     * @return the name of the resource
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the resource.
     *
     * @return the type of the resource (e.g., Vehicle, Personnel)
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the current status of the resource.
     *
     * @return the status of the resource
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the resource.
     *
     * @param status the new status of the resource
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the available quantity of the resource.
     *
     * @return the available quantity
     */
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     * Sets the available quantity of the resource.
     *
     * @param availableQuantity the new available quantity
     */
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    /**
     * Gets the quantity of the resource that has been allocated to a disaster.
     *
     * @return the allocated quantity
     */
    public int getAllocatedQuantity() {
        return allocatedQuantity;
    }

    /**
     * Sets the quantity of the resource that has been allocated to a disaster.
     *
     * @param allocatedQuantity the new allocated quantity
     */
    public void setAllocatedQuantity(int allocatedQuantity) {
        this.allocatedQuantity = allocatedQuantity;
    }

    /**
     * Returns a string representation of the resource, showing its name, type,
     * allocated quantity, and available quantity.
     *
     * @return a string describing the resource
     */
    @Override
    public String toString() {
        return name + " (" + type + ") - Allocated: " + allocatedQuantity + " - Available: " + availableQuantity;
    }
}
