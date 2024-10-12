// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import preponderous.viron.config.ServiceConfig;
import preponderous.viron.models.Location;

@Service
public class LocationService {
    private final RestTemplateBuilder restTemplateBuilder;
    private final ServiceConfig serviceConfig;

    private final String baseUrl;
    
    @Autowired
    public LocationService(RestTemplateBuilder restTemplateBuilder, ServiceConfig serviceConfig) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.serviceConfig = serviceConfig;

        this.baseUrl = this.serviceConfig.getVironHost() + ":" + serviceConfig.getVironPort() + "/location";
    }

    public List<Location> getAllLocations() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Location[]> response = restTemplate.exchange(baseUrl + "/get-all-locations", HttpMethod.GET, null, Location[].class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting locations");
        }
        return Arrays.asList(response.getBody());
    }

    public Location getLocationById(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Location> response = restTemplate.exchange(baseUrl + "/get-location-by-id/" + id, HttpMethod.GET, null, Location.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting location by id");
        }
        return response.getBody();
    }

    public List<Location> getLocationsInEnvironment(int environmentId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Location[]> response = restTemplate.exchange(baseUrl + "/get-locations-in-environment/" + environmentId, HttpMethod.GET, null, Location[].class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting locations in environment");
        }
        return Arrays.asList(response.getBody());
    }

    public List<Location> getLocationsInGrid(int gridId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Location[]> response = restTemplate.exchange(baseUrl + "/get-locations-in-grid/" + gridId, HttpMethod.GET, null, Location[].class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting locations in grid");
        }
        return Arrays.asList(response.getBody());
    }

    public Location getLocationOfEntity(int entityId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Location> response = restTemplate.exchange(baseUrl + "/get-location-of-entity/" + entityId, HttpMethod.GET, null, Location.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting location of entity");
        }
        return response.getBody();
    }

    public boolean addEntityToLocation(int entityId, int locationId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Boolean> response = restTemplate.exchange(baseUrl + "/add-entity-to-location/" + entityId + "/" + locationId, HttpMethod.POST, null, Boolean.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error adding entity to location");
        }
        return response.getBody();
    }

    public boolean removeEntityFromLocation(int entityId, int locationId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Boolean> response = restTemplate.exchange(baseUrl + "/remove-entity-from-location/" + entityId + "/" + locationId, HttpMethod.POST, null, Boolean.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error removing entity from location");
        }
        return response.getBody();
    }

    public boolean removeEntityFromCurrentLocation(int entityId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Boolean> response = restTemplate.exchange(baseUrl + "/remove-entity-from-current-location/" + entityId, HttpMethod.POST, null, Boolean.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error removing entity from current location");
        }
        return response.getBody();
    }

    // testing
    public static void main(String[] args) {
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setVironHost("http://localhost");
        serviceConfig.setVironPort(8080);
        LocationService locationService = new LocationService(new RestTemplateBuilder(), serviceConfig);
        List<Location> locations = locationService.getLocationsInEnvironment(1);
        for (Location location : locations) {
            System.out.println(location);
        }
    }
}
