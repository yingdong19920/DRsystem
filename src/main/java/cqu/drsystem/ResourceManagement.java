package cqu.drsystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages resources in the Disaster Response System.
 */
public class ResourceManagement {

    private final List<Resource> resources = new ArrayList<>();

    /**
     * Adds a new resource to the system.
     * 
     * @param resource The resource to add
     */
    public void addResource(Resource resource) {
        resources.add(resource);
    }

    /**
     * Retrieves a resource by its name.
     * 
     * @param name The name of the resource
     * @return The resource if available, or null if not found
     */
    public Resource getResourceByName(String name) {
        for (Resource resource : resources) {
            if (resource.getName().equalsIgnoreCase(name) && "available".equalsIgnoreCase(resource.getStatus())) {
                return resource;
            }
        }
        return null;  // If no available resource is found
    }

    /**
     * Allocates a resource to a disaster.
     * 
     * @param disaster The disaster
     * @param resource The resource to allocate
     */
    public void allocateResource(Disaster disaster, Resource resource) {
        if ("available".equalsIgnoreCase(resource.getStatus())) {
            resource.setStatus("allocated to " + disaster.getType());
            System.out.println("Resource allocated: " + resource.getName() + " to disaster " + disaster.getType());
        } else {
            System.out.println("Resource " + resource.getName() + " is not available.");
        }
    }

    /**
     * Updates the status of a resource.
     * 
     * @param resource The resource to update
     * @param status The new status of the resource
     */
    public void updateResourceStatus(Resource resource, String status) {
        resource.setStatus(status);
    }

    /**
     * Lists all resources in the system.
     * @return 
     */
    public List<Resource> getResources() {
        return new ArrayList<>(resources);
    }
}
