// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import preponderous.viron.models.Location;

@Service
public class LocationService {
    private RestTemplateBuilder restTemplateBuilder;
    private String baseUrl = "http://localhost:9999/location"; // TODO: pull from config
    
    @Autowired
    public LocationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public List<Location> getAllLocations() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Location[]> response = restTemplate.exchange(baseUrl + "/get-all-locations", HttpMethod.GET, null, Location[].class);
        return Arrays.asList(response.getBody());
    }

    public Location getLocationById(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Location> response = restTemplate.exchange(baseUrl + "/get-location-by-id/" + id, HttpMethod.GET, null, Location.class);
        return response.getBody();
    }

    public List<Location> getLocationsInEnvironment(int environmentId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Location[]> response = restTemplate.exchange(baseUrl + "/get-locations-in-environment/" + environmentId, HttpMethod.GET, null, Location[].class);
        return Arrays.asList(response.getBody());
    }

    public List<Location> getLocationsInGrid(int gridId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Location[]> response = restTemplate.exchange(baseUrl + "/get-locations-in-grid/" + gridId, HttpMethod.GET, null, Location[].class);
        return Arrays.asList(response.getBody());
    }

    public Location getLocationOfEntity(int entityId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Location> response = restTemplate.exchange(baseUrl + "/get-location-of-entity/" + entityId, HttpMethod.GET, null, Location.class);
        return response.getBody();
    }

    // testing
    public static void main(String[] args) {
        LocationService locationService = new LocationService(new RestTemplateBuilder());
        List<Location> locations = locationService.getLocationsInEnvironment(1);
        for (Location location : locations) {
            System.out.println(location);
        }
    }
}
